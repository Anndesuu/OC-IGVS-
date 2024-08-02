package com.example.instructorsgradeviewsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AddUsers extends AppCompatActivity {

    String username, password;
    EditText usern, passw;
    Button addauth_user;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);


        usern = findViewById(R.id.set_username);
        passw = findViewById(R.id.set_password);
        addauth_user = findViewById(R.id.adduser);
        progress = findViewById(R.id.progress2);
        addauth_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            progress.setVisibility(View.VISIBLE);
                OnAddUser(v);
            }
        });

    }

    public void OnAddUser(View view) {
        String username = usern.getText().toString();
        String password = passw.getText().toString();
        String type = "adduser";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this, progress);
        backgroundWorker.execute(type, username, password);

    }
}