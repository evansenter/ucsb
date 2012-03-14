class Histogram < ActiveRecord::Base
  validates_presence_of :elem_id, :datasource, :num_groups
  validates_numericality_of :num_groups, :only_integer => true, :greater_than => 0, :message => "must be a whole number greater than zero"

  def before_validation
    self.max_score, self.min_score = Network.maximum("ds_score_#{datasource}"), Network.minimum("ds_score_#{datasource}")
    self.step_size = (max_score - min_score) / num_groups
  end
 
  def create_histogram
    table = get_first_row
    2.times {next_row table}
    table
  end

  # Creates the row of the histogram with dist_thresh = 1
  def get_first_row
    (1..num_groups).map {|i| [Subgraph.new(elem_id, datasource, max_score - step_size * i, 1)]}
  end

  # Creates a new row in the histogram off of the last one by calling Subgraph::expand on a copy of each previous graph
  # FIX: This Marshal thing is a hack, it should be changed
  def next_row(table)
    table.each {|bin| bin << Marshal.load(Marshal.dump(bin.last)).expand}
    table.select {|bin| bin.last.edges.size == bin[-2].edges.size}.size == table.size
  end
end
