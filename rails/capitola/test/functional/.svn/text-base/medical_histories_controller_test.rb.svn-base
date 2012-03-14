require File.dirname(__FILE__) + '/../test_helper'

class MedicalHistoriesControllerTest < ActionController::TestCase
  def test_should_get_index
    get :index
    assert_response :success
    assert_not_nil assigns(:medical_histories)
  end

  def test_should_get_new
    get :new
    assert_response :success
  end

  def test_should_create_medical_history
    assert_difference('MedicalHistory.count') do
      post :create, :medical_history => { }
    end

    assert_redirected_to medical_history_path(assigns(:medical_history))
  end

  def test_should_show_medical_history
    get :show, :id => medical_histories(:one).id
    assert_response :success
  end

  def test_should_get_edit
    get :edit, :id => medical_histories(:one).id
    assert_response :success
  end

  def test_should_update_medical_history
    put :update, :id => medical_histories(:one).id, :medical_history => { }
    assert_redirected_to medical_history_path(assigns(:medical_history))
  end

  def test_should_destroy_medical_history
    assert_difference('MedicalHistory.count', -1) do
      delete :destroy, :id => medical_histories(:one).id
    end

    assert_redirected_to medical_histories_path
  end
end
