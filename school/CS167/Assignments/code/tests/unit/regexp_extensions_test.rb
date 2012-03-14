require "test/unit"
require "../../lib/regexp_extensions.rb"

class RegexpTest < Test::Unit::TestCase
  def test_true
    assert true
  end

  def test_internals
    assert_equal "a", /a/.internals
    assert_equal "acgt", Regexp.new("acgt", Regexp::IGNORECASE).internals
  end
end