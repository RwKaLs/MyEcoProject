package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    private static final int GET_COUNTRIES = 1;
    public int[] countries;
    public EditText edNickName;
    public Button saveUserNick;
    SharedPreferences sharedPrefProf;
    final String SAVED_NICKNAME = "SVNICKNAME", SAVED_ATTEMPTS = "SVATTEMPTS";
    private String userNick;
    private int attempts;

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadProf();
        Intent iget = new Intent(ProfileActivity.this, GameActivity.class);
        iget.putExtra("PROFILECOUNTRIES", 589);
        startActivityForResult(iget, GET_COUNTRIES);
        setContentView(R.layout.activity_profile);
        saveUserNick = findViewById(R.id.setNick);
        if (attempts < 1){
            saveUserNick.setVisibility(View.INVISIBLE);
            saveUserNick.setFocusable(false);
            edNickName.setFocusable(false);
        }
        View.OnClickListener onclck = v -> {
            if (attempts > 0) {
                userNick = String.valueOf(edNickName.getText());
                attempts -= 1;
                saveProf();
                msgAttempts();
            }
        };
        saveUserNick.setOnClickListener(onclck);
    }

    private void msgAttempts(){
        Toast.makeText(this, "Никнейм изменён. Возможны изменения ещё " + attempts + " раз", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (resultCode == RESULT_OK) {
            countries = data.getIntArrayExtra("COUNTRIESARRAY");
        }
        int level = data.getIntExtra("LEVEL_TO_PROF", 0);
        TextView tv = findViewById(R.id.testtext);
        TextView tvLevel = findViewById(R.id.tv_level_prof);
        edNickName = findViewById(R.id.edNickName);
        edNickName.setText(userNick);
        tvLevel.setText("Ваш уровень: " + level);
        tv.setText(Arrays.toString(countries));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveProf();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveProf();
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