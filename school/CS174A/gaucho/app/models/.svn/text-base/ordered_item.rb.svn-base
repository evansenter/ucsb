class OrderedItem < ActiveRecord::Base
  belongs_to :order
  belongs_to :product

  def self.from_cart_item(cart_item)
    an_item = self.new
    an_item.product, an_item.quantity, an_item.total_price = cart_item.product, cart_item.quantity, cart_item.price
    an_item
  end
end
