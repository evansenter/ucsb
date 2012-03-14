require "test/unit"
require "../../objects/translator.rb"

class TranslatorTest < Test::Unit::TestCase
  def test_true
    assert true
  end
  
  def test_to_mrna
    assert_equal "aAcCgGuU", Translator.to_mrna("aAcCgGtT")
  end
  
  def test_translate_single_codons
    assert_equal "m", Translator.translate("AuG")
    assert_equal "!", Translator.translate("uAg")
    assert_equal "X", Translator.translate("xxx")
  end
  
  def test_translate_sequence
    assert_equal "mXf!", Translator.translate("augxxxuuuuagt")
    assert_equal "XffllX", Translator.translate("qqquuuuucuuauugqqq")
  end
  
  def test_complement_sequence
    assert_equal "aXtXa", Translator.complement("tqaqt")
    assert_equal "agctagct", Translator.complement("TcGatCgA")
  end
  
  def test_collect
    assert_equal "bc", Translator.collect("abcde", (1..2))
    assert_equal "b", Translator.collect("abcde", (1...2))
    assert_equal "abdegh", Translator.collect("!abcdefgh", [(1..2), (4..5), (7..8)])
    assert_equal "abde", Translator.collect(["!", "a", "b", "c", "d", "e"], [(1..2), (4..5)])
  end
    
  def test_collect__one_indexed
    assert_equal "ab", Translator.collect("abcde", (1..2), :one_indexed => true)
    assert_equal "a", Translator.collect("abcde", (1...2), :one_indexed => true)
    assert_equal "abdegh", Translator.collect("abcdefgh", [(1..2), (4..5), (7..8)], :one_indexed => true)
    assert_equal "abde", Translator.collect(["a", "b", "c", "d", "e"], [(1..2), (4..5)], :one_indexed => true)
  end
  
  def test_flanking
    assert_equal [(-1..0), (11..12), (18..19), (31..32)], Translator.flanking([(1..10), (20..30)])
    assert_equal [(-1..0), (10..11), (18..19), (30..31)], Translator.flanking([(1...10), (20...30)])
  end
  
  def test_flanking__inner
    assert_equal [(11..12), (18..19)], Translator.flanking([(1..10), (20..30)], :inner => true)
    assert_equal [(10..11), (18..19)], Translator.flanking([(1...10), (20..30)], :inner => true)
  end
  
  def test_read_fa_file
    expected_string = "CTAACCCTAACCCTAACCCTAACCCTAACCCTAACCCTCTGAAAGTGGACCTATCAGCAGGATGTGGGTG" +
                      "GGAGCAGATTAGAGAATAAAAGCAGACTGCCTGAGCCAGCAGTGGCAACCCAATGGGGTCCCTTTCCATA" +
                      "CTGTGGAAGCTTCGTTCTTTCACTCTTTGCAATAAATCTTGCTATTGCTCACTCTTTGGGTCCACACTGC" +
                      "CTTTATGAGCTGTGACACTCACCGCAAAGGTCTGCAGCTTCACTCCTGAGCCAGTGAGACCACAACCCCA" +
                      "CCAGAAAGAAGAAACTCAGAACACATCTGAACATCAGAAGAAACAAACTCCGGACGCGCCACCTTTAAGA"
    assert_equal expected_string, Translator.read_fa_file("../files/test_file.fa")
  end
  
  def test_percent_appearance
    assert_equal 0, Translator.percent_appearance("X", "")
    assert_equal 0, Translator.percent_appearance("X", [])
    assert_equal 0.5, Translator.percent_appearance(/a/, "ab")
    assert_equal 1, Translator.percent_appearance("a", ["a", "a", "a"])
    assert_equal 1, Translator.percent_appearance("acgt", ["a", "c", "g", "t"])
    assert_equal 0.8, Translator.percent_appearance(/acgt/, "acgtacgtaa")
    assert_equal 2 / Float(3), Translator.percent_appearance(/aa/, "aaa")
  end
  
  def test_percent_appearance__case_insensitive
    assert_equal 1, Translator.percent_appearance("a", "aAa", :case_insensitive => true)
    assert_equal 0.5, Translator.percent_appearance("a", ["A", "X"], :case_insensitive => true)
    assert_equal 1, Translator.percent_appearance(/a/, "aAa", :case_insensitive => true)
    assert_equal 0.75, Translator.percent_appearance(/a/, ["A", "a", "A", "X"], :case_insensitive => true)
  end
  
  def test_nucleotide_frequency
    assert_equal ({"a" => 0, "c" => 0, "g" => 0, "t" => 0}), Translator.nucleotide_frequency("")
    assert_equal ({"a" => 0, "c" => 0, "g" => 0, "t" => 0}), Translator.nucleotide_frequency([])
    assert_equal ({"a" => 0.5, "c" => 0.5, "g" => 0, "t" => 0}), Translator.nucleotide_frequency("aaaccc")
  end  
  
  def test_nucleotide_frequency__case_insensitive
    assert_equal ({"a" => 0.1, "c" => 0.2, "g" => 0.3, "t" => 0.4}), Translator.nucleotide_frequency("AccGGGTTTT", :case_insensitive => true)
    assert_equal ({"a" => 0.5, "c" => 0, "g" => 0, "t" => 0}), Translator.nucleotide_frequency(["A", "x"], :case_insensitive => true)
  end
end