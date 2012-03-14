class MedicalHistoriesController < ApplicationController
  # GET /medical_histories
  # GET /medical_histories.xml
  def index
    @medical_histories = MedicalHistory.find(:all)

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @medical_histories }
    end
  end

  # GET /medical_histories/1
  # GET /medical_histories/1.xml
  def show
    @medical_history = MedicalHistory.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @medical_history }
    end
  end

  # GET /medical_histories/new
  # GET /medical_histories/new.xml
  def new
    @medical_history = MedicalHistory.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @medical_history }
    end
  end

  # GET /medical_histories/1/edit
  def edit
    @medical_history = MedicalHistory.find(params[:id])
  end

  # POST /medical_histories
  # POST /medical_histories.xml
  def create
    @medical_history = MedicalHistory.new(params[:medical_history])

    respond_to do |format|
      if @medical_history.save
        flash[:notice] = 'MedicalHistory was successfully created.'
        format.html { redirect_to(@medical_history) }
        format.xml  { render :xml => @medical_history, :status => :created, :location => @medical_history }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @medical_history.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /medical_histories/1
  # PUT /medical_histories/1.xml
  def update
    @medical_history = MedicalHistory.find(params[:id])

    respond_to do |format|
      if @medical_history.update_attributes(params[:medical_history])
        flash[:notice] = 'MedicalHistory was successfully updated.'
        format.html { redirect_to(@medical_history) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @medical_history.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /medical_histories/1
  # DELETE /medical_histories/1.xml
  def destroy
    @medical_history = MedicalHistory.find(params[:id])
    @medical_history.destroy

    respond_to do |format|
      format.html { redirect_to(medical_histories_url) }
      format.xml  { head :ok }
    end
  end
end
