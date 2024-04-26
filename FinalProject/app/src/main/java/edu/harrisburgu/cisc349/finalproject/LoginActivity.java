package edu.harrisburgu.cisc349.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    private Button mSignUpButton;
    EditText usernameLog;
    EditText passwordLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mSignUpButton = (Button) findViewById(R.id.signup_button);

        usernameLog = (EditText) findViewById(R.id.username);
        passwordLog = (EditText) findViewById(R.id.password);

        // Sign up button functionality
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,AddNewUserActivity.class);
                startActivity(intent);
            }
        });


        // Login button functionality
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameLog.getText().toString();
                String password = passwordLog.getText().toString();

                String data = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", username, password);


                //JSONArrayRequest
                JsonRequest jsonRequest =
                        new JsonRequest(Request.Method.POST,
                                "http://192.168.10.63:5000/login", data,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            boolean success = response.getBoolean("login");
                                            Log.d("JSONObject Response", "Success: " +
                                                    response);

                                            if (success) {
                                                Toast.makeText(v.getContext(), R.string.success, Toast.LENGTH_SHORT)
                                                        .show();
                                                Intent intent = new Intent(LoginActivity.this,GamePlayActivity.class);
                                                startActivity(intent);

                                            } else {
                                                Toast.makeText(v.getContext(), R.string.failure, Toast.LENGTH_SHORT)
                                                        .show();
                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Log.d("JSON Error", "Error:" + error);
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
