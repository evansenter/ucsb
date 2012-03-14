class CommentsController < ApplicationController
  def create
    @comment = Comment.new(params[:comment])

    respond_to do |format|
      flash[:notice] = "Comment was successfully added." if @comment.save
      format.html { redirect_to(:controller => :posts, :action => :show, :id => @comment.post_id) }
    end
  end
end
