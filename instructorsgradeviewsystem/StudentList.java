package com.example.instructorsgradeviewsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Item> items = new ArrayList<Item>();

    private static final String Studenturl = "https://geloyujin.000webhostapp.com/studentlist.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));

        loadStudentlist();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void loadStudentlist() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Studenturl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray studentlist = new JSONArray(response);

                            for(int i = 0; i< studentlist.length(); i++){
                                JSONObject StudentObject = studentlist.getJSONObject(i);

                                String name = StudentObject.getString("name");
                                String studentnum = StudentObject.getString("student_num");

                                Item item = new Item(name, studentnum);
                                items.add(item);
                            }

                            MyAdapter adapter = new MyAdapter(StudentList.this, items);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
    }
