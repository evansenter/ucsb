require "../lib/motif_and_background_matrices_module.rb"

class Gibbs
  include MotifAndBackgroundMatrices

  attr_accessor :motif_length, :data, :motif_matrix, :background_hash, :frequency_hash

  def initialize(options = {})
    options = {:combine_data? => true, :randomize_starting_locs? => true}.merge(options)
    @data             = options.delete(:data) || SEQUENCES
    @motif_length     = (options.delete(:motif_length) || 10).freeze
    @calculation_type = (options.delete(:calculation_type) || :*).freeze
    
    # The M/B matrices module builds off the :training array in the @data hash, so we need to prepare the data based on the user's run preference.
    # Either way we're removing the :test key.
    if options.delete(:combine_data?)
      @data.delete(:test).each { |sequence| @data[:training] << sequence }
    else
      @data[:training] = @data.delete(:test)
    end
    
    # Default is to start with random motif positions. This randomizes the data.
    @data[:training].each { |sequence| sequence[0] = rand(number_of_possible_motifs(sequence[:full_sequence].length)) } if options.delete(:randomize_starting_locs?)
    
    # Initialize the frequency hash.
    @frequency_hash = {}
    clear_frequency_hash
  end

  def move_one_step
    # Pull a random sequence out.
    @data[:removed_sequence] = @data[:training].delete_at(sequence_index_to_remove)
    
    # Calculate position-specific motif probabilities and position non-specific background probabilities.
    @motif_matrix    = build_motif_matrix
    @background_hash = build_background_hash
    
    # Find the best motif position and record it in the frequency hash.
    best_motif_position = max_index_and_score(maximum_likelihood(@data[:removed_sequence], @calculation_type))[:index]
    @frequency_hash[@data[:removed_sequence][:full_sequence]][best_motif_position] += 1
    
    # Update that sequence's recorded motif starting position, and put it back into the training data.
    @data[:removed_sequence][0] = best_motif_position
    @data[:training] << @data[:removed_sequence]
  end
  
  def sequence_index_to_remove
    rand(@data[:training].length)
  end
  
  def clear_frequency_hash
    @data[:training].each { |sequence| @frequency_hash[sequence[:full_sequence]] = (0...number_of_possible_motifs(sequence[:full_sequence].length)).map { 0 } }
  end
  
  def run_and_print
    print_code = proc do |calculation_type|
      1000.times { move_one_step }
      clear_frequency_hash
      print "Finished warming up.\n\n"
      
      10000.times { move_one_step }
      sort(@data[:training])
      
      (0...@data[:training].length).each do |index|
        sequence = @data[:training][index][:full_sequence]
        print "Sequence [#{sequence}]'s motif starts at index #{max_index_and_score(@frequency_hash[sequence])[:index]}\n"
      end
    end
    super(print_code)
  end
end