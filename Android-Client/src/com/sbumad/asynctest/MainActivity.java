package com.sbumad.asynctest;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class MainActivity extends Activity {
	private String url = "142.157.11.37";
	private String port = "3000";
	public static boolean isAlive = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MAIN","Attempting");
        
        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        client.get("http://"+url+":"+port+"/api/products", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                //Log.i("MAIN",response);
            }
        });
        Log.i("MAIN","Attempting To Get JSON");
        client.get("http://"+url+":"+port+"/api/products.json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray timeline) {
                //Log.i("MAIN", timeline.toString());
            }
        });
        ServerMaster sm = new ServerMaster("142.157.11.37", "3000");
       
        try {
        	 sm.signIn("MAXIMUS");
			Thread.sleep(2000);
			sm.getAllGames();
			Thread.sleep(2000);
	        sm.makeGame();
	        Thread.sleep(2000);
	        sm.joinGame(15);
	        Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //
        //
        //Log.i("MAIN", sm.getAllGames().toString());

        /*
        RequestParams params = new RequestParams();
       // params.put("utf8", "✓");
        JSONObject paramsJSON = new JSONObject();
        JSONObject product = new JSONObject();
        
        try {
			product.put("name", "asdfasdfasfdasdfasfd");
			product.put("price","312523452346");
			product.put("released_on(1i)","2014");
			product.put("released_on(2i)","2");
			product.put("released_on(3i)","23");
			paramsJSON.put("product", product);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        StringEntity entity = null;
        try {
        	entity = new StringEntity(paramsJSON.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //
        client.setBasicAuth("admin","secret");
        Log.i("MAIN","POSTING");
        client.post(null,"http://"+url+":"+port+"/api/products", entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                //Log.i("MAIN","POST: "+response);
            }
        });
        //
        //
        
        Timer t = new Timer();  
        
        TimerTask task = new TimerTask() {  
        
         @Override  
         public void run() {  
          runOnUiThread(new Runnable() {  
        
           @Override  
           public void run() {  
               AsyncHttpClient client = new AsyncHttpClient();
               client.setBasicAuth("admin","secret");
        	   client.get("http://"+url+":"+port+"/api/products", new AsyncHttpResponseHandler() {
                   @Override
                   public void onSuccess(String response) {
                       Log.i("MAIN",response);
                   }
               });
        	   Log.d("MAIN","\n\n\n");
           }  
          });  
         }  
        };  
        
        t.scheduleAtFixedRate(task, 0, 1000);  
        */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
