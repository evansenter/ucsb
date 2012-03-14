class DataPoint
  attr_reader :num_cells, :max_age, :avg_age, :max_fitness, :min_fitness, :avg_fitness
  
  def initialize(num_cells, max_age, avg_age, max_fitness, min_fitness, avg_fitness)
    @num_cells, @max_age, @avg_age, @max_fitness, @min_fitness, @avg_fitness = num_cells, max_age, avg_age, max_fitness, min_fitness, avg_fitness
  end
	
  def print_data
    if num_cells > 0
      print num_cells, " GA's present, age(max = ", max_age, ", avg = ", avg_age, "), fitness(max = ", max_fitness, ", min = ", min_fitness, " avg = ", avg_fitness, ")\n"
    else
      print num_cells, " GA's present, artificial extinction - good job..\n\n"
    end
  end

  def print_csv
    print num_cells, ", ", max_age, ", ", avg_age, ", ", max_fitness, ", ", min_fitness, ", ", avg_fitness, "\n"
  end
end
