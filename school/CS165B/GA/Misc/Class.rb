require 'rubygems'
require 'ruby_to_ansi_c'

class A
  attr_reader :a
  def initialize
    @a = "a"
    puts @a
  end
end

class B
  attr_reader :b
  def initialize
    @b = "b"
  end
end

def main
  print (A.new).a, (B.new).b, "\n"
end

result = RubyToAnsiC.translate_all_of A
puts result
