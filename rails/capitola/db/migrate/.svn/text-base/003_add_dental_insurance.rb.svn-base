class AddDentalInsurance < ActiveRecord::Migration
  def self.up
    add_column :medical_histories, :account_owner, :string
    add_column :medical_histories, :owners_relation_to_patient, :string
    add_column :medical_histories, :owners_insurance_co, :string
    add_column :medical_histories, :owners_group_number, :integer
    add_column :medical_histories, :patient_has_additional_insurance, :boolean
    add_column :medical_histories, :subscriber_name, :string
    add_column :medical_histories, :subscriber_birthday, :date
    add_column :medical_histories, :subscriber_social, :integer
    add_column :medical_histories, :subscriber_relationship_to_patient, :string
    add_column :medical_histories, :subscriber_insurance_co, :string
    add_column :medical_histories, :subscriber_group_number, :integer
  end

  def self.down
    remove_column :medical_histories, :account_owner
    remove_column :medical_histories, :owners_relation_to_patient
    remove_column :medical_histories, :owners_insurance_co
    remove_column :medical_histories, :owners_group_number
    remove_column :medical_histories, :patient_has_additional_insurance
    remove_column :medical_histories, :subscriber_name
    remove_column :medical_histories, :subscriber_birthday
    remove_column :medical_histories, :subscriber_social
    remove_column :medical_histories, :subscriber_relationship_to_patient
    remove_column :medical_histories, :subscriber_insurance_co
    remove_column :medical_histories, :subscriber_group_number
  end
end
