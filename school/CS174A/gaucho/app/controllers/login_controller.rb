class LoginController < ApplicationController
  before_filter :authorize, :except => [:login, :add_user]
  
  layout "login"

  def add_user
    @user = User.new(params[:user])
    if request.post? and @user.save
      flash.now[:notice] = "User #{@user.name} created successfully"
      @user = User.new  
    end
  end

  def login
    session[:user_id] = nil
    if request.post?
      user = User.authenticate(params[:name], params[:password])
      if user
	session[:user_id] = user.id
	uri = session[:original_uri]
	session[:original_uri] = nil
	redirect_to(uri || { :action => "index" })	
      else
	flash[:notice] = "Invalid username/password combination"
      end
    end
  end

  def logout
    session[:user_id] = nil
    flash[:notice] = "You have logged out successfully! Yay!"
    redirect_to(:controller => :products, :action => :index)
  end

  def index
    @all_orders, @total_orders, @total_users = Order.find(:all), Order.count, User.count
  end

  def edit_order
    @order = Order.find(params[:id])
  end

  def update_order
    @order = Order.find(params[:id])
    respond_to do |format|
      if @order.update_attributes(params[:order])
        flash[:notice] = 'Order was successfully updated.'
        format.html { redirect_to(:action => :index) }
        format.xml  { head :ok }
      else
	format.html { render :action => "edit_order" }
        format.xml  { render :xml => @order.errors, :status => :unprocessable_entity }
      end
    end
  end

  def adjust_quantity
    @new_quantity = (@ordered_item = OrderedItem.find(params[:id])).quantity + params[:adjustment].to_i
    respond_to do |format|
      if (@product = Product.find(@ordered_item.product_id)).availability > OrderedItem.find(:all, :conditions => ["product_id = ?", @ordered_item.product_id]).sum(&:quantity) or @new_quantity < @ordered_item.quantity
	@ordered_item.update_attribute(:quantity, @new_quantity) 
	@ordered_item.update_attribute(:total_price, @ordered_item.quantity * @product.price) 
	if @ordered_item.quantity <= 0: OrderedItem.delete(@ordered_item.id) end
	Order.delete(@ordered_item.order_id) if OrderedItem.find(:all, :conditions => ["order_id = ?", @ordered_item.order_id]).sum(&:quantity) <= 0 and request.post?
	flash[:notice] = "#{Product.find(@ordered_item.product_id).title} quantity was successfully updated."
	format.html { redirect_to(:action => :index) }
	format.xml  { head :ok }
      else
	flash[:notice] = "There are not enough of those items in inventory to adjust your order, please try again soon."
	format.html { redirect_to(:action => :index) }
        format.xml  { render :xml => @ordered_item.errors, :status => :unprocessable_entity }
      end
    end
  end

  def delete_user
    User.find(params[:id]).destroy if request.post?
    redirect_to(:action => :list_users)
  end

  def list_users
    @all_users = User.find(:all)
  end

  def give_admin
    (@user = User.find(params[:id])).update_attribute(:admin, (@user.admin ? 0 : 1)) if request.post?
    redirect_to(:action => :list_users)
  end
end
