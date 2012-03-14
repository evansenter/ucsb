class RefactorNetwork < ActiveRecord::Migration
  def self.up
    change_column :networks, :ds_score_1, :float
    change_column :networks, :ds_score_2, :float
    change_column :networks, :ds_score_3, :float
    change_column :networks, :ds_score_4, :float
  end

  def self.down
    change_column :networks, :ds_score_1, :decimal
    change_column :networks, :ds_score_2, :decimal
    change_column :networks, :ds_score_3, :decimal
    change_column :networks, :ds_score_4, :decimal
  end
end
