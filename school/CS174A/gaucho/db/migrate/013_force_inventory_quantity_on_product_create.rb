class ForceInventoryQuantityOnProductCreate < ActiveRecord::Migration
  def self.up
    change_column :products, :availability,	:integer, :null => false
    change_column :products, :reorder_quantity, :integer, :default => 0
  end

  def self.down
    change_column :products, :availability,      :integer
    change_column :products, :reorder_quantity,  :integer
  end
end
