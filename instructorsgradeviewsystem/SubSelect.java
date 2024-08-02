package com.example.instructorsgradeviewsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SubSelect extends AppCompatActivity {
    private Button ths, ge;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_select);


        ths = findViewById(R.id.thesisbutton);
        ths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubSelect.this, THS103.class));
            }
        });

        ge = findViewById(R.id.gebutton);
        ge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubSelect.this, GE08CS.class));
            }
        });


        ImageView menubutton = (ImageView) findViewById(R.id.menubtn);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(SubSelect.this, v);
        popup.getMenuInflater().inflate(R.menu.ins_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.item1){
                    Toast.makeText(SubSelect.this, "Add Users", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SubSelect.this, AddUsers.class));
                }

              /*  if (item.getItemId() == R.id.item2){
                    Toast.makeText(SubSelect.this, "Account Settings", Toast.LENGTH_SHORT).show();
                }*/


                return true;
            }
        });
        popup.show();
    }


}