module WorkSessionsHelper

  def total_hours_worked
    total_hours_worked = 0
    WorkSession.find(:all, :conditions => ["open = ?", false]).each {|session| total_hours_worked += session.time_worked}
    total_hours_worked.to_s.match(/\d+\.\d\d/)
  end
end
