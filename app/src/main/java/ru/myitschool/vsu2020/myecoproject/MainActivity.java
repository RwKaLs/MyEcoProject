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
        ImageButton map_btn = findViewById(R.id.map_btn);
        ImageButton game_btn = findViewById(R.id.game_btn);
        ImageButton profile_btn = findViewById(R.id.profile_btn);
        Intent igmap = new Intent(MainActivity.this, GMapActivity.class);
        Intent igame = new Intent(MainActivity.this, GameActivity.class);
        Intent iprofile = new Intent(MainActivity.this, ProfileActivity.class);
        @SuppressLint("NonConstantResourceId") View.OnClickListener onclck = v -> {
            switch(v.getId()){
                case R.id.map_btn:
                    startActivity(igmap);
                    break;
                case R.id.game_btn:
                    startActivity(igame);
                    break;
                case R.id.profile_btn:
                    startActivity(iprofile);
                    break;
            }
        };
        map_btn.setOnClickListener(onclck);
        game_btn.setOnClickListener(onclck);
        profile_btn.setOnClickListener(onclck);
    }
}
