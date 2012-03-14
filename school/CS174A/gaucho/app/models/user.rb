require 'digest/sha1'

class User < ActiveRecord::Base
  attr_accessor :password_confirmation

  validates_presence_of	:name
  validates_uniqueness_of :name
  validates_confirmation_of :password

  def validate
    errors.add_to_base("Missing password") if hashed_password.blank?
  end
  
  def password
    @password
  end

  def password=(pass)
    @password = pass
    create_new_salt
    self.hashed_password = User.encrypted_password(self.password, self.salt)
  end

  def self.authenticate(name, password)
    if (user = self.find_by_name(name))
      expected_password = encrypted_password(password, user.salt)
      user = nil if user.hashed_password != expected_password
    end
    user
  end

  def after_destroy
    raise "Can't delete the last user" if User.count.zero?
  end
  
  private
  
  def self.encrypted_password(password, salt)
    Digest::SHA1.hexdigest(password + "Mineralsalz" + salt)
  end
  
  def create_new_salt
    self.salt = self.object_id.to_s + rand.to_s
  end
end
