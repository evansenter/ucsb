class EnvMap
  attr_reader :good_food

  def initialize(config)
    @config = config
    @good_food, @no_food, @poison_food, @poison_prob, @food_flip_prob = "[O] ", "[ ] ", "[X] ", @config['poison_prob'], @config['food_flip_prob']
    if @config['use_initial_map'] == 0
      @env_grid = (0...(@length = @config['length'])).map {|i| (0...@length).map {|j| rand < @config['initial_food_prob'] ? get_food : @no_food}}  
    else
      @env_grid = (0...(@length = @config['length'])).map {|i| (0...@length).map {|j| ("" << @config[i.to_s][j]) == "O" ? get_food : @no_food}} 
    end
  end
	
  def get_food
    rand < @poison_prob ? @poison_food : @good_food
  end

  def print_env_grid
    print "\nGame of Life map (Below):\n"
    @env_grid.each do |row|
      row.each {|cell| print cell}
      print "\n"
    end
  end
	
  def print_env_info
    num_good, num_poison, num_empty, num_tiles = 0, 0, 0, @length * @length
    @env_grid.each {|row| row.each {|cell| cell == @good_food ? num_good += 1 : cell == @poison_food ? num_poison += 1 : num_empty += 1 }}
    print "There are ", num_good, " good food tiles, ", num_poison, " poison food tiles, and ", num_empty, " empty tiles\n"
  end
	
  def to_a
    @env_grid
  end
	
  def adjacent_coord_pairs(x, y)
    neighbors = []
    if @config['wrap_around'] == 0
      (-1..1).each {|a| (-1..1).each {|b| neighbors << [x + a, y + b] unless (a == 0 && b == 0) || !((0...@length) === (x + a) && (0...@length) === (y + b))}}
    else
      (-1..1).each {|a| (-1..1).each {|b| neighbors << [(x+a) % @length, (y+b) % @length] unless a == 0 && b == 0}}
    end
    neighbors
  end
	
  def food_present(row, column)
    @env_grid[row][column] == @no_food ? 0 : (@env_grid[row][column] == @good_food ? 1 : -1)
  end
	
  def evolve_env
    next_gen = @env_grid.clone
    (0...@length).each {|x| (0...@length).each do |y|
      live_neighbors = adjacent_coord_pairs(x, y).select {|coord| @env_grid[coord[0]][coord[1]] != @no_food}.size
      next_gen[x][y] = @env_grid[x][y] != @no_food ? ((2..3) === live_neighbors ? @env_grid[x][y] : @no_food) : (live_neighbors == 3 ? get_food : @no_food)
    end }
    if rand < @food_flip_prob
      rand(@config['max_inserts']).times do
	x, y = rand(@length), rand(@length)
	next_gen[x][y] = (next_gen[x][y] == @no_food ? get_food : @no_food)
      end
    end
    @env_grid = next_gen
  end
end
