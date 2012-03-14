class Post < ActiveRecord::Base
  has_many :comments
  
  validates_presence_of :title, :body

  def before_validation_on_create
    self.tags = "default" unless attribute_present?("tags")
    self.tags.gsub!(" ", ",")
  end
end
