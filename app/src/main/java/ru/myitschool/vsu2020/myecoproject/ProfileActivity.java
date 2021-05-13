package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private static final int GET_COUNTRIES = 1;
    public int[] countries;
    public EditText edNickName;
    public TextView tvLevel, tvCoins, tvCountry1, tvCountry2, tvCountry3, tvCountry4, tvCountry5;
    public ImageView imgCountry1, imgCountry2, imgCountry3, imgCountry4, imgCountry5;
    public Button westCountries, eastCountries;
    SharedPreferences sharedPrefProf;
    final String SAVED_NICKNAME = "SVNICKNAME", SAVED_ATTEMPTS = "SVATTEMPTS";
    private String userNick;
    private int attempts, level, coins;

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadProf();
        Intent iget = new Intent(ProfileActivity.this, GameActivity.class);
        iget.putExtra("PROFILECOUNTRIES", 589);
        startActivityForResult(iget, GET_COUNTRIES);
        setContentView(R.layout.activity_profile);
        tvCountry1 = findViewById(R.id.tv_country1);
        tvCountry2 = findViewById(R.id.tv_country2);
        tvCountry3 = findViewById(R.id.tv_country3);
        tvCountry4 = findViewById(R.id.tv_country4);
        tvCountry5 = findViewById(R.id.tv_country5);
        imgCountry1 = findViewById(R.id.img_country1);
        imgCountry2 = findViewById(R.id.img_country2);
        imgCountry3 = findViewById(R.id.img_country3);
        imgCountry4 = findViewById(R.id.img_country4);
        imgCountry5 = findViewById(R.id.img_country5);
        westCountries = findViewById(R.id.btn_West);
        eastCountries = findViewById(R.id.btn_East);
        @SuppressLint("NonConstantResourceId") View.OnClickListener onclck = v -> {
            switch (v.getId()){
                case R.id.btn_East:
                    showEast();
                    break;
                case R.id.btn_West:
                    showWest();
            }
        };
        westCountries.setOnClickListener(onclck);
        eastCountries.setOnClickListener(onclck);
    }

    private void msgAttempts(int mode){
        if (mode == 1) {
            if (attempts == 1 || attempts == 5) {
                Toast.makeText(this, "Никнейм изменён. Возможны изменения ещё " + attempts + " раз", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Никнейм изменён. Возможны изменения ещё " + attempts + " раза", Toast.LENGTH_LONG).show();
            }
        } else if (mode == 2){
            Toast.makeText(this, "Невозможно изменить никнейм (закончились попытки)", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (resultCode == RESULT_OK) {
            countries = data.getIntArrayExtra("COUNTRIESARRAY");
        }
        level = data.getIntExtra("LEVEL_TO_PROF", 0);
        coins = data.getIntExtra("COINS", 0);
        tvLevel = findViewById(R.id.tv_level_prof);
        tvCoins = findViewById(R.id.tv_coins_prof);
        edNickName = findViewById(R.id.edNickName);
        edNickName.setText(userNick);
        if (attempts < 1) {
            edNickName.setFocusable(false);
        }
        tvLevel.setText("Ваш уровень: " + level);
        tvCoins.setText("EcoCoins: " + coins);
    }

    private String formatCountry(int count){
        return "Жетоны страны: " + count;
    }

    private void showEast(){
        tvCountry1.setText(formatCountry(countries[0]));
        tvCountry2.setText(formatCountry(countries[1]));
        tvCountry3.setText(formatCountry(countries[2]));
        tvCountry4.setText(formatCountry(countries[3]));
        tvCountry5.setText(formatCountry(countries[4]));
        imgCountry1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.russia));
        imgCountry2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.china));
        imgCountry3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.france));
        imgCountry4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.italy));
        imgCountry5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.england));
    }

    private void showWest(){
        tvCountry1.setText(formatCountry(countries[5]));
        tvCountry2.setText(formatCountry(countries[6]));
        tvCountry3.setText(formatCountry(countries[7]));
        tvCountry4.setText(formatCountry(countries[8]));
        tvCountry5.setText(formatCountry(countries[9]));
        imgCountry1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.usa));
        imgCountry2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.canada));
        imgCountry3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.brazil));
        imgCountry4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mexico));
        imgCountry5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.colombia));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent iback = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(iback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (String.valueOf(edNickName.getText()).equals(userNick)){
            saveProf();
        } else if (!String.valueOf(edNickName.getText()).equals("")){
            if (attempts < 1){
                msgAttempts(2);
            } else {
                attempts--;
                msgAttempts(1);
                saveProf();
            }
        }
    }

    public void loadProf(){
        sharedPrefProf = getPreferences(MODE_PRIVATE);
        userNick = sharedPrefProf.getString(SAVED_NICKNAME, "");
        attempts = sharedPrefProf.getInt(SAVED_ATTEMPTS, 5);
    }

    public void saveProf(){
        sharedPrefProf = getPreferences(MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPrefProf.edit();
        editor.putString(SAVED_NICKNAME, String.valueOf(edNickName.getText()));
        editor.putInt(SAVED_ATTEMPTS, attempts);
        editor.apply();
        editor.commit();
    }
}