module PostsHelper
  def clean_tags(post = nil)
    (post ? post : @post).tags.gsub(",", ", ")
  end
end
