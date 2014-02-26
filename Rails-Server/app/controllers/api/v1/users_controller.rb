module Api
  module V1
    class UsersController < ApplicationController
      #skip_before_filter :verify_authenticity_token
      http_basic_authenticate_with name: "admin", password: "secret"
      #before_filter :restrict_access
      
      respond_to :json

      def index
        
        respond_with User.all
      end

      def show
        respond_with User.find(params[:id])
      end

      def create
        
        respond_with User.create(params[:user])
      end

      def update
        logger.info "\n USER BUG CATCHER\n"
        logger.info params.inspect
        logger.info "\n USER BUG CATCHER\n"
        @user = User.find(params[:id])
        @user.game_id = params[:game_id]
        @user.save
        logger.info "\n USER BUG CATCHER\n"
        logger.info @user.inspect
        logger.info "\n USER BUG CATCHER\n"
        respond_with User.find(params[:id])
      end

      def destroy
        respond_with User.destroy(params[:id])
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