package com.example.instructorsgradeviewsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WelcomePage extends AppCompatActivity {

    Button ths, ge;
    TextView loginname;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        loginname = (TextView) findViewById(R.id.studentloginname);
        ths = (Button) findViewById(R.id.buttonths);
        ge = (Button) findViewById(R.id.buttonge);


        Intent intent = getIntent();
        String receivedString = intent.getStringExtra("name");

        loginname.setText(receivedString);


        ths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, GradeView.class);
                intent.putExtra("subject", "ths");
                intent.putExtra("name", receivedString);
                startActivity(intent);
            }
        });

        ge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, GradeView.class);
                intent.putExtra("subject", "ge");
                intent.putExtra("name", receivedString);
                startActivity(intent);
            }
        });
    }
}