class FormatTimeWorked < ActiveRecord::Migration
  def self.up
    change_column :work_sessions, :time_worked, :decimal, :precision => 4, :scale => 2
  end

  def self.down
    raise ActiveRecord::IrreversibleMigration
  end
end
