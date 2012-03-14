class AddAttrToQueries < ActiveRecord::Migration
  def self.up
    add_column :queries, :datasources, Hash
    add_column :queries, :protein_id_list, Array
  end

  def self.down
    remove_column :queries, :datasources
    remove_column :datasources, :protein_id_list
  end
end
