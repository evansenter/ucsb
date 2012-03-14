class HistogramsController < ApplicationController
  # GET /histograms
  # GET /histograms.xml
  def index
    @histograms = Histogram.find(:all)

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @histograms }
    end
  end

  # GET /histograms/1
  # GET /histograms/1.xml
  def show
    @histogram = Histogram.find(params[:id])
    @table = @histogram.create_histogram

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @histogram }
    end
  end

  # GET /histograms/new
  # GET /histograms/new.xml
  def new
    @histogram = Histogram.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @histogram }
    end
  end

  # GET /histograms/1/edit
  def edit
    @histogram = Histogram.find(params[:id])
  end

  # POST /histograms
  # POST /histograms.xml
  def create
    @histogram = Histogram.new(params[:histogram])

    respond_to do |format|
      if @histogram.save
        flash[:notice] = 'Histogram was successfully created.'
        format.html { redirect_to(@histogram) }
        format.xml  { render :xml => @histogram, :status => :created, :location => @histogram }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @histogram.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /histograms/1
  # PUT /histograms/1.xml
  def update
    @histogram = Histogram.find(params[:id])

    respond_to do |format|
      if @histogram.update_attributes(params[:histogram])
        flash[:notice] = 'Histogram was successfully updated.'
        format.html { redirect_to(@histogram) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @histogram.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /histograms/1
  # DELETE /histograms/1.xml
  def destroy
    @histogram = Histogram.find(params[:id])
    @histogram.destroy

    respond_to do |format|
      format.html { redirect_to(histograms_url) }
      format.xml  { head :ok }
    end
  end
end
