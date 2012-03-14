class Symbol
  def to_proc
    proc { |object, *args| object.send(self, *args) }
  end
end