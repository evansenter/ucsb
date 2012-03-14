# This script takes two arguements, a file to read from and a file to write to.
# It iterates through the first file and identifies all the unique scores, and sorts them
# in ascending order. It then remaps the decimal score to an integer encoding, and all null
# values become -1. In the end you are left with a new file of integer scores and a transform 
# file with the mappings between decimal and integer values. Next one can run convert_data_to_bin.c 
# on the new file to get a binary file of the edge weights.
start_time = Time.now

ARGV.each do |data_file|
  values, current = [], 1
  # Collect all unique scores
  (original_data = File.open(data_file)).each_line do |line|
    values << line unless values.include? line or line =~ /null/
    current += 1
    puts "Read line #{current}" if current % 1000000 == 0
  end
  puts "There are #{values.size} unique scores in the file #{data_file}, remapping to file #{"int_" << data_file}"
  new_data, mapping = File.open("int_" << data_file, "w"), File.open("" << data_file << "_transform", "w")
  # Sort scores by value and close old file before reopening
  values.sort! {|a, b| a.to_f <=> b.to_f}
  original_data.close
  # For each line in the score file, if it's null we write a '-1', otherwise we write the index that score is at in the sorted array
  current = 1
  (original_data = File.open(data_file)).each_line  do |line| 
    line =~ /null/ ? new_data.write("-1\n") : new_data.write(values.index(line).to_s << "\n")
    current += 1
    puts "Wrote line #{current}" if current % 1000000 == 0
  end
  # Append the null information into our transformation file, for clarity
  mapping.write("null -1\n")
  # And then write the sorted array, and the corresponding integer
  values.each_index {|index| mapping.write "#{values[index].gsub("\n", "")} #{index}\n"}
  original_data.close
  new_data.close
  mapping.close
end

puts "Task complete, runtime: #{(Time.now - start_time) / 60} minutes."
