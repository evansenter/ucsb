class AddDatasourceIdToDatasources < ActiveRecord::Migration
  def self.up
    remove_column :datasources, :enabled
  end

  def self.down
    add_column :datasources, :enabled, :boolean
  end
end
