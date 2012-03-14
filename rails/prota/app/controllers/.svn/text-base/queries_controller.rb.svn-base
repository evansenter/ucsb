class QueriesController < ApplicationController
  # GET /queries
  # GET /queries.xml
  def index
    @queries = Query.find(:all)

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @queries }
    end
  end

  # GET /queries/1
  # GET /queries/1.xml
  def show
    @query = Query.find(params[:id])
    @proteins = Network.find_by_sql(@query.protein_conditions)

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @query }
    end
  end

  def histogram
    @root_id = params[:id]

    respond_to do |format|
      format.html # histogram.html.erb
    end
  end

  # GET /queries/new
  # GET /queries/new.xml
  def new
    @query = Query.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @query }
    end
  end

  # GET /queries/1/edit
  def edit
    @query = Query.find(params[:id])
  end

  # Return the a hash of the ds_enabled and ds_weight values (with the possibility of other ds params as well /ds_\d+_.+/) only
  def hashify_datasources
    params.reject {|key, value| key.to_s.match(/ds_\d+_.+/) == nil}
  end

  # Builds the hash needed for Query.new
  def form_query
    {:protein_id_list => params[:protein_ids].gsub(" ", ""), :datasources => hashify_datasources}
  end

  # POST /queries
  # POST /queries.xml
  def create
    @query = Query.new(form_query)

    respond_to do |format|
      if @query.save
        flash[:notice] = 'Query was successfully created.'
        format.html { redirect_to(@query) }
        format.xml  { render :xml => @query, :status => :created, :location => @query }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @query.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /queries/1
  # PUT /queries/1.xml
  def update
    @query = Query.find(params[:id])

    respond_to do |format|
      if @query.update_attributes(params[:query])
        flash[:notice] = 'Query was successfully updated.'
        format.html { redirect_to(@query) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @query.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /queries/1
  # DELETE /queries/1.xml
  def destroy
    @query = Query.find(params[:id])
    @query.destroy

    respond_to do |format|
      format.html { redirect_to(queries_url) }
      format.xml  { head :ok }
    end
  end
end
