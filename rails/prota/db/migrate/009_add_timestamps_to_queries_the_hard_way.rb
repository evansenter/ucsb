class AddTimestampsToQueriesTheHardWay < ActiveRecord::Migration
  def self.up
    drop_table(:queries)
    create_table :queries do |t|
      t.string :protein_id_list, :required => true
      t.string :datasources, :required => true

      t.timestamps
    end
  end

  def self.down
    remove_column :queries, :created_at
    remove_column :queries, :updated_at
  end
end
