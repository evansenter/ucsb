require 'test/unit'
require 'cell.rb'

class CellTest < Test::Unit::TestCase
	def setup
		@cell = Cell.new(0, 0)
	end

	def test_true
		assert @cell != nil
	end
	
	def test_mitosis
		chrom = @cell.mitosis(0)
		(new_cell = Cell.new(0, 0)).insert_genome(chrom)		
		new_cell.chromosome.genes.each_index do |i|
			assert_equal new_cell.chromosome.genes[i].precondition, @cell.chromosome.genes[i].precondition
			assert_equal new_cell.chromosome.genes[i].postcondition, @cell.chromosome.genes[i].postcondition
		end
		
		chrom = @cell.mitosis(1)
		(new_cell2 = Cell.new(0, 0)).insert_genome(chrom)	
		new_cell2.chromosome.genes.each_index do |i|
			assert_not_equal new_cell2.chromosome.genes[i].precondition, @cell.chromosome.genes[i].precondition
			assert_not_equal new_cell2.chromosome.genes[i].postcondition, @cell.chromosome.genes[i].postcondition
		end
	end
	
	def test_print_cell
		@cell.print_cell
	end
	
	def test_get_action
		assert @cell.get_action(@cell.chromosome.get_random_bitstring(9)).class == Action
	end
end