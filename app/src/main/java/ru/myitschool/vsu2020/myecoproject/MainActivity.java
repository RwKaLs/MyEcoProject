package ru.myitschool.vsu2020.myecoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton map_btn = (ImageButton) findViewById(R.id.map_btn);
        Intent igmap = new Intent(MainActivity.this, GMapActivity.class);
        View.OnClickListener onclck = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.map_btn:
                        startActivity(igmap);
                }
            }
        };
    }
}