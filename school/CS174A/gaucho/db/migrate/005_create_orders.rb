class CreateOrders < ActiveRecord::Migration
  def self.up
    create_table :orders do |t|
      t.string :name
      t.string :email
      t.text :address
      t.string :pay_type, :limit => 10

      t.timestamps
    end
  end

  def self.down
    drop_table :orders
  end
end
