require 'gene.rb'

class Chromosome	
  attr_reader :genes, :precondition_length, :postcondition_length
  
  # (8 possible signals from adjacent cell + 3 for food) x 9 cells adjacent (including self)
  # 2 bits [row movement], 2 bits [column movement], 3 bits [signal]
  def initialize(options = {})
    @options = options
    @precondition_length, @postcondition_length = 99, 7
    @genes = (0...@options['number_of_genes']).map { Gene.new(@options, get_random_bitstring(@precondition_length), get_random_bitstring(@postcondition_length)) }
    @best_genes = @options['number_of_genes'] * @options['percent_gene_responders']
  end
	
  def get_random_bitstring(length)
    (0...length).map { rand < 0.5 ? 0 : 1 }.to_s
  end
	
  def mutate_bitstring(bitstring)
    new_string = ""
    bitstring.length.times {|i| new_string << (rand < @options['mutation_rate'] ? (bitstring[i, 1] == "0" ? "1" : "0") : bitstring[i, 1])}
    new_string
  end
	
  def insert_genes(new_genes)
    @genes = new_genes
  end
	
  def replicate_genes
    @genes.map {|gene| Gene.new(@options, mutate_bitstring(gene.precondition), mutate_bitstring(gene.postcondition))}
  end

  def sort_genes
    @genes.sort {|a, b| b.postcondition <=> a.postcondition}
  end

  def meiosis(chrom_1, chrom_2)
    @genes, haploid_1, haploid_2 = [], chrom_1.sort_genes, chrom_2.sort_genes
    haploid_1.length.times {|i| @genes << haploid_1[i].merge_gene(haploid_2[i])}
    @genes
  end
	
  def get_response(conditions)
    @genes.map {|gene| gene.get_postcondition(conditions)}.sort {|a, b| b.power <=> a.power}[0, @best_genes][rand(@best_genes)]
  end
	
  def print_chromosome
    @genes.each {|gene| gene.print_gene}
  end
end
