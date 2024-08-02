package com.example.instructorsgradeviewsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradedUploader extends AppCompatActivity {

    String receivedString, a;
    TextView sub, textView;
    LinearLayout addimage, resetButton;
    ImageView imageView;
    Button reset, submit;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Uri> imageList;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String url, encodeImg;
    Bitmap bitmap;
    List<Bitmap> photos;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graded_uploader);

        sub = (TextView) findViewById(R.id.textV);
        addimage = (LinearLayout) findViewById(R.id.addimage);
        resetButton = (LinearLayout) findViewById(R.id.btnreset);
//        imageView = (ImageView) findViewById(R.id.image);
        reset = (Button)findViewById(R.id.my_button);
        submit = (Button)findViewById(R.id.submit);
        progress = (ProgressBar)findViewById(R.id.progress);
        textView = (TextView)findViewById(R.id.textView10);
        a = "0";


        Intent intent = getIntent();
        receivedString = intent.getStringExtra("sub");
        if (receivedString.equals("ths")){
            url = "https://geloyujin.000webhostapp.com/uploadgrades.php";
            sub.setText("Upload Grades (THS 103)");
        }
        else if (receivedString.equals("ge")){
            url = "https://geloyujin.000webhostapp.com/uploadgradesge.php";
            sub.setText("Upload Grades (GE 08CS)");
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageList = new ArrayList<>();
        photos = new ArrayList<>();

        imageAdapter = new ImageAdapter(imageList);
        recyclerView.setAdapter(imageAdapter);

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    openFileChooser();
                } else {
                    requestStoragePermission();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos.clear();
                imageList.clear();
                imageAdapter.notifyDataSetChanged();
                a = "0";
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a.equals("1")) {
//                    progress.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    if (!photos.isEmpty()) {
                        for (Bitmap bitmap : photos) {
                            encodeBitmapImg(bitmap);
                            uploaddatatodb();
//                            OnGrades(v);

                        }
                    } else {
                        Toast.makeText(GradedUploader.this, "No selected photos!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
//    public void OnGrades(View view) {
//        String t1 = receivedString;
//        String submit = encodeImg;
//        String type = "grades";
//        BackgroundWorker backgroundWorker = new BackgroundWorker(this, progress);
//        backgroundWorker.execute(type,  t1, submit);
//
//    }
    private void uploaddatatodb() {
        String t1 = receivedString;
        String encodedImg = encodeImg;
        url = "https://geloyujin.000webhostapp.com/uploadgrades.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("t1", receivedString);
                Log.d("encodedImg", encodedImg);
                progress.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                imageList.clear();
                imageAdapter.notifyDataSetChanged();
                a = "0";
                Toast.makeText(GradedUploader.this, "File Uploaded Successfully", Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GradedUploader.this, "File Upload Unsuccessful", Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("t1", t1);
                map.put("submit", encodedImg);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                3,
                2.0f
        ));
        // Create a request queue and add the request to the queue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private boolean isStoragePermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUrl = data.getData();
            imageList.add(imageUrl);
            imageAdapter.notifyDataSetChanged();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUrl);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();

                if (bitmap != null) {
                    photos.add(bitmap);
                } else {
                    Log.e("MasterListUpload", "Bitmap is null after decoding");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("MasterListUpload", "Error decoding bitmap from URI: " + e.getMessage());
            }
            a = "1";
        }
    }

    private void encodeBitmapImg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImg = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFileChooser();
            } else {
                // Permission denied. Handle accordingly.
            }
        }
    }

}
