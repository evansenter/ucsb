require 'rubygems'
require 'open-uri'
require 'hpricot'

def parse_search(query)
  query.gsub(" ", "+")
end

def scrape_page(query)
  Hpricot(open("http://www.ultimate-guitar.com/search.php?s=#{parse_search(query[:item])}&w=#{query[:type]}"))
end

query = {:item => "jack", :type => "songs"}
puts "http://www.ultimate-guitar.com/search.php?s=#{parse_search(query[:item])}&w=#{query[:type]}"
((scrape_page(query))/"/html/body/").search("//table").select {|table| table.inner_html.to_s.include? query[:item]}.each {|table| puts table.search("//a").select {|link| (link = link.to_s).include? query[:item] \
  and link.include? "tabs"}}
