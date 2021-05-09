package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    private static final int GET_COUNTRIES = 1;
    public int[] countries;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent iget = new Intent(ProfileActivity.this, GameActivity.class);
        iget.putExtra("PROFILECOUNTRIES", 589);
        startActivityForResult(iget, GET_COUNTRIES);
        setContentView(R.layout.activity_profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (resultCode == RESULT_OK) {
            countries = data.getIntArrayExtra("COUNTRIESARRAY");
        }
        TextView tv = findViewById(R.id.testtext);
        tv.setText(Arrays.toString(countries));
    }
}