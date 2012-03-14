class Subgraph
  attr_accessor :edges, :vertices, :score_thresh, :dist_thresh

  # Sets up class variables and queries the database with generate_subgraph(@root_id)
  def initialize(root_id, datasource, score_thresh, dist_thresh)
    @vertices, @apical_vertices, @datasource, @score_thresh, @dist_thresh = [] << (@root_id = root_id), [], datasource, score_thresh, dist_thresh
    (@edges = generate_subgraph(@root_id)).each {|edge| add_vertices_from(edge)}
  end

  def empty?
    @edges.empty?
  end

  # Generates a query that either searches for edges above a certain threshold or within a certain range, and returns the results.
  def generate_subgraph(center_id)
    conditions = "SELECT * FROM networks WHERE ds_score_#{@datasource} " << (@score_thresh.class == Range ? "BETWEEN #{@score_thresh.first} AND #{@score_thresh.last}" : ">= #{@score_thresh}") << " AND "
    (1..2).each {|i| conditions << "protein_#{i}_id = #{center_id} OR "}
    Network.find_by_sql conditions[0, conditions.length - " OR ".length]
  end

  # Takes all of this Subgraph's edges, and adds their neighbors above threshold (In effect increasing the distance threshold), returns self for chaining
  def expand
    expanding_vertices, @apical_vertices, @dist_thresh = @apical_vertices.clone, [], @dist_thresh.next
    expanding_vertices.each {|vertex| merge_with_edges(generate_subgraph(vertex))}
    self
  end

  # Pass this a list of edges (Network), add to this graph if they don't already exist
  # Possible optimization would be to check only the edge ids for the include? statement
  def merge_with_edges(edges)
    edges.each {|edge| @edges << add_vertices_from(edge) unless @edges.include? edge}
  end

  # If an edge is added to this graph by means of merge_with_edges, new vertices are added to the vertex list
  def add_vertices_from(edge)
    unless (@vertices << (@apical_vertices << edge.protein_1_id).last unless @vertices.include? edge.protein_1_id)
      @vertices << (@apical_vertices << edge.protein_2_id).last unless @vertices.include? edge.protein_2_id
    end
    edge
  end
end
