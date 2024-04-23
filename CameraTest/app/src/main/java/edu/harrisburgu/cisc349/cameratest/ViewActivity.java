package edu.harrisburgu.cisc349.cameratest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        list = (ListView) findViewById(R.id.listView);

        ArrayList<ImageData> results = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.10.63:5000/images";

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject imageData = response.getJSONObject(i);
                                    String imageString = imageData.getString("image");
                                    String comment = imageData.getString("comment");
                                    String name = imageData.getString("name");
                                    String dateTime = imageData.getString("dateTime");

                                    byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                                    Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


                                    Log.d("JSON Response", "Response: " + response.toString());



                                    results.add(new ImageData(image, comment, name, dateTime));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d("ViewActivity", "Result size " + results.size());
                            PictureListAdapter adapter = new PictureListAdapter(list.getContext(), results);
                            list.setAdapter(adapter);
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
