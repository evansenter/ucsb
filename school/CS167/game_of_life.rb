# If live and 2 to 3 neighbors => live
# If dead and 3 neighbors => live

class ConwayMap
  def initialize(size, options = {})
    @map_size = size
    @map = (1..@map_size).map { (1..@map_size).map { nil } }
  end
  
  def each
    # How do you overload this iterator with yield?
  end
  
  def neighbors(row, column, options = {})
  
  end
end