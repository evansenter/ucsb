require 'test/unit'
require 'chromosome.rb'

class ChromosomeTest < Test::Unit::TestCase
	def test_get_random_bitstring
    bitstring = Chromosome.new.get_random_bitstring(10)
    assert_equal 10, bitstring.length
    assert bitstring.all? { |bit| 0..1 === bit }
  end
end