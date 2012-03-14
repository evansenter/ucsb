require 'rubygems'
require 'hpricot'
require 'open-uri'

class Crawler < ActiveRecord::Base
  validate_presence_of :search
  
  def initialize
    @doc = Hpricot(open("http://www.google.com/products?q=#{search}&btnG=Search+Products&hl=en")) 
  end

  def get_results

  end
end
