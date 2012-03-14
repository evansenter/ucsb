class CreateCrawlers < ActiveRecord::Migration
  def self.up
    create_table :crawlers do |t|
      t.string :search

      t.timestamps
    end
  end

  def self.down
    drop_table :crawlers
  end
end
