class AddPhoneNumbers < ActiveRecord::Migration
  def self.up
    add_column :medical_histories, :home_phone, :string
    add_column :medical_histories, :work_phone, :string
    add_column :medical_histories, :cell_phone, :string
    add_column :medical_histories, :spouse_work_phone, :string
    add_column :medical_histories, :availability, :string
    add_column :medical_histories, :emergency_name, :string
    add_column :medical_histories, :emergency_relationship, :string
    add_column :medical_histories, :emergency_home_phone, :string
    add_column :medical_histories, :emergency_work_phone, :string
  end

  def self.down
    remove_column :medical_histories, :home_phone
    remove_column :medical_histories, :work_phone
    remove_column :medical_histories, :cell_phone
    remove_column :medical_histories, :spouse_work_phone
    remove_column :medical_histories, :availability
    remove_column :medical_histories, :emergency_name
    remove_column :medical_histories, :emergency_relationship
    remove_column :medical_histories, :emergency_home_phone
    remove_column :medical_histories, :emergency_work_phone
  end
end
