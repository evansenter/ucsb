class RemoveTimeWorkedFromWorkSessions < ActiveRecord::Migration
  def self.up
    remove_column :work_sessions, :time_worked
  end

  def self.down
    create_column :work_sessions, :time_worked, :integer
  end
end
