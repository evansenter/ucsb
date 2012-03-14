class Query < ActiveRecord::Base
  validates_presence_of :datasources, :protein_id_list
  validates_format_of :protein_id_list, 
			:with => /((\d+|\d+-\d+),)*(\d+|\d+-\d+)/, 
			:message => "does not match the required range syntax" 

  def before_validation
    normalize_query_weights_and_jsonify
  end

  # Normalizes the weights specified by the user and then converts the hash to a json string
  def normalize_query_weights_and_jsonify
    total_weight = 0
    (hash = self.datasources).select {|key, value| key.match(/ds_\d+_weight/) and self.datasources["ds_#{key.slice(/\d+/)}_enabled"] == "1"}.each {|key_value_array| total_weight += key_value_array[1].to_f}
    (1..(Network.columns.select {|col| col.name.include? "ds_score"}.size)).each {|i| hash["ds_#{i}_weight"] = hash["ds_#{i}_weight"].to_f / total_weight}
    self.datasources = hash.to_json
  end

  # Returns an array of Fixnums or Ranges of proteins to include
  def parse_protein_ids
    protein_id_list.split(",").map {|token| token.match(/\d+-\d+/) ? ((token.to_i)..(token.sub(/\d+-/, "").to_i)) : token.to_i}
  end

  # Builds an SQL query corresponding to the protein / gene ids requested by the user and their first neighbors
  def protein_conditions
    conditions = "SELECT * FROM networks WHERE "
    (1..2).each do |i|
      parse_protein_ids.each do |id_elem|
	conditions << "protein_#{i}_id " << (id_elem.class == Range ? "BETWEEN #{id_elem.first} AND #{id_elem.last}" : "= #{id_elem}") << " OR " 
      end
    end
    conditions[0, conditions.length - " OR ".length]
  end

  def ds_params_hash
    ActiveSupport::JSON::decode(datasources)
  end
end
