class ProteinNamesController < ApplicationController
  def index
    @protein_names = ProteinName.find(:all)

    respond_to do |format|
      format.html # index.html.erb
    end
  end
end
