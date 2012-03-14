class CreateNetworks < ActiveRecord::Migration
  def self.up
    create_table :networks do |t|
      t.string :protein_name
      t.integer :protein_id
      t.decimal :ds_score_1
      t.decimal :ds_score_2
      t.decimal :ds_score_3
      t.decimal :ds_score_4

      t.timestamps
    end
  end

  def self.down
    drop_table :networks
  end
end
