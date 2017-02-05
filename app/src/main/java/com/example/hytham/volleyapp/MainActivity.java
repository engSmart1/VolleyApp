package com.example.hytham.volleyapp;

import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Util.AppController;

public class MainActivity extends ActionBarActivity {
    private String imageUrl = "http://i.imgur.com/7spzG.png";
    private String stringUrl = "https://magadistudio.com/complete-android-developer-course-source-files/string.html";
    private String url = "http://jsonplaceholder.typicode.com/comments";
    private String currencyUrl ="https://openexchangerates.org/api/latest.json?app_id=80136b98290c40918f2c2c22bdb8fd8b";
    private TextView mTextView;
    private NetworkImageView networkImageView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getJSONArray(url);
        mTextView = (TextView) findViewById(R.id.currencyText);
        networkImageView = (NetworkImageView) findViewById(R.id.imageView);
        imageView = (ImageView) findViewById(R.id.myImageView);

        getImage(imageUrl);
        getImageOldWay(imageUrl);

        //getJSONObject(currencyUrl);
        getStringObject(stringUrl);


    }
    private void getImageOldWay(String url){
        ImageLoader imageLoader = AppController.getInstance().getImageLouder();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
             imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } );
    }
    private void getImage(String url){
        ImageLoader imageLoader = AppController.getInstance().getImageLouder();
        networkImageView.setImageUrl(url ,imageLoader);

    }
    private void getStringObject(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("MY RESPONSE" , response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest);

    }
    private void getJSONObject(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject ratesObject = response.getJSONObject("rates");

                    String currency  = ratesObject.getString("BOB");
                    //Log.v("CURRENCY" , currency);
                    mTextView.setText("BOB: " + currency);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }
    private void getJSONArray(String url){
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Log.d("Data from the web: " , response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String mId = String.valueOf(id);

                        String body = jsonObject.getString("body");
                        Log.d("All Id Now ", mId + "\n" + " Body Now! " + body);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MainActivity", error.getMessage());
            }
        });
        //AppController.getmInstance().addToRequestQueue(request);

    }
}
