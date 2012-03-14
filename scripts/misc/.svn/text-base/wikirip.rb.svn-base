require 'rubygems'
require 'hpricot'
require 'open-uri'

def stripper(query)
  ((data = (Hpricot(open("http://en.wikipedia.org/wiki/#{(query[0, 1].capitalize! << query[1, query.length - 1]).gsub(" ", "_")}"))/"/html/body/div/div/div/div[2]/p").first.to_s.gsub(/<\/?[^>]*>|\[\d+\]/, "")).include? "may refer to:") ? data.split(',')[0] : data
end

puts "What treasures do you want?"
puts stripper gets
