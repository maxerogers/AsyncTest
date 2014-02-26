require 'api_constraints'

Store::Application.routes.draw do
  resources :games

  resources :users

  namespace :api, defaults: {format: 'json'} do
    scope module: :v1, constraints: ApiConstraints.new(version: 1, default: true) do
      resources :products
      resources :games
      resources :users
    end
  end

  resources :products
  root to: 'products#index'
end
