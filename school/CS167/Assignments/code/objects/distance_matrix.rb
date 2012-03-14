require "../lib/array_extensions.rb"
require "../lib/symbol_extensions.rb"

class DistanceMatrix
  attr_accessor :matrix
  
  MATRIX_11 = [[0],
               [9, 0],
               [8, 3, 0],
               [7, 6, 5, 0],
               [8, 7, 6, 3, 0]]
    
  MATRIX_12 = [[0],
               [4, 0],
               [3, 3, 0],
               [1, 6, 5, 0]]
      
  MATRIX_13 = [[0],
               [2, 0],
               [1.5, 6, 0]]
        
  MATRIX_14 = [[0],
               [1.25, 0]]
  
  MATRIX_21 = [[0],
               [4,  0],
               [8,  6,  0],
               [11, 8,  3,  0],
               [5,  4,  6,  8,  0]] 
  
  MATRIX_22 = [[0],
               [5,  0],
               [7.5,  3,  0],
               [2.5,  6,  8,  0]] 
    
  MATRIX_23 = [[0],
               [2.75,  0],
               [1.75,  8,  0]] 
  
  def initialize(matrix)
    @matrix = DistanceMatrix.const_get(matrix.to_sym)
  end
  
  def additive?
    (0...@matrix.length).each { |i| (0...@matrix.length).each { |j| (0...@matrix.length).each { |k| (0...@matrix.length).each do |l|
      return false if @matrix.get(i, j) + @matrix.get(k, l) > [@matrix.get(i, k) + @matrix.get(j, l), @matrix.get(j, k) + @matrix.get(i, l)].max
    end } } }
    return true
  end
  
  def calculate_q_matrix
    (0...@matrix.length).map { |i| (0...@matrix.length).map do |j|
      if i <= j
        first_term  = (@matrix.length - 2) * @matrix.get(i, j)
        second_term = (0...@matrix.length).map { |k| @matrix.get(i, k) }.inject(&:+)
        third_term  = (0...@matrix.length).map { |k| @matrix.get(j, k) }.inject(&:+)
        first_term - second_term - third_term
      end
    end }.reverse.map { |row| row.compact }
  end
  
  def pair_to_join
    q_matrix = calculate_q_matrix
    
    p q_matrix
    
    best_i = best_j = lowest_score = nil
    
    (0...@matrix.length).map { |i| (i + 1...@matrix.length).map do |j|
      if lowest_score.nil? || q_matrix.get(i, j) < lowest_score
        best_i, best_j = [i, j].sort.reverse
        lowest_score = q_matrix.get(i, j)
      end
    end }
    
    [best_i, best_j]
  end
  
  def distance_from_pair_to_new_node(pair_array)
    [pair_array, pair_array.reverse].map do |pair|
      first_term = @matrix.get(pair.first, pair.last) * 0.5
      second_term = (0...@matrix.length).map { |i| @matrix.get(pair.first, i) }.inject(&:+)
      third_term  = (0...@matrix.length).map { |i| @matrix.get(pair.last, i)  }.inject(&:+)
      multiplier = 1.0 / (2 * (@matrix.length - 2))
      
      first_term + multiplier * (second_term - third_term)
    end
  end
  
  def distance_from_node_to_new_node(node, pair_array)
    (@matrix.get(node, pair_array.first) + @matrix.get(node, pair_array.last) - @matrix.get(pair_array.first, pair_array.last)) / 2.0
  end
end