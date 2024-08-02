package com.example.instructorsgradeviewsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

public class InstructorLog extends AppCompatActivity {

    EditText username, password;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_log);


        username = (EditText) findViewById(R.id.username_ins);
        password = (EditText) findViewById(R.id.password_ins);
        String message = getIntent().getStringExtra("ALERT_DIALOG_MESSAGE");
        progress = findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);


        Button login = (Button) findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = username.getText().toString();
                progress.setVisibility(View.VISIBLE);
                Log.d("GradeView", aa + " ");
                Log.d("Password:", password.getText().toString() + "");
                OnLogin(v);
            }
        });
    }

    public void OnLogin(View view) {
        String u_name = username.getText().toString();
        String p_word = password.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this, progress);
        backgroundWorker.execute(type, u_name, p_word);
    }
}
