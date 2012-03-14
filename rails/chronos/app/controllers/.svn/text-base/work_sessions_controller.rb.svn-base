class WorkSessionsController < ApplicationController
  # GET /work_sessions
  # GET /work_sessions.xml
  def index
    @work_session, @work_sessions = WorkSession.new, WorkSession.find(:all)

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @work_sessions }
    end
  end

  # GET /work_sessions/1
  # GET /work_sessions/1.xml
  def show
    @work_session = WorkSession.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @work_session }
    end
  end

  # GET /work_sessions/new
  # GET /work_sessions/new.xml
  def new
    @work_session = WorkSession.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @work_session }
    end
  end

  # GET /work_sessions/1/edit
  def edit
    @work_session = WorkSession.find(params[:id])
  end

  # POST /work_sessions
  # POST /work_sessions.xml
  def create
    @work_session = WorkSession.new(params[:work_session])

    respond_to do |format|
      if @work_session.save
        flash[:notice] = 'Task was successfully created.'
        format.html { redirect_to(@work_session) }
        format.xml  { render :xml => @work_session, :status => :created, :location => @work_session }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @work_session.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /work_sessions/1
  # PUT /work_sessions/1.xml
  def update
    @work_session = WorkSession.find(params[:id])

    respond_to do |format|
      if @work_session.update_attributes(params[:work_session])
        flash[:notice] = 'Task was successfully updated.'
        format.html { redirect_to(@work_session) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @work_session.errors, :status => :unprocessable_entity }
      end
    end
  end
  
  def close
    (@work_session = WorkSession.find(params[:id])).update_attributes({ :ended_at => Time.now, :open => false })
    flash[:notice] = "Current work on project #{@work_session.project} has been closed, #{@work_session.time_worked_as_s} hours logged."
    redirect_to(:action => :index)
  end

  # DELETE /work_sessions/1
  # DELETE /work_sessions/1.xml
  def destroy
    @work_session = WorkSession.find(params[:id])
    @work_session.destroy

    respond_to do |format|
      format.html { redirect_to(work_sessions_url) }
      format.xml  { head :ok }
    end
  end
end
