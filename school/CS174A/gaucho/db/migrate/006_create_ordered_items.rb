class CreateOrderedItems < ActiveRecord::Migration
  def self.up
    create_table :ordered_items do |t|
      t.integer :product_id, :null => false
      t.integer :order_id, :null => false
      t.integer :quantity, :null => false
      t.decimal :total_price, :null => false, :precision => 8, :scale => 2

      t.timestamps
    end    
  end

  def self.down
    drop_table :ordered_items
  end
end
