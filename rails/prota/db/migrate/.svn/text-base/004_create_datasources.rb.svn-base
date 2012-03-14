class CreateDatasources < ActiveRecord::Migration
  def self.up
    create_table :datasources do |t|
      t.string :name
      t.boolean :enabled

      t.timestamps
    end
  end

  def self.down
    drop_table :datasources
  end
end
