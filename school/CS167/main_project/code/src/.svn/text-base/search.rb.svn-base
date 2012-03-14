print "Searching for matches in directory: #{(root = Dir.new(ARGV.first)).path}\n"

sequest_files = root.select { |file| file =~ /.*\.out/ }
print "Found #{sequest_files.length} files, attempting to parse.\n"

max_length_seen = 0
max_file = nil

sequest_files.each do |file|
  lines = File.new(File.join(root.path, file)).readlines
  primary_match = lines[15].strip
  if primary_match[0..1] == "1."
    sequence = primary_match.split(" ").last
    if sequence.length > max_length_seen
      max_length_seen = sequence.length
      max_file = file
    end
  end
end

print "The longest sequence (length: #{max_length_seen}) is in: #{File.join(root.path, max_file)}\n"
print "#{File.new(File.join(root.path, max_file)).readlines[15]}\n"