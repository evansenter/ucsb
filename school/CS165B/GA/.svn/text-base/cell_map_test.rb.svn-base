require 'test/unit'
require 'cell_map.rb'

class CellMapTest < Test::Unit::TestCase
	def setup
		@cells = CellMap.new(10, 0.25, 0.0, 0.0)
		@cells2 = CellMap.new(10, 0.0, 0.0, 0.0)
	end

	def test_true
		assert @cells != nil
	end
	
	def test_print_cell_grid
		@cells.print_cell_grid
	end
	
	def test_to_a
		assert_equal @cells.to_a.class, Array
	end
	
	def test_adjacent_coord_pairs
		@cells.adjacent_coord_pairs(1, 1).each do |c_p|
			assert (0..2) === c_p[0] && (0..2) === c_p[1]
		end
	end
	
	def test_convert_signal_to_bitstring
		assert_equal @cells.convert_signal_to_bitstring("0000000000000000", 0), "1000000000000000"
		assert_equal @cells.convert_signal_to_bitstring("0000000000000000", 15), "0000000000000001"
	end
	
	def test_bitstring_of_conditions
		bitstring = @cells.bitstring_of_conditions(cell = Cell.new(0, 0))
		assert cell.age == 1
		assert_equal bitstring.length, 99
	end
	
	def test_resolve_action
		assert @cells.default_signal_string.length == 8
		cell = Cell.new(0, 0)
		@cells.resolve_action(cell, @cells.default_signal_string)
	end
	
	def test_gather_cells
		assert @cells2.gather_cells.length == 0
	end
	
	def test_update_cells
		@cells.update_cells
	end
	
	def test_print_cell_info
		assert @cells.gather_cells.length > 0
		assert @cells.print_cell_map_info.class == DataPoint
	end
end