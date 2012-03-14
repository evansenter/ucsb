class CreateWorkSessions < ActiveRecord::Migration
  def self.up
    create_table :work_sessions do |t|
      t.string :name
      t.string :project
      t.decimal :ended_at
      t.integer :time_worked

      t.timestamps
    end
  end

  def self.down
    drop_table :work_sessions
  end
end
