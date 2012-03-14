class WorkSession < ActiveRecord::Base
  SECONDS_TO_HOURS = 3600.0 # (60 sec / min) * (60 min / hour)
  
  validates_presence_of :name, :project

  def time_worked
    open ? nil : (ended_at.to_i - created_at.to_i) / SECONDS_TO_HOURS
  end

  def time_worked_as_s
    time_worked.nil? ? "hours pending" : time_worked.to_s.match(/\d+.\d\d/).to_s << " hours"
  end
end
