class Order < ActiveRecord::Base
  has_many :ordered_items

  PAYMENT_TYPES = [
    ["Check",		"check"],
    ["Credit card",	"cc"],
    ["Purchase order",	"po"]
  ]

  # TO DO: VALIDATE FORMAT OF EMAIL
  validates_presence_of :name, :address, :email, :pay_type
  validates_inclusion_of :pay_type, :in => PAYMENT_TYPES.map {|displayed, value| value}

  def add_ordered_items_from_cart(cart)
    cart.items.each do |item|
     an_item = OrderedItem.from_cart_item(item)
     ordered_items << an_item
    end
  end

  def after_update
    if self.shipped_status
      Product.find(OrderedItem.find_by_order_id(self.id).product_id).each do |product|
	product.availability -= OrderedItem.find(:first, :conditions => ["product_id = ? and order_id = ?", product.id, self.id]).quantity
	product.save
      end
    end
  end
end
