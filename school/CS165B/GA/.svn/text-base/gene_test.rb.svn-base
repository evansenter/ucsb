require 'test/unit'
require 'gene.rb'

class GeneTest < Test::Unit::TestCase
	def setup
		@gene = Gene.new("000000000", "00000000")
	end

	def test_true
		assert @gene != nil
	end
	
	def test_bitstring_to_i
		assert_equal 0, @gene.bitstring_to_i("")
		assert_equal 0, @gene.bitstring_to_i("0")
		assert_equal 1, @gene.bitstring_to_i("1")
		assert_equal 13, @gene.bitstring_to_i("1101")
	end
	
	def test_get_postconditions
		assert_equal @gene.get_postcondition(@gene.precondition).class, Action
		bad_gene = ""
		@gene.precondition.length.times {|i| bad_gene << (@gene.precondition[i, 1] == "0" ? "1" : "0")}
		assert @gene.get_postcondition(bad_gene).power <= 0
	end
	
	def test_print_parsed_postcondition
		@gene.print_parsed_postcondition
	end
end