require 'action.rb'

class Gene
  attr_reader :precondition, :postcondition

  def initialize(config, precondition, postcondition)
    @config, @precondition, @postcondition = config, precondition, postcondition
  end

  def bitstring_to_i(bitstring)
    base10_int = 0
    bitstring.reverse!.length.times {|i| base10_int += (bitstring[i, 1] == "1" ? 2 ** i : 0)}
    base10_int
  end

  def get_postcondition(conditions)
    similarity = 0
    @precondition.length.times do |i|
      if @precondition[i, 1] == "0" && conditions[i, 1] == "1"
        similarity -= ((8..10) === (i % 11) ? @config['food_weight'] : 1)
      elsif @precondition[i, 1] == conditions[i, 1]
	similarity += ((8..10) === (i % 11) ? @config['food_weight'] : 1)
      end
    end
    Action.new(similarity, (@postcondition[0, 2] == "11" ? 1 : @postcondition[0, 2] == "00" ? -1 : 0), (@postcondition[2, 2] == "11" ? 1 : @postcondition[2, 2] == "00" ? -1 : 0), bitstring_to_i(@postcondition[4, @postcondition.length - 4]))
  end

  def merge_gene(gene_2)
    new_precondition, new_postcondition, reader = "", "", 0
    @precondition.length.times do |i|
      reader = (reader == 0 ? 1 : 0) if rand < @config['crossover_flip']
      new_precondition << (reader == 0 ? @precondition : gene_2.precondition)[i, 1]
      new_precondition[-1] = (new_precondition[-1, 1] == 0 ? 1.to_s : 0.to_s) if rand < @config['mutation_rate']
    end
    @postcondition.length.times do |i|
      reader = (reader == 0 ? 1 : 0) if rand < @config['crossover_flip']
      new_postcondition << (reader == 0 ? @postcondition : gene_2.postcondition)[i, 1]
      new_postcondition[-1] = (new_postcondition[-1, 1] == 0 ? 1.to_s : 0.to_s) if rand < @config['mutation_rate']
    end
    @precondition, @postcondition = new_precondition, new_postcondition
    self
  end

  def print_gene
    print "Gene precondition\n", @precondition, "\n\n", "Gene postcondition\n", @postcondition, "\n[][][  ]\n\n"
  end

  def print_parsed_postcondition
    print "The response is to move rowwise: ", @postcondition[0, 2] == "11" ? "forward " : @postcondition[0, 2] == "00" ? "backward " : "nowhere "
    print "and in columnwise: ", @postcondition[2, 2] == "11" ? "right " : @postcondition[2, 2] == "00" ? "left " : "nowhere "
    print "and display the signal ", bitstring_to_i(@postcondition[4, 4]), "\n\n"
  end
end
