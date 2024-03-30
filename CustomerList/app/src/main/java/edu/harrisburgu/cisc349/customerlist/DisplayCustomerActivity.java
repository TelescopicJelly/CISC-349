package edu.harrisburgu.cisc349.customerlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayCustomerActivity extends AppCompatActivity {

    protected static final String url = "http://10.0.0.66:5000/update";
    static CustomerListAdapter adapter;
    static RequestQueue queue;
    Context context;
    Customer customer;

    public static Intent newIntent(Context packageContext, CustomerListAdapter adapterRef, RequestQueue queue) {
        Intent i = new Intent(packageContext, DisplayCustomerActivity.class);
        adapter = adapterRef;
        DisplayCustomerActivity.queue = queue;
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_display_layout);
        context = this;

        int index = getIntent().getIntExtra(CustomerListAdapter.EXTRA_SELECTED_ITEM, -1);
        if (index >= 0)
        {
            customer = (Customer) adapter.getItem(index);
            populateView(customer);
        }
        TextView nameField = findViewById(R.id.name_view);
        TextView addressField = findViewById(R.id.address_view);
        TextView phoneField = findViewById(R.id.phone_view);
        TextView comment_text = findViewById(R.id.comment_input);

        Button addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String address = addressField.getText().toString();
                String phone = phoneField.getText().toString();
                String new_comment = comment_text.getText().toString();

                List<String> commentList = customer.getComments();
                if (commentList == null) {
                    commentList = new ArrayList<>();

                }
                commentList.add(new_comment);
                customer.setComments(commentList);


                JSONObject jobj = new JSONObject();

                try {
                    jobj.put("_id", customer.getId());
                    jobj.put("name", name);
                    jobj.put("address", address);
                    jobj.put("phone", phone);
                    JSONArray commentsArray = new JSONArray(commentList);
                    jobj.put("comments", commentsArray);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                JsonRequest jsonRequest =
                        new JsonObjectRequest(Request.Method.POST,
                                url, jobj,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                                        adapter.notifyDataSetChanged();
                                        finish();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Save Error", "Error:" + error);
                                Toast.makeText(context, "Save failed", Toast.LENGTH_SHORT)
                                        .show();
                                finish();
                            }
                        }) {
                            @Override
                            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                                // Parse network response here if necessary
                                JSONObject jsonResponse = null; // Replace null with parsed JSON object
                                return Response.success(jsonResponse, null);
                            }
                        };
                queue.add(jsonRequest);
            }
        });
    }

    protected void populateView(Customer customer)
    {
        TextView t = findViewById(R.id.name_view);
        t.setText(customer.getName());
        t = findViewById(R.id.address_view);
        t.setText(customer.getAddress());
        t = findViewById(R.id.phone_view);
        t.setText(customer.getPhone());

        ListView l = findViewById(R.id.commentListView);
        ArrayAdapter<String> array = new ArrayAdapter<>(this, R.layout.comment_item_layout, customer.getComments());
        l.setAdapter(array);
    }
}
