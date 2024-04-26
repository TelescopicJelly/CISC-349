package edu.harrisburgu.cisc349.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddNewUserActivity extends AppCompatActivity {

    EditText usernameField;
    EditText passwordField;
    Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        mSaveButton = (Button) findViewById(R.id.save_button);
        usernameField = findViewById(R.id.username_input);
        passwordField = findViewById(R.id.password_input);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                ArrayList<String> NewUser = new ArrayList<>();

                String data = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", username, password);
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(data);
                    jobj.put("NewUserList",new JSONArray(NewUser));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JSONObject finalJobj = jobj;
                //JSONArrayRequest
                JsonRequest jsonRequest =
                        new JsonObjectRequest(Request.Method.POST,
                                "http://192.168.10.63:5000/add", finalJobj,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(AddNewUserActivity.this, "Success!", Toast.LENGTH_SHORT)
                                                .show();
                                        finish();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Log.d("Save Error", "Error:" + error);
                                Toast.makeText(AddNewUserActivity.this, "Save failed", Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            }

                        }) {
                            @Override
                            protected Response parseNetworkResponse (NetworkResponse response){
                                String data = new String(response.data);
                                Response<JSONObject> res = null;
                                try {
                                    JSONObject json = new JSONObject(data);
                                    res = Response.success(json, null);
                                    Log.d("Login", "parseNetworkResponse called");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                return res;
                            }
                            };
                queue.add(jsonRequest);
                        }
            });
    }
}
