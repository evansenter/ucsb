require File.dirname(__FILE__) + '/../test_helper'

class CrawlersControllerTest < ActionController::TestCase
  def test_should_get_index
    get :index
    assert_response :success
    assert_not_nil assigns(:crawlers)
  end

  def test_should_get_new
    get :new
    assert_response :success
  end

  def test_should_create_crawler
    assert_difference('Crawler.count') do
      post :create, :crawler => { }
    end

    assert_redirected_to crawler_path(assigns(:crawler))
  end

  def test_should_show_crawler
    get :show, :id => crawlers(:one).id
    assert_response :success
  end

  def test_should_get_edit
    get :edit, :id => crawlers(:one).id
    assert_response :success
  end

  def test_should_update_crawler
    put :update, :id => crawlers(:one).id, :crawler => { }
    assert_redirected_to crawler_path(assigns(:crawler))
  end

  def test_should_destroy_crawler
    assert_difference('Crawler.count', -1) do
      delete :destroy, :id => crawlers(:one).id
    end

    assert_redirected_to crawlers_path
  end
end
