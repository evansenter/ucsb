class ModifyWorkSessionsTable < ActiveRecord::Migration
  def self.up
    change_column :work_sessions, :time_worked, :datetime
    add_column :work_sessions, :open, :boolean, :default => true
  end

  def self.down
    raise ActiveRecode::IrreversibleMigration
  end
end
