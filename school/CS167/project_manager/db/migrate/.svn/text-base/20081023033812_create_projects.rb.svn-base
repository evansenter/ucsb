class CreateProjects < ActiveRecord::Migration
  def self.up
    create_table :projects do |t|
      t.string :title
      t.text :abstract
      t.integer :milestone_id
      t.text :data_sources
      t.text :references

      t.timestamps
    end
  end

  def self.down
    drop_table :projects
  end
end
