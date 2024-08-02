package com.example.instructorsgradeviewsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GE08CS extends AppCompatActivity {
    Button submit, studentlist, masterlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge08_cs);

        submit = (Button) findViewById(R.id.submitgrades);
        masterlist = (Button) findViewById(R.id.masterlist);
        studentlist = (Button) findViewById(R.id.studentlist);

        studentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GE08CS.this, StudentList.class));
            }
        }) ;

        masterlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GE08CS.this, MasterList.class);
                intent.putExtra("subject","ge");
                startActivity(intent);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GE08CS.this, UploadedGradeList.class);
                intent.putExtra("subject","ge");
                startActivity(intent);
            }
        });

    }
}