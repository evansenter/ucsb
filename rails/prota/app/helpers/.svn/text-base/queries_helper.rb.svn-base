module QueriesHelper

  # Builds a single row for the table of datasources, and adjusts the scores shown in the view by their weights, and appends the sum
  def protein_weights(protein_pair, datasources)
    total_score = 0
    (1..num_datasources).map do |i|
      "<td>" << if datasources["ds_#{i}_enabled"] == "1"
	total_score += (protein_pair["ds_score_#{i}"] *= datasources["ds_#{i}_weight"])
	protein_pair["ds_score_#{i}"].to_s
      else
	"Omitted"
      end << "</td>"
    end << "<td>#{total_score}</td>"
  end
end
