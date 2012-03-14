def neighbors(node, num_nodes)
  (1..num_nodes).each do |i|
    start = 0
  end
end

def get_index(node_1, node_2, num_nodes)
  if node_1 < node_2 and node_1 < num_nodes and node_2 <= num_nodes
    line = 1
    (1...node_1).each {|i| line += num_nodes - i}
    line += node_2 - node_1 - 1
    puts line
  end
end

get_index(1, 2, 5)
get_index(1, 3, 5)
get_index(1, 4, 5)
get_index(1, 5, 5)
get_index(2, 3, 5)
