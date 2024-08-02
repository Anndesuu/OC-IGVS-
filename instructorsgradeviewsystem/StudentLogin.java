package com.example.instructorsgradeviewsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentLogin extends AppCompatActivity {

    EditText stud_no, pass_w;
    String stud;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        stud_no = (EditText)findViewById(R.id.studentno);
        pass_w = (EditText) findViewById(R.id.password_s);

        Button stb = (Button) findViewById(R.id.student_button);
        Button create = (Button) findViewById(R.id.create_acc);
        stud = stud_no.getText().toString();
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);
        stb.setText("Log-In");

        stb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
//                OnLogin(v);
                startActivity(new Intent(StudentLogin.this, WelcomePage.class));
            }

        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (StudentLogin.this, CreateStudentAcct.class));
            }
        });

    }
    public void OnLogin(View view) {
        String s_num = stud_no.getText().toString();
        String p_word = pass_w.getText().toString();
        String type = "studentlogin";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this, progress);
        backgroundWorker.execute(type, s_num, p_word);


    }
}