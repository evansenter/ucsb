class Product < ActiveRecord::Base
  has_many :ordered_items

  validates_presence_of :title, :brand, :description, :price, :our_price, :availability, :reorder_quantity, :image_url
  validates_uniqueness_of :title
  validates_numericality_of :price, :our_price
  validates_numericality_of :availability, :reorder_quantity,
			    :only_integer => :true
  validates_format_of :image_url,
		      :with => %r{\.(gif|jpg|png)$}i,
		      :message => "must be a GIF, JPG or PNG file"

  def self.find_products_for_sale
    find(:all, :order => "title")
  end

  def short_description
    description.length > 100 ? description[0, 100] << "..." : description
  end
  
  def each
    yield self
  end

  protected
  def validate
    errors.add(:price, "must be at least 0.01") if price.nil? || price < 0.01
    errors.add(:our_price, "must be at least 0.01") if our_price.nil? || our_price < 0.01
    errors.add(:availibibly, "must be at least 1") if availability.nil? || availability < 1
    errors.add(:reorder_quantity, "must be greater than or equal to 0") if reorder_quantity.nil? || reorder_quantity < 0
  end
end
