module Api
  module V1
    class GamesController < ApplicationController
      #skip_before_filter :verify_authenticity_token
      http_basic_authenticate_with name: "admin", password: "secret"
      #before_filter :restrict_access
      
      respond_to :json

      def index
        
        respond_with Game.all
      end

      def show
        respond_with Game.find(params[:id])
      end

      def create
        
        respond_with Game.create(params[:game])
      end

      def update
        respond_with Game.update(params[:id], params[:game])
      end

      def destroy
        respond_with Game.destroy(params[:id])
      end
      
    private
    
      # def restrict_access
      #   api_key = ApiKey.find_by_access_token(params[:access_token])
      #   head :unauthorized unless api_key
      # end
    
      def restrict_access
        authenticate_or_request_with_http_token do |token, options|
          ApiKey.exists?(access_token: token)
        end
      end
    end
  end
end