class ProteinName < ActiveRecord::Base
  validates_presence_of :name, :protein_id
end
