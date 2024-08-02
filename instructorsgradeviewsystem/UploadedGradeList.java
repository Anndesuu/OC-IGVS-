package com.example.instructorsgradeviewsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UploadedGradeList extends AppCompatActivity {

    ImageView menu;
    TextView sub;
    String receivedString, del_url, fetch_url;
    RecyclerView recyclerView;
    MasterListAdapter imageAdapter;
    List<Model> imageList;
    Model model;
    Button refresh;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_grade_list);

        menu = (ImageView) findViewById(R.id.menubtn);
        sub = (TextView) findViewById(R.id.textV);
        refresh = (Button) findViewById(R.id.refresh);
        progress = (ProgressBar) findViewById(R.id.progress);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        imageList = new ArrayList<>();
        imageAdapter = new MasterListAdapter(this, imageList);
        recyclerView.setAdapter(imageAdapter);

        getImage();

        Intent intent = getIntent();
        receivedString = intent.getStringExtra("subject");
        if (receivedString.equals("ths")) {
            sub.setText("Grades (THS 103)");
        } else if (receivedString.equals("ge")) {
            del_url = "https://geloyujin.000webhostapp.com/gradesdeletege.php";
            fetch_url = "https://geloyujin.000webhostapp.com/fetchg_ge.php";
            sub.setText("Grades (GE 08CS)");
        }


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, receivedString);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });


    }


    private void getImage() {
        String url1 = "https://geloyujin.000webhostapp.com/fetch_grades.php?";
        String url = url1 + "param=" + receivedString;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                     JSONObject jsonObject =  new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray= jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for (int i = 0; i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String url2 = object.getString("image");
                            String urlImage = "https://geloyujin.000webhostapp.com/grades/"+url2;
                            model = new Model(urlImage);
                            imageList.add(model);
                            imageAdapter.notifyDataSetChanged();
                            progress.setVisibility(View.INVISIBLE);


                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void deleteData() {
        String DELETE_URL = "https://geloyujin.000webhostapp.com/delete_grades.php?";
        String deleteUrl = DELETE_URL + "param=" + receivedString;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, deleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imageList.clear();
                        imageAdapter.notifyDataSetChanged();
                        Toast.makeText(UploadedGradeList.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UploadedGradeList.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottombox2);

        LinearLayout yes = dialog.findViewById(R.id.linear1);
        LinearLayout no = dialog.findViewById(R.id.linear2);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.Animeshon;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }



    public void showPopup(View v, String receivedString) {
        PopupMenu popup = new PopupMenu(UploadedGradeList.this, v);
        popup.getMenuInflater().inflate(R.menu.grades, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.item1) {
                    Intent intent = new Intent(UploadedGradeList.this, GradedUploader.class);
                    intent.putExtra("sub", receivedString);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.item2) {
                    showDialog();
                }

                return true;
            }
        });
        popup.show();
    }
}