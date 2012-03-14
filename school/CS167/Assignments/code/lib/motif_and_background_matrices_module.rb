require "../lib/string_extensions.rb"

module MotifAndBackgroundMatrices    
  SEQUENCES = { :training => [{:motif_index => 8,  :full_sequence => "ATATTGTGTTTATTGTTTACCTACCGAACACATATGACAG"}, 
                              {:motif_index => 15, :full_sequence => "TCGGCGGTGGTAGACCTTATTGTTCCGCAACCACATCTAT"}, 
                              {:motif_index => 12, :full_sequence => "AAATCCCTTTTGTCTATTGTTTGAAAGTGGTGGGGCCATG"}, 
                              {:motif_index => 15, :full_sequence => "TTGAGCTAAATTATCTTTATTGTTTCGTGGCCGCATGATG"}, 
                              {:motif_index => 19, :full_sequence => "GGCTTTGGGACAGTCATGGCCAATTGTTCGTTATTCTGCC"}, 
                              {:motif_index => 15, :full_sequence => "TACGTTTAGTCGGTCTTAATTGTTCCAGTACCCAGGTCGG"}, 
                              {:motif_index => 14, :full_sequence => "GAGTCCCCCTTTCTCTTATTGTTCCAGTGGTTCGTCCACC"}, 
                              {:motif_index => 16, :full_sequence => "AAGGACGATATTGTTTTCGATTGTTCCAGAGATACATTTC"}, 
                              {:motif_index => 15, :full_sequence => "TTAGGGAGTACTCATTTCATTGTTTATAATAATGCGATCA"}, 
                              {:motif_index => 12, :full_sequence => "ACGAATAGTCTATCTATTGTTTCTGAGAAAGACACCCATG"}],

                :test =>     [{:motif_index => 12, :full_sequence => "ATACGGCTTGTCTTGATTGTTTATCCAATTGTCGGAGCTC"}, 
                              {:motif_index => 18, :full_sequence => "GCCCTATTCATTGGACGATTGATTGTTTCGTCTCCCCGTG"}, 
                              {:motif_index => 14, :full_sequence => "ATTCAACCTACACTCTCATTGTTTACATGGTGGATAGGGG"}, 
                              {:motif_index => 15, :full_sequence => "CACCTGCGACGCCTTTCGATTGTTTAATAGTTGACACAGT"}, 
                              {:motif_index => 11, :full_sequence => "TCTTGTTCCAGTCTATTGTTCAGAAGAGGACTACTTCAAA"}] }
  
  def build_motif_matrix
    # Make a matrix the length of the motif, with each index containing a hash of all possible symbols.
    motif_matrix = (0...@motif_length).map {{ :A => 0.0, :C => 0.0, :G => 0.0, :T => 0.0 }}

    @data[:training].each do |sequence|
      full_sequence = sequence[:full_sequence]
      motif_start   = sequence[:motif_index]
      
      # Get the substring of the full sequence that represents the motif.
      motif = full_sequence[motif_start, @motif_length].each_to_a    
      # Iterate over the array, and increment the counter at the index in the hash for the seen symbol.
      motif.each_index { |index| motif_matrix[index][motif[index].upcase.to_sym] += 1 }
    end

    # Normalize each hash of the motif matrix.
    motif_matrix.map { |symbol_hash| symbol_hash.each { |key, value| symbol_hash[key] /= @motif_length } }
  end

  def build_background_hash
    # Make a hash of the symbols, we will compute the probability they are in the background.
    background_hash = { :A => 0.0, :C => 0.0, :G => 0.0, :T => 0.0 }
    
    # Reject the motif segment, indicated by the first position in the sequence array.
    @data[:training].each do |sequence|
      full_sequence = sequence[:full_sequence]
      motif_start   = sequence[:motif_index]
      
      # The background sequence is the full sequence minus the region that has the motif, I build it from the halves rather than rip out the middle.
      background_sequence = (full_sequence[(0...motif_start)] + full_sequence[((motif_start + @motif_length)...full_sequence.length)]).each_to_a
      # Increment the counter for the corresponding symbol for each character in the background.
      background_sequence.each { |value| background_hash[value.upcase.to_sym] += 1 }
    end
    
    # Normalize the hash of the background matrix - finding the sum explicitly because sequences could be of variable length.
    sum = background_hash.inject(0.0) { |sum, key_value_array| sum + key_value_array.last }
    background_hash.each { |key, value| background_hash[key] /= sum }
  end
  
  def number_of_possible_motifs(sequence_length)
    sequence_length - @motif_length + 1
  end
  
  def maximum_likelihood(sequence, calculation_type = :*)
    # Gather the full sequence from the sequence array.
    full_sequence = sequence[:full_sequence].each_to_a
    # Calculate the number of possible sequences.
    possible_motifs = number_of_possible_motifs(full_sequence.length)
    # There's two different ways to calculate this, with a product of probabilities or a sum of location probabilities divided by the length of the sequence.
    starting_injection = calculation_type == :* ? 1.0 : 0.0
    
    # For each possible motif position...
    (0...possible_motifs).map do |motif_start|
      # We will calculate the probability that this sequence has a motif at the motif_start, using the training data calculated in initialize.
      motif_likelihood = (0...full_sequence.length).inject(starting_injection) do |probability, index|
        # Turn the current symbol in the sequence into a Ruby symbol for hash lookup.
        current_symbol = full_sequence[index].upcase.to_sym
        
        # Chain calculate the probability with this inject return, if/else on whether the current index of the sequence is in the motif or not.
        # This is so we know which model to get the probability from. Using a little metaprogramming so we can calculate it differently
        # depending on the call params.
        symbol_probability = if (motif_start...(motif_start + @motif_length)) === index
                               index_relative_to_motif = index - motif_start
                               @motif_matrix[index_relative_to_motif][current_symbol]
                             else
                               @background_hash[current_symbol]
                             end
        probability.send(calculation_type, symbol_probability)
      end
      
      # Divide through by the sequence length if we're doing a sum calculation.
      calculation_type == :* ? motif_likelihood : motif_likelihood /= full_sequence.length
    end
  end
  
  def max_index_and_score(results)
    # Returns a hash of the best score and its index, 0-indexed. It is greedy, and will only return one best score if there is a tie.
    best_score = results.max
    {:index => results.index(best_score), :score => best_score}
  end
  
  def sort(data)
    data.sort! { |a, b| a[:full_sequence] <=> b[:full_sequence] }
  end
  
  def run_and_print(what_to_print)
    # The main execution method. Metaprogrammed. You have to learn somehow. :) Look in the subclasses to see the closure that gets passed in.
    print "- - - - - R U N N I N G   #{self.class.to_s.upcase.each_to_a.join(' ')} - - - - -\n\n"
    [:*, :+].each do |calculation_type|
      print "Calculating the most likely motif location with a " + (calculation_type == :* ? "product" : "sum") + ".\n\n"
      
      what_to_print.call(calculation_type)
      
      print "\nMotif matrix: #{@motif_matrix.inspect}\n"
      print "\Background matrix: #{@background_hash.inspect}\n\n"
    end
    print "- - - - - D O N E - - - - -\n"
  end
end