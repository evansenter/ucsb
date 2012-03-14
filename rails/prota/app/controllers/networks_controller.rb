class NetworksController < ApplicationController

  def index
    @protein_pairs = Network.find(:all)

    respond_to do |format|
      format.html
    end
  end
end
