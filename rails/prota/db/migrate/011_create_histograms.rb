class CreateHistograms < ActiveRecord::Migration
  def self.up
    create_table :histograms do |t|
      t.integer :elem_id
      t.integer :datasource
      t.float :max_score
      t.float :min_score
      t.integer :num_groups

      t.timestamps
    end
  end

  def self.down
    drop_table :histograms
  end
end
