package edu.harrisburgu.cisc349.cameratest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import android.content.Intent;

import android.provider.MediaStore;

import android.view.View;

import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    BitmapDrawable drawable;
    RequestQueue queue;
    EditText commentEditText, nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button capture = findViewById(R.id.camara_button);
        imageView = findViewById(R.id.camaraImageView);
        commentEditText = findViewById(R.id.comment);
        nameEditText = findViewById(R.id.name);

        queue = Volley.newRequestQueue(this);
        queue.start();


// When clicking the capture button
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CameraTest", "in Click");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

// When clicking on view images button
        Button list = findViewById(R.id.list_button);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CameraTest", " in list Click");
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });

// When clicking upload button
        Button upload = findViewById(R.id.upload_button);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);

                String comment = "Users comment";
                String name = "Users name";

                String dateTime = getCurrentDateTime();

                drawable = (BitmapDrawable) imageView.getDrawable();
                final Bitmap bitmap = drawable.getBitmap();

                String encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100);

                uploadToServer(encodedImage, name, comment, dateTime);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality){
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

// Uploading images and comments to mongodb
    private void uploadToServer(final String image, final String name, final String comment, final String dateTime) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("comment", comment);
            json.put("dateTime", dateTime);
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://192.168.10.63:5000/image";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Hello", "Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Hello", "Response: " +error.getMessage());
            }
        });

        queue.add(jsonObjectRequest);
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void uploadImage() {
        if (imageView.getDrawable() != null) {
            String comment = commentEditText.getText().toString();
            String name = nameEditText.getText().toString();

            String dateTime = getCurrentDateTime();
            Log.d("MainActivity", "DateTime: " + dateTime); // Add this log statement

            drawable = (BitmapDrawable) imageView.getDrawable();
            final Bitmap bitmap = drawable.getBitmap();
            String encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100);
            Toast.makeText(this, "Your Picture was Uploaded", Toast.LENGTH_SHORT).show();


            if (!comment.isEmpty()) {
                uploadToServer(encodedImage, name, comment, dateTime);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a comment", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Must take picture first", Toast.LENGTH_SHORT).show();
        }
    }
}
