class SwitchEndedAtToDatetime < ActiveRecord::Migration
  def self.up
    change_column :work_sessions, :ended_at, :datetime
  end

  def self.down
    raise ActiveRecord::IrreversibleMigration
  end
end
