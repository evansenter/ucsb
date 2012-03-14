require 'cell_map.rb'

@config = {}

def cut(line)
  line.split(" ")
end

def csv?
  @config["csv_mode"] == 1
end

def out(string)
  print string unless csv?
end

def parse_params_file(loc)
  File.open(loc).select {|line| line[0, 1] != "#"}.each {|line| @config[cut(line)[1]] = ((cut(line)[0] != "i" and cut(line)[0] != "f") ? cut(line)[0] : (cut(line)[0] == "i" ? cut(line)[2].to_i : cut(line)[2].to_f))}
end

parse_params_file(ARGV[0])
@config.each_pair {|key, value| print "Key / Value pair: ", key, " => ", value, "\n" unless csv?}
out "#{ARGV[0]} loaded successfully.\nStarting the game in 1 second.\n\n"
sleep 1

cells, data, max_fitness, no_counter = CellMap.new(@config), [], 0, false
@config['counter'], no_counter = 1, true unless @config['counter'] > 0

while cells.gather_cells.length > 0 and @config['counter'] != 0
  unless csv?
    cells.print_cell_grid
    cells.print_env_grid
    out "\n"
    print "Counter: #{@config['counter']}\n" unless csv?
  end
  unless @config['heavy_end_data'] == 0
    data << cells.cell_map_info unless @config['heavy_end_data'] == 0
    cells.print_cell_map_info(data[-1]) unless csv?
    max_fitness = (data[-1].max_fitness > max_fitness ? data[-1].max_fitness : max_fitness)
  end
  cells.update_cells
  if ((@config['counter'] - 1) % @config['evolution_freq'] == 0)
    cells.evolve_cells
    print "\n- - - - - E V O L U T I O N - - - - -\nCells were bred with crossover rate: #{@config['crossover_flip']} and mutation rate: #{@config['mutation_rate']}\n- - - - - - - - - - - - - - - - - - -\n\n" unless csv?
  end
  @config['counter'] += no_counter ? 1 : -1
end

if cells.gather_cells.length == 0
  unless csv?
    print "\nF I N A L  S T A T E:\n\n"
    cells.print_cell_grid
    print "\n\n"
    cells.print_env_grid
    print "\n"
  end
  data << cells.cell_map_info unless @config['heavy_end_data'] == 0
  cells.print_cell_map_info(data[-1]) unless csv? or @config['heavy_end_data'] == 0
end

data.each {|data_point| csv? ? data_point.print_csv : data_point.print_data unless @config['heavy_end_data'] == 0} 

@config['counter'] == 0 ? (out "The requested number of rounds were executed.\n") : (print "All the cells died off ", @config['counter'], " moves in and ", @config['counter'] / @config['evolution_freq'], " evolutions in.\n" unless csv?)
print "Max fitness over the whole evolution was: ", max_fitness, "\n\n" unless csv?
