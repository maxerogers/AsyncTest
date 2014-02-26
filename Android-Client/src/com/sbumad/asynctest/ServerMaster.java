package com.sbumad.asynctest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ServerMaster {
	private String url;
	private String port;
	private AsyncHttpClient client;
	private JSONArray result;
	private JSONArray availableGames;
	private String currentGameName;
	private static int currentGameId;
	private static String currentPlayerName;
	private static int currentPlayerId;
	public ServerMaster(String url, String port){
		this.url = url;
		this.port = port;
		client = new AsyncHttpClient();
		client.setBasicAuth("admin","secret");
	}
	
	//Sign In
	//POST
	public void signIn(String name){
		JSONObject playerObj = new JSONObject();
		JSONObject jsonParms = new JSONObject();
		StringEntity entity = null;
		try {
			playerObj.put("name", name);
			//playerObj.put("game_id", 15);
			//playerObj.put("id", 29);
			
			jsonParms.put("user", playerObj);
			entity = new StringEntity(jsonParms.toString());
			entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.post(null,"http://"+url+":"+port+"/api/users", entity, "application/json", new AsyncHttpResponseHandler() {
            public void onSuccess(String response) {
                Log.i("ServerMaster", "Sign In: "+response);
                try {
					JSONObject raven = new JSONObject(response);
					//
					//
					//
					currentPlayerId = (Integer) raven.get("id");
					currentPlayerName = (String) raven.get("name");
					//Log.d("SERVERMASTER","New Game Id: "+currentGameId);
				} catch (JSONException e) { 
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
	
	//FindGames
	//GET
	public void getAllGames(){
		client.get("http://"+url+":"+port+"/api/games.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray response) {
                //Log.i("MAIN", response.toString());
                result = response;
                availableGames = response;
            }
        });
	}
	//MakeGame
	//POST
	public void makeGame(){
		JSONObject gameObj = new JSONObject();
		JSONObject jsonParms = new JSONObject();
		SecureRandom random = new SecureRandom();
		try {
			gameObj.put("name", ""+new BigInteger(130, random).toString(32));
			jsonParms.put("game", gameObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringEntity entity = null;
		try {
			entity = new StringEntity(jsonParms.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		client.post(null,"http://"+url+":"+port+"/api/games", entity, "application/json", new AsyncHttpResponseHandler() {
            public void onSuccess(String response) {
                Log.i("ServerMaster", "make Game: "+response);
                try {
					JSONObject raven = new JSONObject(response);
					currentGameId =  (Integer) raven.get("id");
					currentGameName = (String) raven.getString("name");
					//Log.d("SERVERMASTER","New Game Id: "+currentGameId);
				} catch (JSONException e) { 
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
	//JoinGame(game id)
	// PUT?
	public void joinGame(int id){
		Log.i("SERVERMASTER", "Joining the Game: "+currentGameId);

		StringEntity entity = null;
		JSONObject userObj = new JSONObject();
		JSONObject jsonParms = new JSONObject();
		try {
			int x = 0;
			
			userObj.put("game_id", id);
			userObj.put("name", "Max");
			//userObj.put("id", 12);
			jsonParms.put("user", userObj);
			jsonParms.put("id", 12);
			jsonParms.put("game_id", id);
			//
			
			entity = new StringEntity(jsonParms.toString());
			entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.put(null,"http://"+url+":"+port+"/api/users/"+12+"", entity, "application/json", new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
                Log.i("ServerMaster", "Joining Game:"+response);
                //
            }
		});
	}
	//Move(player id)

}
