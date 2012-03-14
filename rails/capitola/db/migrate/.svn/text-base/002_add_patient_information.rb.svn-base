class AddPatientInformation < ActiveRecord::Migration
  def self.up
    add_column :medical_histories, :patient_id, :integer
    add_column :medical_histories, :patient_name, :string
    add_column :medical_histories, :patient_address, :string
    add_column :medical_histories, :patient_city, :string
    add_column :medical_histories, :patient_state, :string
    add_column :medical_histories, :patient_zip, :integer
    add_column :medical_histories, :patient_email, :string
    add_column :medical_histories, :patient_sex, :string
    add_column :medical_histories, :patient_birthdate, :date
    add_column :medical_histories, :patient_relationship_status, :string
    add_column :medical_histories, :patient_occupation, :string
  end

  def self.down
    remove_column :medical_histories, :patient_id
    remove_column :medical_histories, :patient_name
    remove_column :medical_histories, :patient_address
    remove_column :medical_histories, :patient_city
    remove_column :medical_histories, :patient_state
    remove_column :medical_histories, :patient_zip
    remove_column :medical_histories, :patient_email
    remove_column :medical_histories, :patient_sex
    remove_column :medical_histories, :patient_birthdate
    remove_column :medical_histories, :patient_relationship_status
    remove_column :medical_histories, :patient_occupation
  end
end
