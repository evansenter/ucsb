require 'test/unit'
require 'cell_bin.rb'
require 'cell.rb'

class CellBinTest < Test::Unit::TestCase
	def setup
		@cell_bin = CellBin.new
	end

	def test_true
		assert @cell_bin != nil
	end
	
	def test_num_cells_and_insert
		assert_equal 0, @cell_bin.num_cells
		@cell_bin << Cell.new(0, 0) << Cell.new(0, 0)
		assert_equal 2, @cell_bin.num_cells
	end
	
	def test_delete
		cell = Cell.new(0, 0)
		cell2 = Cell.new(0, 0)
		@cell_bin << cell
		@cell_bin << cell2
		assert_equal 2, @cell_bin.num_cells
		@cell_bin.remove(cell)
		assert_equal 1, @cell_bin.num_cells
		@cell_bin.remove(cell2)
		assert_equal 0, @cell_bin.num_cells
	end
	
	def test_each
		assert_equal 0, @cell_bin.num_cells
		@cell_bin.each{|cell| puts cell}
		@cell_bin << Cell.new(0, 0) << Cell.new(0, 0)
		@cell_bin.each{|cell| puts cell}
	end
end