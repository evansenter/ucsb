prot = "1,  2, 3-10,14    "
new = nil
if prot.gsub!(" ", "").match(/((\d+|\d+-\d+),)*(\d+|\d+-\d+)/).to_s.length == prot.length: new = prot.split(",").map {|token| token.match(/\d+-\d+/) ? ((token.to_i)..(token.sub(/\d+-/, "").to_i)) : token.to_i} end
p new.class
