# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of ActiveRecord to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 13) do

  create_table "datasources", :force => true do |t|
    t.string   "name"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "datasource_id"
  end

  create_table "dictionaries", :force => true do |t|
    t.string   "protein_name"
    t.integer  "protein_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "histograms", :force => true do |t|
    t.integer  "elem_id"
    t.integer  "datasource"
    t.float    "max_score"
    t.float    "min_score"
    t.integer  "num_groups"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.float    "step_size"
  end

  create_table "networks", :force => true do |t|
    t.float    "ds_score_1"
    t.float    "ds_score_2"
    t.float    "ds_score_3"
    t.float    "ds_score_4"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "protein_1_id"
    t.integer  "protein_2_id"
  end

  create_table "protein_names", :force => true do |t|
    t.string   "name"
    t.integer  "protein_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "queries", :force => true do |t|
    t.string   "protein_id_list"
    t.string   "datasources"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

end
