module StoreHelper
  
  def hidden_div_if(condition, attributes = {})
    attributes["style"] = "display: none" if condition
    "<div #{tag_options(attributes.stringify_keys)}>"
  end
end
