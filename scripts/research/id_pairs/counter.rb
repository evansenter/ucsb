# Tracks the number of occurences of ids in a file
values = {}
File.open(ARGV[0]).each_line do |line|
	index = line.to_i
	values[index].nil? ? values[index] = 1 : values[index] += 1
end
values.sort.each {|data_pair| print data_pair[0].to_s << " " << data_pair[1].to_s << "\n"}
