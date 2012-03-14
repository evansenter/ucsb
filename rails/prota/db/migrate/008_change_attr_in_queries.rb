class ChangeAttrInQueries < ActiveRecord::Migration
  def self.up
    drop_table(:queries)
    create_table :queries do |t|
      t.string :protein_id_list, :required => true
      t.string :datasources, :required => true

      t.timestamps
    end
  end

  def self.down
    raise ActiveRecord::IrreversibleMigration
  end
end
