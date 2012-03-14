require "../lib/motif_and_background_matrices_module.rb"

class Markov
  include MotifAndBackgroundMatrices

  attr_accessor :motif_length, :data, :motif_matrix, :background_hash, :calculation_type
  
  def initialize(options = {})
    @data             = options.delete(:data) || SEQUENCES
    @motif_length     = (options.delete(:motif_length) || 10).freeze
    @calculation_type = (options.delete(:calculation_type) || :*).freeze
    @motif_matrix     = build_motif_matrix
    @background_hash  = build_background_hash
  end
  
  def run_and_print
    print_code = proc do |calculation_type|
      (0...sort(@data[:test]).length).each do |index|
        print "ACTUAL:     Sequence [#{@data[:test][index][:full_sequence]}]'s motif starts at index #{@data[:test][index][:motif_index]}\n"
        
        results = maximum_likelihood(@data[:test][index], calculation_type)
        print "CALCULATED: Sequence [#{@data[:test][index][:full_sequence]}]'s motif starts at index #{max_index_and_score(results)[:index]}\n\n"
      end
    end
    
    super(print_code)
  end
end