require "../lib/regexp_extensions.rb"
require "../lib/string_extensions.rb"

class Translator
  # Methods that would be nice:
  # number_of_appearances (From HW1 11.4)
  
  CODON = {
    "uuu" => "f",
    "uuc" => "f",
    "uua" => "l",
    "uug" => "l", 
    "ucu" => "s",
    "ucc" => "s",
    "uca" => "s",
    "ucg" => "s",
    "uau" => "y",
    "uac" => "y",
    "uaa" => "!",
    "uag" => "!",
    "ugu" => "c",
    "ugc" => "c",
    "uga" => "!",
    "ugg" => "w",
    "cuu" => "l",
    "cuc" => "l",
    "cua" => "l",
    "cug" => "l",
    "ccu" => "p",
    "ccc" => "p",
    "cca" => "p",
    "ccg" => "p", 
    "cau" => "h",
    "cac" => "h",
    "caa" => "q",
    "cag" => "q", 
    "cgu" => "r",
    "cgc" => "r",
    "cga" => "r",
    "cgg" => "r",
    "auu" => "i",
    "auc" => "i",
    "aua" => "i",
    "aug" => "m",
    "acu" => "t",
    "acc" => "t",
    "aca" => "t",
    "acg" => "t",
    "aau" => "n",
    "aac" => "n",
    "aaa" => "k",
    "aag" => "k",
    "agu" => "s",
    "agc" => "s",
    "aga" => "r",
    "agg" => "r",
    "guu" => "v",
    "guc" => "v",
    "gua" => "v",
    "gug" => "v",
    "gcu" => "a",
    "gcc" => "a",
    "gca" => "a",
    "gcg" => "a",
    "gau" => "d",
    "gac" => "d",
    "gaa" => "e",
    "gag" => "e", 
    "ggu" => "g",
    "ggc" => "g",
    "gga" => "g",
    "ggg" => "g"
  }
  CODON.default = "X"
  
  COMPLEMENT = {
    "a" => "t",
    "t" => "a",
    "c" => "g",
    "g" => "c"
  }
  COMPLEMENT.default = "X"
  
  def self.to_mrna(sequence)
    sequence.gsub("t", "u").gsub("T", "U")
  end
  
  def self.translate(sequence)
    (0...(sequence.length / 3)).inject("") { |string, codon_start| string << Translator::CODON[sequence[codon_start * 3, 3].downcase] }
  end
  
  def self.complement(sequence)
    (0...sequence.length).inject("") { |string, index| string << Translator::COMPLEMENT[sequence[index, 1].downcase] }
  end
  
  def self.collect(data, ranges, options = {})
    data = data.to_s if data.is_a? Array
    data = " " << data if options[:one_indexed]
    ranges = [ranges].flatten
    
    ranges.inject("") { |string, range| string << data[range] }
  end
  
  def self.flanking(ranges, options = {})
    flanking_ranges = ranges.inject([]) do |flankers, range|
      min, max = range.min, range.max
      flankers << Range.new(min - 2, min - 1) << Range.new(max + 1, max + 2)
    end
    options[:inner] ? flanking_ranges[1...-1] : flanking_ranges
  end
  
  def self.percent_appearance(query, data, options = {})
    data = data.to_s if data.is_a? Array
    
    # If the query is a regexp, we inspect it and strip off all but what is inside the / characters (Thus, no options are retained)
    query = query.internals if query.is_a? Regexp
    # Then we rebuild the regexp with the necessary options
    query = (options[:case_insensitive] ? Regexp.new(query, Regexp::IGNORECASE) : Regexp.new(query))
    
    matches = 0
    query.global_match(data) { matches += 1 }
    
    percent_appeared = Float(matches * query.internals.length) / Float(data.length)
    percent_appeared.nan? ? 0 : percent_appeared
  end
  
  def self.nucleotide_frequency(data, options = {})
    {
      "a" => percent_appearance("a", data, options),
      "c" => percent_appearance("c", data, options),
      "g" => percent_appearance("g", data, options),
      "t" => percent_appearance("t", data, options)
    }
  end
  
  def self.read_fa_file(file_loc)
    fa_file = File.new(file_loc)
    (data = fa_file.readlines).shift
    data = data.map { |string| string.gsub("\n", "") }.join
  end
end