package edu.harrisburgu.cisc349.dynamic_list_view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected static final String url = "https://nua.insufficient-light.com/data/holiday_songs_spotify.json";

    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        ArrayList<HolidaySongs> results = new ArrayList<HolidaySongs>();

        RequestQueue queue = Volley.newRequestQueue(this);
        //queue.start();

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        HolidaySongs album = new HolidaySongs(response.getJSONObject(i));
                                        results.add(album);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                HolidaySongsAdapter adapter = new HolidaySongsAdapter(listView.getContext(), results, queue);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(adapter);

                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("JSONArray Error", "Error:" + error);
                    }
                });
        queue.add(jsonArrayRequest);
    }
}