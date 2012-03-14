class AddColumnsToProducts < ActiveRecord::Migration
  def self.up
    add_column :products, :brand,	      :string
    add_column :products, :availability,      :integer
    add_column :products, :reorder_quantity,  :integer
    add_column :products, :our_price,	      :decimal
  end

  def self.down
    remove_column :products, :brand
    remove_column :products, :availability
    remove_column :products, :reorder_quantity
    remove_column :products, :our_price
  end
end
