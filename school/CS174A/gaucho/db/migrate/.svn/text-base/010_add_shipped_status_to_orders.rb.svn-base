class AddShippedStatusToOrders < ActiveRecord::Migration
  def self.up
    add_column :orders, :shipped_status, :boolean
  end

  def self.down
    remove_column :orders, :shipped_status
  end
end
