require File.dirname(__FILE__) + '/../test_helper'

class WorkSessionsControllerTest < ActionController::TestCase
  def test_should_get_index
    get :index
    assert_response :success
    assert_not_nil assigns(:work_sessions)
  end

  def test_should_get_new
    get :new
    assert_response :success
  end

  def test_should_create_work_session
    assert_difference('WorkSession.count') do
      post :create, :work_session => { }
    end

    assert_redirected_to work_session_path(assigns(:work_session))
  end

  def test_should_show_work_session
    get :show, :id => work_sessions(:one).id
    assert_response :success
  end

  def test_should_get_edit
    get :edit, :id => work_sessions(:one).id
    assert_response :success
  end

  def test_should_update_work_session
    put :update, :id => work_sessions(:one).id, :work_session => { }
    assert_redirected_to work_session_path(assigns(:work_session))
  end

  def test_should_destroy_work_session
    assert_difference('WorkSession.count', -1) do
      delete :destroy, :id => work_sessions(:one).id
    end

    assert_redirected_to work_sessions_path
  end
end
