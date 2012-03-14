class Datasource < ActiveRecord::Base
  validates_presence_of :datasource_id, :name
  validates_numericality_of :datasource_id, :integer_only => true, :greater_than => 0, :message => "must be a whole number greater than zero"
end
