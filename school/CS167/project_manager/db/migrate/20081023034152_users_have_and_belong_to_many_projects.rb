class UsersHaveAndBelongToManyProjects < ActiveRecord::Migration
  def self.up
    create_table(:projects_users, :id => false) do |t|
      t.column :project_id, :integer
      t.column :user_id, :integer
    end
    
    add_index :projects_users, [:project_id, :user_id], :unique => true
  end

  def self.down
    drop_table :projects_users
  end
end
