package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton map_btn = (ImageButton) findViewById(R.id.map_btn);
        Intent igmap = new Intent(MainActivity.this, GMapActivity.class);
        @SuppressLint("NonConstantResourceId") View.OnClickListener onclck = v -> {
            switch(v.getId()){
                case R.id.map_btn:
                    startActivity(igmap);
                case R.id  .game_btn:
            }
        };
        map_btn.setOnClickListener(onclck);
    }
}