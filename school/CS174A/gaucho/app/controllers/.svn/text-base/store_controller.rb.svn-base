class StoreController < ApplicationController 
  before_filter :find_cart, :except => :empty_cart  

  def index
    @products = Product.find_products_for_sale
  end

  def add_to_cart
    begin
      product = Product.find(params[:id])
    rescue ActiveRecord::RecordNotFound
      logger.error("Attempt to access invalid product #{params[:id]}") 
      redirect_to_index("invalid product")
    else
      @current_item = @cart.add_product(product)   
      redirect_to_index unless request.xhr?
    end
  end

  def empty_cart
    session[:cart] = nil
    redirect_to_index
  end

  def checkout
    @cart.items.empty? ? redirect_to_index("Your cart is empty") : @order = Order.new
  end

  def commit_order
    (@order = Order.new(params[:order])).add_ordered_items_from_cart(@cart)
    @order.shipped_status = false
    if @order.save
      session[:cart] = nil
      redirect_to_index("Thank you for your order")
    else
      render :action => :checkout
    end
  end

  private
  def find_cart
    @cart = (session[:cart] ||= Cart.new)
  end

  def redirect_to_index(message = nil)
    flash[:notice] = message if message
    redirect_to :action => :index
  end
end
