class Regexp
  def global_match(string, &proc)
    # http://grokmonkey.blogspot.com/2008/04/global-match-for-ruby.html
    return_value = nil
    loop do
      result = string.sub(self) do |m|
        proc.call($~) # pass MatchData object.
        ''
      end
      break return_value if result == string
      string = result
      return_value ||= true
    end
  end
  
  def internals
    inspect.gsub(/^\w*\/|\/\w*$/, "")
  end
end