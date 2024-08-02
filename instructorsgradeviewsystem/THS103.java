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

public class THS103 extends AppCompatActivity {

    Button submit, studentlist, masterlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ths103);

        submit = (Button) findViewById(R.id.submitgrades);
        masterlist = (Button) findViewById(R.id.masterlist);
        studentlist = (Button) findViewById(R.id.studentlist);

        studentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(THS103.this, StudentList.class));
            }
        }) ;

        masterlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(THS103.this, MasterList.class);
                intent.putExtra("subject","ths");
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(THS103.this, UploadedGradeList.class);
                intent.putExtra("subject","ths");
                startActivity(intent);

            }
        });

    }
    }