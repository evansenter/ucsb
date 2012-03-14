# Methods added to this helper will be available to all templates in the application.
module ApplicationHelper
  def highlight(text, lang = "ruby")
    text = (text.gsub(/<code>.+<\/code>/m) {|inner_code| code(inner_code.gsub(/(<code>\s*|<\/code>)/m, ""), :theme => "twilight", :line_numbers => true, :lang => lang)} or text)
    #(Hpricot(text)/"code").each {|chunk| code(chunk.inner_html, :theme => "twilight", :line_numbers => true, :lang => lang)}
  end
end
