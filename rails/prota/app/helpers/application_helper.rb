# Methods added to this helper will be available to all templates in the application.
module ApplicationHelper

  # List cycler
  def list_cycle
    cycle("list_odd", "list_even")
  end

  # Add an ellipsis to a string if more than 100 chars long
  def shortened_string(string)
    string[0, 100] << "..." unless string.length < 100
  end

  # Quick little check of how many columns of data there are in the Network (By naming convention ds_score_\d+)
  def num_datasources
    Network.columns.select {|col| col.name.include? "ds_score"}.size
  end

  # Returns the largest node id (In column protein_1_id only!) from the network
  def max_node_id
    (elem_1_id = Network.maximum("protein_1_id")) > (elem_2_id = Network.maximum("protein_2_id")) ? elem_1_id : elem_2_id
  end

  # Generates a random 3 letter string
  def random_name
    (1..3).map {"" << rand(26) + 65}.to_s
  end
end
