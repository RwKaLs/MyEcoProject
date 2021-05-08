package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import ru.myitschool.vsu2020.myecoproject.drawing.ClickerSurface;
import ru.myitschool.vsu2020.myecoproject.logic.World;

public class GameActivity extends AppCompatActivity {
    ClickerSurface clickerSurface;
    SharedPreferences sharedPreferences;
    public World w = new World();
    public WorldProvider wp = () -> w;
    public SavingClicker sc;
    public Button upd, country;
    final String SAVED_COINS = "COINS", SAVED_K = "K", SAVED_LEVEL = "LEVEL", SAVED_CEFF = "CEFF", SAVED_YSPEED = "YSPEED";
    final String SAVED_RUSSIA = "RUSSIA", SAVED_CHINA = "CHINA", SAVED_FRANCE = "FRANCE", SAVED_ITALY = "ITALY", SAVED_ENGLAND = "ENGLAND",
                 SAVED_USA = "USA", SAVED_CANADA = "CANADA", SAVED_BRAZIL = "BRAZIL", SAVED_MEXICO = "MEXICO", SAVED_COLOMBIA = "COLOMBIA";
    final String RUSSIA = "Россия", CHINA = "Китай", FRANCE = "Франция", ITALY = "Италия", ENGLAND = "Англия",
                 USA = "США", CANADA = "Канада", BRAZIL = "Бразилия", MEXICO = "Мексика", COLOMBIA = "Колумбия";
    private static final int COUNTRY_ACTIVITY = 1, COUNTRY_RU = 2, COUNTRY_CHI = 3, COUNTRY_FRA = 4, COUNTRY_ITA = 5, COUNTRY_ENG = 6,
            COUNTRY_US = 7, COUNTRY_CA = 8, COUNTRY_BRA = 9, COUNTRY_MEX = 10, COUNTRY_COL = 11;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        upd = findViewById(R.id.btnClickerUpd);
        country = findViewById(R.id.btnGameCountry);
        sc = this::saveData;
        loadData();
        if (getIntent() != null){
            if (getIntent().getIntExtra("PROFILECOUNTRIES", 0) == 589){
                Intent iprof = new Intent();
                int[] countries = new int[]{ wp.getWorld().getCountryCount(RUSSIA),
                        wp.getWorld().getCountryCount(CHINA), wp.getWorld().getCountryCount(FRANCE), wp.getWorld().getCountryCount(ITALY),
                        wp.getWorld().getCountryCount(ENGLAND), wp.getWorld().getCountryCount(USA), wp.getWorld().getCountryCount(CANADA),
                        wp.getWorld().getCountryCount(BRAZIL), wp.getWorld().getCountryCount(MEXICO), wp.getWorld().getCountryCount(COLOMBIA)};
                iprof.putExtra("COUNTRIESARRAY", countries);
                setResult(RESULT_OK, iprof);
                finish();
            }
        }
        clickerSurface = new ClickerSurface(this, wp, sc);
        TextView tv = findViewById(R.id.textArrayshow);
        //loadData();
        ((FrameLayout)findViewById(R.id.game_area)).addView(clickerSurface);
        View.OnClickListener onclck = v -> {
            if (clickerSurface.equals(v)) {
                clickerSurface.onScreen();
            } else if (upd.equals(v)) {
                if(wp.getWorld().getMoney() >= wp.getWorld().getK()) {
                    wp.getWorld().spendMoney(wp.getWorld().getK());
                    wp.getWorld().addLevel();
                    wp.getWorld().addK();
                    wp.getWorld().addCeffMoney();
                } else{
                    lowMoney();
                }
            } else if(country.equals(v)){
                Intent i = new Intent(GameActivity.this, ChoiceCountry.class);
                i.putExtra("Money", wp.getWorld().getMoney());
                i.putExtra("K", wp.getWorld().getK());
                startActivityForResult(i, COUNTRY_ACTIVITY);
            } else if(tv.equals(v)){
                tv.setText(Arrays.toString(wp.getWorld().getTokens()));
            }
        };
        tv.setOnClickListener(onclck);
        clickerSurface.setOnClickListener(onclck);
        upd.setOnClickListener(onclck);
        country.setOnClickListener(onclck);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COUNTRY_ACTIVITY:
                /*проверить на страну*/
                 //тут был if в maybemaybe
                try {
                    assert data != null;
                    int cost = data.getIntExtra("COST", 0);
                    wp.getWorld().setMoney(wp.getWorld().getMoney() - cost);
                    String retcountry = data.getStringExtra("COUNTRY");
                    switch (retcountry){
                        case "Россия":
                            Intent iru = new Intent(GameActivity.this, CatcherActivity.class);
                            iru.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(iru, COUNTRY_RU);
                            Toast.makeText(this, "ru", Toast.LENGTH_LONG).show();
                            break;
                        case "Китай":
                            Intent ichi = new Intent(GameActivity.this, CatcherActivity.class);
                            ichi.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(ichi, COUNTRY_CHI);
                            Toast.makeText(this, "chi", Toast.LENGTH_LONG).show();
                            break;
                        case "Франция":
                            Intent ifra = new Intent(GameActivity.this, CatcherActivity.class);
                            ifra.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(ifra, COUNTRY_FRA);
                            Toast.makeText(this, "fra", Toast.LENGTH_LONG).show();
                            break;
                        case "Италия":
                            Intent iita = new Intent(GameActivity.this, CatcherActivity.class);
                            iita.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(iita, COUNTRY_ITA);
                            Toast.makeText(this, "ita", Toast.LENGTH_LONG).show();
                            break;
                        case "Англия":
                            Intent ieng = new Intent(GameActivity.this, CatcherActivity.class);
                            ieng.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(ieng, COUNTRY_ENG);
                            Toast.makeText(this, "eng", Toast.LENGTH_LONG).show();
                            break;
                        case "США":
                            Intent ius = new Intent(GameActivity.this, CatcherActivity.class);
                            ius.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(ius, COUNTRY_US);
                            Toast.makeText(this, "us", Toast.LENGTH_LONG).show();
                            break;
                        case "Канада":
                            Intent ica = new Intent(GameActivity.this, CatcherActivity.class);
                            ica.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(ica, COUNTRY_CA);
                            Toast.makeText(this, "ca", Toast.LENGTH_LONG).show();
                            break;
                        case "Бразилия":
                            Intent ibra = new Intent(GameActivity.this, CatcherActivity.class);
                            ibra.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(ibra, COUNTRY_BRA);
                            Toast.makeText(this, "bra", Toast.LENGTH_LONG).show();
                            break;
                        case "Мексика":
                            Intent imex = new Intent(GameActivity.this, CatcherActivity.class);
                            imex.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(imex, COUNTRY_MEX);
                            Toast.makeText(this, "mex", Toast.LENGTH_LONG).show();
                            break;
                        case "Колумбия":
                            Intent icol = new Intent(GameActivity.this, CatcherActivity.class);
                            icol.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(icol, COUNTRY_COL);
                            Toast.makeText(this, "col", Toast.LENGTH_LONG).show();
                            break;
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case COUNTRY_RU:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(RUSSIA);
                }
                saveData();
                break;
            case COUNTRY_CHI:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(CHINA);
                }
                saveData();
                break;
            case COUNTRY_FRA:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(FRANCE);
                }
                saveData();
                break;
            case COUNTRY_ITA:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(ITALY);
                }
                saveData();
                break;
            case COUNTRY_ENG:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(ENGLAND);
                }
                saveData();
                break;
            case COUNTRY_US:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(USA);
                }
                saveData();
                break;
            case COUNTRY_CA:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(CANADA);
                }
                saveData();
                break;
            case COUNTRY_BRA:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(BRAZIL);
                }
                saveData();
                break;
            case COUNTRY_MEX:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(MEXICO);
                }
                saveData();
                break;
            case COUNTRY_COL:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(COLOMBIA);
                }
                saveData();
                break;
            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent iback = new Intent(GameActivity.this, MainActivity.class);
        startActivity(iback);
    }

    public void loadData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        w.setMoney(sharedPreferences.getInt(SAVED_COINS, 0));
        w.setK(sharedPreferences.getInt(SAVED_K, 1000));
        w.setLevel(sharedPreferences.getInt(SAVED_LEVEL, 1));
        w.setCeff_money(sharedPreferences.getInt(SAVED_CEFF, 1));
        w.setySpeed(sharedPreferences.getInt(SAVED_YSPEED, 10));
        w.setToken(RUSSIA, sharedPreferences.getInt(SAVED_RUSSIA, 0));
        w.setToken(CHINA, sharedPreferences.getInt(SAVED_CHINA, 0));
        w.setToken(FRANCE, sharedPreferences.getInt(SAVED_FRANCE, 0));
        w.setToken(ITALY, sharedPreferences.getInt(SAVED_ITALY, 0));
        w.setToken(ENGLAND, sharedPreferences.getInt(SAVED_ENGLAND, 0));
        w.setToken(USA, sharedPreferences.getInt(USA, 0));
        w.setToken(CANADA, sharedPreferences.getInt(SAVED_CANADA, 0));
        w.setToken(BRAZIL, sharedPreferences.getInt(SAVED_BRAZIL, 0));
        w.setToken(MEXICO, sharedPreferences.getInt(SAVED_MEXICO, 0));
        w.setToken(COLOMBIA, sharedPreferences.getInt(SAVED_COLOMBIA, 0));
    }

    public void saveData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVED_COINS, wp.getWorld().getMoney());
        editor.putInt(SAVED_K, wp.getWorld().getK());
        editor.putInt(SAVED_LEVEL, wp.getWorld().getLevel());
        editor.putInt(SAVED_CEFF, wp.getWorld().getCeff_money());
        editor.putInt(SAVED_YSPEED, wp.getWorld().getySpeed());
        editor.putInt(SAVED_RUSSIA, wp.getWorld().getCountryCount(RUSSIA));
        editor.putInt(SAVED_CHINA, wp.getWorld().getCountryCount(CHINA));
        editor.putInt(SAVED_FRANCE, wp.getWorld().getCountryCount(FRANCE));
        editor.putInt(SAVED_ITALY, wp.getWorld().getCountryCount(ITALY));
        editor.putInt(SAVED_ENGLAND, wp.getWorld().getCountryCount(ENGLAND));
        editor.putInt(SAVED_USA, wp.getWorld().getCountryCount(USA));
        editor.putInt(SAVED_CANADA, wp.getWorld().getCountryCount(CANADA));
        editor.putInt(SAVED_BRAZIL, wp.getWorld().getCountryCount(BRAZIL));
        editor.putInt(SAVED_MEXICO, wp.getWorld().getCountryCount(MEXICO));
        editor.putInt(SAVED_COLOMBIA, wp.getWorld().getCountryCount(USA));
        editor.apply();
        editor.commit();
    }
    public void lowMoney(){
        Toast.makeText(this, "Недостаточно EcoCoins!", Toast.LENGTH_SHORT).show();
    }
}