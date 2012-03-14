# Makes a file of ascending contiguous pairwise id combinations with a user specified number of node ids
file = File.open(ARGV[0], "w")
(1..ARGV[1].to_i).each {|i| (i+1..ARGV[1].to_i).each {|j| file << "#{i} #{j}\n"}}
