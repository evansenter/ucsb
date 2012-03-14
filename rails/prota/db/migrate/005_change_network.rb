class ChangeNetwork < ActiveRecord::Migration
  def self.up
    remove_column :networks, :protein_name
    remove_column :networks, :protein_id
    add_column :networks, :protein_1_id, :integer
    add_column :networks, :protein_2_id, :integer
  end

  def self.down
    raise ActiveRecord::IrreversibleMigration
  end
end
