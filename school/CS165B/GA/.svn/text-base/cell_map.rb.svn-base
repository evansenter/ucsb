require 'cell.rb'
require 'env_map.rb'
require 'data_point.rb'

class CellMap
  attr_reader :config, :env_grid, :length, :default_signal_string

  def initialize(config)
    @config = config
    @cell_grid, @env_grid, @default_signal_string = (0...(@length = @config['length'])).map {|row| (0...@length).map {|column| rand < @config['cell_prob'] ? Cell.new(@config, row, column) : nil}}, EnvMap.new(@config), "00000000"
  end
	
  def print_cell_grid
    print "\nGenetic algorithm map (On top of):\n"
    @cell_grid.each do |row|
      row.each {|cell| cell.class == Cell ? cell.print_symbol : (print "[ ] ")}
      print "\n"
    end
  end
	
  def print_env_grid
    env_grid.print_env_grid
  end
	
  def to_a
    @cell_grid
  end
	
  def adjacent_coord_pairs(row, column)
    neighbors = []
    (-1..1).each {|a| (-1..1).each {|b| neighbors << [(row+a) % @length, (column+b) % @length]}}
    neighbors
  end
	
  def convert_signal_to_bitstring(bitstring, cell_signal)
    bitstring[cell_signal] = "1"
    bitstring
  end
	
  def bitstring_of_conditions(cell)
    cell.age += 1
    fit = cell.fitness
    cell.fitness = (cell.fitness + @env_grid.food_present(cell.row, cell.column)) * @config['retained_fitness'] # There is a atrophy penalty each round - an upkeep cost all cells must pay to stay alive
    bitstring = ""
    adjacent_coord_pairs(cell.row, cell.column).each do |c_p|
      bitstring << (@cell_grid[c_p[0]][c_p[1]].class == Cell ? convert_signal_to_bitstring(@default_signal_string, @cell_grid[c_p[0]][c_p[1]].signal) : @default_signal_string) << (@env_grid.food_present(c_p[0], c_p[1]) == 1 ? "100" : @env_grid.food_present(c_p[0], c_p[1]) == -1 ? "001" : "010")
    end
    bitstring
  end
      	
  def gather_cells
    cells = []
    (0...@length).each {|row| (0...@length).each {|column| if @cell_grid[row][column].class == Cell: @cell_grid[row][column].fitness > 1 ? cells << @cell_grid[row][column] : @cell_grid[row][column] = nil end }}
    cells
  end
	
  def update_cells
    @env_grid.evolve_env
    (cells = gather_cells).each {|cell| resolve_action(cell, bitstring_of_conditions(cell)) }
  end
	
  def resolve_action(cell, bitstring)
    action = cell.get_action(bitstring) # Takes an Action and moves the cell if possible, and changes the signal
    if @cell_grid[new_row = (cell.row + action.row) % @length][new_column = (cell.column + action.column) % @length] == nil
      old_row, old_column = cell.row, cell.column
      @cell_grid[new_row][new_column] = cell
      @cell_grid[old_row][old_column] = nil
      cell.row, cell.column = new_row, new_column
    end
    cell.signal = action.signal
  end
	
  def evolve_cells
    next_grid = (0...@length).map {(0...@length).map { nil }}
    (breeding_cells = (parental_gen = gather_cells.sort {|a, b| b.fitness <=> a.fitness})[0, (@config['cell_retention'] * parental_gen.length) + 0.5]).each {|cell| next_grid[cell.row][cell.column] = cell}
    while breeding_cells.length > 1
      donor_1, donor_2, primary_parent, spawn_points = breeding_cells.delete_at(rand(breeding_cells.length)), breeding_cells.delete_at(rand(breeding_cells.length)), rand, []
      adjacent_coord_pairs((primary_parent > 0.5 ? donor_1 : donor_2).row, (primary_parent > 0.5 ? donor_1 : donor_2).column).each {|c_p| spawn_points << c_p unless next_grid[c_p[0]][c_p[1]] != nil}
      next unless spawn_points.length > 0 and donor_1 and donor_2 # Skip breeding unless we have parents and a spawn point
      coords = spawn_points[rand(spawn_points.length)]
      (next_grid[coords[0]][coords[1]] = Cell.new(@config, coords[0], coords[1])).meiosis(donor_1.chromosome, donor_2.chromosome)
      next_grid[coords[0]][coords[1]].generation = (primary_parent > 0.5 ? donor_1 : donor_2).generation + 1
      donor_1.signal, donor_2.signal = 0, 0	# Cells that just had an offspring have a signal of 0
    end
    @cell_grid = next_grid
  end
	
  def cell_map_info
    cells = gather_cells
    return DataPoint.new(0, 0, 0, 0, 0, 0) unless cells.length > 0
    max_age, max_fitness, min_fitness, avg_age, avg_fitness, num_cells = cells[0].age, cells[0].fitness, cells[0].fitness, 0, 0, cells.length
    cells.each do |cell|
      avg_age += cell.age
      avg_fitness += cell.fitness
      max_age = cell.age unless max_age > cell.age
      max_fitness = cell.fitness unless max_fitness > cell.fitness
      min_fitness = cell.fitness unless min_fitness < cell.fitness
    end
    DataPoint.new(num_cells, max_age, (avg_age / num_cells), max_fitness, min_fitness, (avg_fitness / num_cells))
  end

  def print_cell_map_info(data_point)
    @env_grid.print_env_info
    data_point.print_data
  end
end
