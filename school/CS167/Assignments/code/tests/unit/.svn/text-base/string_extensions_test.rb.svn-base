require "test/unit"
require "../../lib/string_extensions.rb"

class StringTest < Test::Unit::TestCase
  def test_true
    assert true
  end
  
  def test_at
    assert_equal nil, "".at(1)
    assert_equal "a", "a".at(0)
    assert_equal "a", "a".at(-1)
    assert_equal "b", "abc".at(1)
  end
  
  def test_each_to_a
    assert_equal [], "".each_to_a
    assert_equal ["a", "b", "c"], "abc".each_to_a
  end
end