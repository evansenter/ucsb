# Methods added to this helper will be available to all templates in the application.
module ApplicationHelper
  
  def admin?
    session[:user_id] != nil and User.find(session[:user_id]).admin ? true : false
  end
end
