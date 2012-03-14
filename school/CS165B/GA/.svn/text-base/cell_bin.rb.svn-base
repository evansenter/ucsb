class CellBin
	def initialize
		@cell_bin = []
	end
	
	def << cell
		@cell_bin << cell
	end
	
	def remove(cell)
		@cell_bin = @cell_bin.select{|bin_cell| bin_cell != cell}
	end
	
	def each
		@cell_bin.each {|cell| yield cell}
	end
	
	def num_cells
		return @cell_bin.length
	end
end