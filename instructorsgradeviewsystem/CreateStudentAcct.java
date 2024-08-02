package com.example.instructorsgradeviewsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CreateStudentAcct extends AppCompatActivity {

    EditText sname, snum, pass1, pass2;
    Button create;
    ProgressBar progress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student_acct);

        sname = findViewById(R.id.studentname);
        snum = findViewById(R.id.studentno);
        pass1 = findViewById(R.id.set_password1);
        pass2 = findViewById(R.id.set_password2);
        create = findViewById(R.id.createaccount);
        progress1 = findViewById(R.id.progress1);

        String password1 = pass1.getText().toString();
        String password2 = pass2.getText().toString();


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password1.equals(password2)) {
                    progress1.setVisibility(View.VISIBLE);
                    Studentreg(v);
                }
                else{
                    Toast.makeText(CreateStudentAcct.this, "Password doesn't match",Toast.LENGTH_SHORT).show();
                }


            }
        });
        }

    public void Studentreg (View view) {
        String studentname = sname.getText().toString();
        String studentnum = snum.getText().toString();
        String pword= pass2.getText().toString();
        String type = "StudentReg";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this, progress1);
        backgroundWorker.execute(type, studentname, studentnum, pword);



    }
}