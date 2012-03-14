require File.dirname(__FILE__) + '/../test_helper'

class HistogramsControllerTest < ActionController::TestCase
  def test_should_get_index
    get :index
    assert_response :success
    assert_not_nil assigns(:histograms)
  end

  def test_should_get_new
    get :new
    assert_response :success
  end

  def test_should_create_histogram
    assert_difference('Histogram.count') do
      post :create, :histogram => { }
    end

    assert_redirected_to histogram_path(assigns(:histogram))
  end

  def test_should_show_histogram
    get :show, :id => histograms(:one).id
    assert_response :success
  end

  def test_should_get_edit
    get :edit, :id => histograms(:one).id
    assert_response :success
  end

  def test_should_update_histogram
    put :update, :id => histograms(:one).id, :histogram => { }
    assert_redirected_to histogram_path(assigns(:histogram))
  end

  def test_should_destroy_histogram
    assert_difference('Histogram.count', -1) do
      delete :destroy, :id => histograms(:one).id
    end

    assert_redirected_to histograms_path
  end
end
