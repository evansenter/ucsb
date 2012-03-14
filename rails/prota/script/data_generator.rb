(1..25).each {|i| ((i+1)..25).each {|j| Network.create(:ds_score_1 => rand, :ds_score_2 => rand, :ds_score_3 => rand, :ds_score_4 => rand, :protein_1_id => i, :protein_2_id => j)}}
