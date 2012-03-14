class ProjectEuler
  def self.problem_1
    # Sum of all values under 1000 that are divisible by 3 or 5.
    (1..1000).inject(0) {|sum, value| sum += (value % 5 == 0 || value % 3 == 0 ? value : 0)}
  end
  
  def self.problem_2
    # Sum of all even numbers in the Fibonacci sequence less than 4 million.
    fibonacci = [1, 1]
    fibonacci << fibonacci[-1] + fibonacci[-2] while fibonacci.last < 4e6
    fibonacci.inject(0) {|sum, value| sum += (value % 2 == 0 ? value : 0)}
  end
  
  def self.problem_3(number)
    # Largest prime factor of a composite number.
    
  end
  
  def self.problem_4(number)
    # Largest palindrome made from the product of two 3-digit numbers.
    
  end
  
  def self.problem_5(number)
    # Smallest number divisible by all the numbers 1 - 20.
  end
  
  def self.problem_6(number)
    # Difference between the sum of squares and the square of sums.
    
  end
  
  def self.problem_7(number)
    # Find the 10001st prime.
    
  end
end