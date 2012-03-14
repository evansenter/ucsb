class CreateProteinNames < ActiveRecord::Migration
  def self.up
    create_table :protein_names do |t|
      t.string :name
      t.integer :protein_id

      t.timestamps
    end
  end

  def self.down
    drop_table :protein_names
  end
end
