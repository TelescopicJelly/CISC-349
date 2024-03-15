package edu.harrisburgu.cisc349.walmart_storelocations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    protected static final String url = "https://nua.insufficient-light.com/data/walmart_store_locations.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.start();

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try{
                                        JSONObject obj = response.getJSONObject(i);
                                        String state = obj.getString("state");
                                        if (state.endsWith("PA"))
                                        {
                                            Store store = new Store(response.getJSONObject(i));
                                                Log.d("Store Error", "Error:" + store);
                                                Fragment fragment = new StoreFragment(store);
                                                fm.beginTransaction()
                                                        .add(R.id.fragmentContainer, fragment)
                                                        .commit();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSONArray Error", "Error:" + error);
                    }
                });
        // Add the request to the RequestQueue
        Log.d("Store Error", "adding queue");
        queue.add(jsonArrayRequest);
    }
}