package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceCountry extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_country);
        int money = getIntent().getIntExtra("Money", 0);
        int k = getIntent().getIntExtra("K", 100000);
        TextView tv = findViewById(R.id.show_coins);
        tv.setText("EcoCoins: " + money);
        Button choice1 = findViewById(R.id.choice1);
        Button choice2 = findViewById(R.id.choice2);
        Button choice3 = findViewById(R.id.choice3);
        Button choice4 = findViewById(R.id.choice4);
        Button choice5 = findViewById(R.id.choice5);
        Time currentTime = new Time();
        currentTime.setToNow();
        if (currentTime.hour > 7 && currentTime.hour < 16){
            choice1.setText(getResources().getString(R.string.russia) + ": " + ((k/1000)*1000));
            choice2.setText(getResources().getString(R.string.china) + ": " + (int)(((k*1.25)/1000)*1000));
            choice3.setText(getResources().getString(R.string.france) + ": " + (int)(((k*1.5)/1000)*1000));
            choice4.setText(getResources().getString(R.string.italy) + ": " + (int)(((k*1.75)/1000)*1000));
            choice5.setText(getResources().getString(R.string.england) + ": " + (((k*2)/1000)*1000));
        } else{
            choice1.setText(getResources().getString(R.string.usa) + ": " + ((k/1000)*1000));
            choice2.setText(getResources().getString(R.string.canada) + ": " + (int)(((k*1.25)/1000)*1000));
            choice3.setText(getResources().getString(R.string.brazil) + ": " + (int)(((k*1.5)/1000)*1000));
            choice4.setText(getResources().getString(R.string.mexico) + ": " + (int)(((k*1.75)/1000)*1000));
            choice5.setText(getResources().getString(R.string.colombia) + ": " + (((k*2)/1000)*1000));
        }
        @SuppressLint("NonConstantResourceId") View.OnClickListener onclck = v -> {
            switch (v.getId()){
                case R.id.choice1:
                    if (money >= ((k/1000)*1000)) {
                        Intent i = new Intent();
                        i.putExtra("COST", ((k/1000)*1000));
                        String s = String.valueOf(choice1.getText().subSequence(0, String.valueOf(choice1.getText()).indexOf(":")));
                        i.putExtra("COUNTRY", s);
                        setResult(RESULT_OK, i);
                        finish();
                    } else {
                        lowMoney();
                    }
                    break;
                case R.id.choice2:
                    if (money >= (((k*1.25)/1000)*1000)) {
                        Intent i = new Intent();
                        i.putExtra("COST", (int)(((k*1.25)/1000)*1000));
                        String s = String.valueOf(choice2.getText().subSequence(0, String.valueOf(choice2.getText()).indexOf(":")));
                        i.putExtra("COUNTRY", s);
                        setResult(RESULT_OK, i);
                        finish();
                    } else {
                        lowMoney();
                    }
                    break;
                case R.id.choice3:
                    if (money >= (((k*1.5)/1000)*1000)) {
                        Intent i = new Intent();
                        i.putExtra("COST", (int)(((k*1.5)/1000)*1000));
                        String s = String.valueOf(choice3.getText().subSequence(0, String.valueOf(choice3.getText()).indexOf(":")));
                        i.putExtra("COUNTRY", s);
                        setResult(RESULT_OK, i);
                        finish();
                    } else {
                        lowMoney();
                    }
                    break;
                case R.id.choice4:
                    if (money >= (((k*1.75)/1000)*1000)) {
                        Intent i = new Intent();
                        i.putExtra("COST", (int)(((k*1.75)/1000)*1000));
                        String s = String.valueOf(choice4.getText().subSequence(0, String.valueOf(choice4.getText()).indexOf(":")));
                        i.putExtra("COUNTRY", s);
                        setResult(RESULT_OK, i);
                        finish();
                    } else {
                        lowMoney();
                    }
                    break;
                case R.id.choice5:
                    if (money >= (((k*2)/1000)*1000)) {
                        Intent i = new Intent();
                        i.putExtra("COST", ((k*2)/1000)*1000);
                        String s = String.valueOf(choice5.getText().subSequence(0, String.valueOf(choice5.getText()).indexOf(":")));
                        i.putExtra("COUNTRY", s);
                        setResult(RESULT_OK, i);
                        finish();
                    } else {
                        lowMoney();
                    }
                    break;
            }
        };
        choice1.setOnClickListener(onclck);
        choice2.setOnClickListener(onclck);
        choice3.setOnClickListener(onclck);
        choice4.setOnClickListener(onclck);
        choice5.setOnClickListener(onclck);
    }

    public void lowMoney(){
        Toast.makeText(this, "Недостаточно EcoCoins!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ChoiceCountry.this, GameActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}