require 'rubygems'
require 'ruby_to_ansi_c'
require 'cell_map.rb'

class MainC

cells = CellMap.new(12, 0.25, 0.5, 0.5)
counter = 0
evolve_freq = 5
max_fitness = 0
mut_freq = 0.1

while cells.gather_cells.length > 0
	cells.print_cell_grid
	print "\n\n"
	cells.print_env_grid
	print "\n"
	max_fitness = ((c = cells.print_cell_map_info.max_fitness) > max_fitness ? c : max_fitness)
	cells.update_cells
	if (counter + 1) % evolve_freq == 0
		cells.evolve_cells(mut_freq)
		print "Cells were evolved with mutation rate of: ", mut_freq, "\n\n"
	end
	counter += 1
	sleep 1.0 / 60.0
	sleep 1.0 / 60.0
end

cells.print_cell_grid
print "\n\n"
cells.print_env_grid
print "\n"
	

print "All the cells died off ", counter, " moves in and ", counter / evolve_freq, " evolutions in.\n"
print "Max fitness over the whole evolution was: ", max_fitness, "\n"

end

result = RubyToAnsiC.translate_all_of MainC
puts result
