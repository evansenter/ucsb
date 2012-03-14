require 'chromosome.rb'

class Cell
  attr_accessor :signal, :age, :fitness, :row, :column, :generation
  attr_reader :chromosome

  def initialize(config, row, column)
    @config, @row, @column, @signal, @age, @generation = config, row, column, 1, 0, 0
    @chromosome, @fitness = Chromosome.new(@config), @config['starting_fitness']
  end
	
  def print_symbol
    print "[", ("" << ((@generation % 26) + 65)), "] "
  end
  
  def mitosis
    @chromosome.replicate_genes
  end

  def meiosis(chrom_1, chrom_2)
    @chromosome.meiosis(chrom_1, chrom_2)
  end
	
  def insert_genome(new_genome)
    @chromosome.insert_genes(new_genome)
  end
  
  def get_action(bitstring)
    @chromosome.get_response(bitstring)
  end
	
  def print_cell
    print "Row: ", @row, ", Column: ", @column, ", Age: ", @age, ", Fitness: ", @fitness, ", Signal: ", @signal, "\n\n"
    @chromosome.print_chromosome
  end
end
