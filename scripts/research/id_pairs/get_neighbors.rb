# Returns the ids of the nodes on a given line, assuming ascending contiguous pairwise combinations and a given number of unique ids
def get_nodes(line, num_nodes)
  node_1, node_2, loc = 1, 2, 1
  while loc != line
    if node_2 > num_nodes: node_2 = (node_1 += 1) + 1 end
    if node_1 > num_nodes: return end
    node_2, loc = node_2 + 1, loc + 1
  end
  puts [node_1, node_2]
end

# Returns the line corresponding to the provided ids, assuming ascending contiguous pairwise combinations and a given number of unique ids
def get_index(node_1, node_2, num_nodes)
  if node_1 < node_2 and node_1 < num_nodes and node_2 <= num_nodes
    line = 1
    (1...node_1).each {|i| line += num_nodes - i}
    line += node_2 - node_1 - 1
    puts line
  end
end

while loc = gets
  get_nodes(loc.split[0].to_i, loc.split[1].to_i)
  #get_index(loc.split[0].to_i, loc.split[1].to_i, loc.split[2].to_i)
  puts "Done"
end
