package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import ru.myitschool.vsu2020.myecoproject.database.CountriesDbManager;
import ru.myitschool.vsu2020.myecoproject.drawing.ClickerSurface;
import ru.myitschool.vsu2020.myecoproject.logic.World;

public class GameActivity extends AppCompatActivity {

    ClickerSurface clickerSurface;
    SharedPreferences sharedPreferences;
    public World w = new World();
    public WorldProvider wp = () -> w;
    public SavingClicker sc;
    public TextView tv_nextK;
    public ImageButton upd, country;

    private CountriesDbManager dbManager;

    final String SAVED_COINS = "COINS", SAVED_K = "K", SAVED_LEVEL = "LEVEL", SAVED_CEFF = "CEFF", SAVED_YSPEED = "YSPEED";

    final String SAVED_RUSSIA = "RUSSIA", SAVED_CHINA = "CHINA", SAVED_FRANCE = "FRANCE", SAVED_ITALY = "ITALY",
            SAVED_ENGLAND = "ENGLAND", SAVED_USA = "USA", SAVED_CANADA = "CANADA", SAVED_BRAZIL = "BRAZIL",
            SAVED_MEXICO = "MEXICO", SAVED_COLOMBIA = "COLOMBIA";

    final String RUSSIA = "Россия", CHINA = "Китай", FRANCE = "Франция", ITALY = "Италия", ENGLAND = "Англия",
                 USA = "США", CANADA = "Канада", BRAZIL = "Бразилия", MEXICO = "Мексика", COLOMBIA = "Колумбия";

    private static final int COUNTRY_ACTIVITY = 1, COUNTRY_RU = 2, COUNTRY_CHI = 3, COUNTRY_FRA = 4,
            COUNTRY_ITA = 5, COUNTRY_ENG = 6, COUNTRY_US = 7, COUNTRY_CA = 8, COUNTRY_BRA = 9,
            COUNTRY_MEX = 10, COUNTRY_COL = 11;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        dbManager = new CountriesDbManager(this);
        upd = findViewById(R.id.btnClickerUpd);
        country = findViewById(R.id.btnGameCountry);
        tv_nextK = findViewById(R.id.show_next_coins);
        sc = this::saveData;
        loadData();
        tv_nextK.setText("Update: " + wp.getWorld().getK() + " EcoCoins");
        if (getIntent() != null){
            if (getIntent().getIntExtra("PROFILECOUNTRIES", 0) == 589){
                Intent iprof = new Intent();
                int[] countries = new int[]{ wp.getWorld().getCountryCount(RUSSIA),
                        wp.getWorld().getCountryCount(CHINA), wp.getWorld().getCountryCount(FRANCE),
                        wp.getWorld().getCountryCount(ITALY), wp.getWorld().getCountryCount(ENGLAND),
                        wp.getWorld().getCountryCount(USA), wp.getWorld().getCountryCount(CANADA),
                        wp.getWorld().getCountryCount(BRAZIL), wp.getWorld().getCountryCount(MEXICO),
                        wp.getWorld().getCountryCount(COLOMBIA)};
                iprof.putExtra("LEVEL_TO_PROF", wp.getWorld().getLevel());
                iprof.putExtra("COINS", wp.getWorld().getMoney());
                iprof.putExtra("COUNTRIESARRAY", countries);
                setResult(RESULT_OK, iprof);
                finish();
            } else if (getIntent().getIntExtra("BACKBTN", 0) == 1492){
                int costRes = getIntent().getIntExtra("COSTRESULT", 0);
                w.setMoney(w.getMoney() + costRes);
            }
        }
        clickerSurface = new ClickerSurface(this, wp, sc);
        //loadData();
        ((FrameLayout)findViewById(R.id.game_area)).addView(clickerSurface);
        @SuppressLint("SetTextI18n") View.OnClickListener onclck = v -> {
            if (clickerSurface.equals(v)) {
                clickerSurface.onScreen();
            } else if (upd.equals(v)) {
                if(wp.getWorld().getMoney() >= wp.getWorld().getK()) {
                    wp.getWorld().spendMoney(wp.getWorld().getK());
                    wp.getWorld().addLevel();
                    wp.getWorld().addK();
                    wp.getWorld().addCeffMoney();
                    tv_nextK.setText("Update: " + wp.getWorld().getK() + " EcoCoins");
                } else{
                    lowMoney();
                }
            } else if(country.equals(v)){
                Intent i = new Intent(GameActivity.this, ChoiceCountry.class);
                i.putExtra("Money", wp.getWorld().getMoney());
                i.putExtra("K", wp.getWorld().getK());
                startActivityForResult(i, COUNTRY_ACTIVITY);
            }
        };
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
                            iru.putExtra("COSTRES", cost);
                            startActivityForResult(iru, COUNTRY_RU);
                            break;
                        case "Китай":
                            Intent ichi = new Intent(GameActivity.this, CatcherActivity.class);
                            ichi.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            ichi.putExtra("COSTRES", cost);
                            startActivityForResult(ichi, COUNTRY_CHI);
                            break;
                        case "Франция":
                            Intent ifra = new Intent(GameActivity.this, CatcherActivity.class);
                            ifra.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            ifra.putExtra("COSTRES", cost);
                            startActivityForResult(ifra, COUNTRY_FRA);
                            break;
                        case "Италия":
                            Intent iita = new Intent(GameActivity.this, CatcherActivity.class);
                            iita.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            iita.putExtra("COSTRES", cost);
                            startActivityForResult(iita, COUNTRY_ITA);
                            break;
                        case "Англия":
                            Intent ieng = new Intent(GameActivity.this, CatcherActivity.class);
                            ieng.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            ieng.putExtra("COSTRES", cost);
                            startActivityForResult(ieng, COUNTRY_ENG);
                            break;
                        case "США":
                            Intent ius = new Intent(GameActivity.this, CatcherActivity.class);
                            ius.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            ius.putExtra("COSTRES", cost);
                            startActivityForResult(ius, COUNTRY_US);
                            break;
                        case "Канада":
                            Intent ica = new Intent(GameActivity.this, CatcherActivity.class);
                            ica.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            ica.putExtra("COSTRES", cost);
                            startActivityForResult(ica, COUNTRY_CA);
                            break;
                        case "Бразилия":
                            Intent ibra = new Intent(GameActivity.this, CatcherActivity.class);
                            ibra.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            ibra.putExtra("COSTRES", cost);
                            startActivityForResult(ibra, COUNTRY_BRA);
                            break;
                        case "Мексика":
                            Intent imex = new Intent(GameActivity.this, CatcherActivity.class);
                            imex.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            imex.putExtra("COSTRES", cost);
                            startActivityForResult(imex, COUNTRY_MEX);
                            break;
                        case "Колумбия":
                            Intent icol = new Intent(GameActivity.this, CatcherActivity.class);
                            icol.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            icol.putExtra("COSTRES", cost);
                            startActivityForResult(icol, COUNTRY_COL);
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
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_RUSSIA, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_CHI:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(CHINA);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_CHINA, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_FRA:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(FRANCE);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_FRANCE, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_ITA:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(ITALY);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_ITALY, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_ENG:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(ENGLAND);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_ENGLAND, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_US:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(USA);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_USA, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_CA:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(CANADA);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_CANADA, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_BRA:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(BRAZIL);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_BRAZIL, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_MEX:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(MEXICO);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_MEXICO, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            case COUNTRY_COL:
                assert data != null;
                if (data.getIntExtra("RESULT", 0) == 1){
                    wp.getWorld().setySpeed(wp.getWorld().getySpeed()+1);
                    wp.getWorld().setToken(COLOMBIA);
                    dbManager.openDb();
                    dbManager.insertToDb(SAVED_COLOMBIA, dateFormat());
                    dbManager.closeDb();
                }
                saveData();
                break;
            }
    }

    public String dateFormat(){
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(date);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent iback = new Intent(GameActivity.this, MainActivity.class);
        startActivity(iback);
    }

    public void loadData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        w.setMoney(sharedPreferences.getInt(SAVED_COINS, 1000000000));
        w.setK(sharedPreferences.getInt(SAVED_K, 1000));
        w.setLevel(sharedPreferences.getInt(SAVED_LEVEL, 1));
        w.setCeff_money(sharedPreferences.getInt(SAVED_CEFF, 1));
        w.setySpeed(sharedPreferences.getInt(SAVED_YSPEED, 10));
        /*w.setToken(RUSSIA, sharedPreferences.getInt(SAVED_RUSSIA, 0));
        w.setToken(CHINA, sharedPreferences.getInt(SAVED_CHINA, 0));
        w.setToken(FRANCE, sharedPreferences.getInt(SAVED_FRANCE, 0));
        w.setToken(ITALY, sharedPreferences.getInt(SAVED_ITALY, 0));
        w.setToken(ENGLAND, sharedPreferences.getInt(SAVED_ENGLAND, 0));
        w.setToken(USA, sharedPreferences.getInt(SAVED_USA, 0));
        w.setToken(CANADA, sharedPreferences.getInt(SAVED_CANADA, 0));
        w.setToken(BRAZIL, sharedPreferences.getInt(SAVED_BRAZIL, 0));
        w.setToken(MEXICO, sharedPreferences.getInt(SAVED_MEXICO, 0));
        w.setToken(COLOMBIA, sharedPreferences.getInt(SAVED_COLOMBIA, 0));*/
        dbManager.openDb();
        w.setToken(RUSSIA, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_RUSSIA)).size()-1);
        w.setToken(CHINA, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_CHINA)).size()-1);
        w.setToken(FRANCE, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_FRANCE)).size()-1);
        w.setToken(ITALY, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_ITALY)).size()-1);
        w.setToken(ENGLAND, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_ENGLAND)).size()-1);
        w.setToken(USA, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_USA)).size()-1);
        w.setToken(CANADA, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_CANADA)).size()-1);
        w.setToken(BRAZIL, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_BRAZIL)).size()-1);
        w.setToken(MEXICO, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_MEXICO)).size()-1);
        w.setToken(COLOMBIA, Objects.requireNonNull(dbManager.getFromDb().get(SAVED_COLOMBIA)).size()-1);
        dbManager.closeDb();
    }

    public void saveData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVED_COINS, wp.getWorld().getMoney());
        editor.putInt(SAVED_K, wp.getWorld().getK());
        editor.putInt(SAVED_LEVEL, wp.getWorld().getLevel());
        editor.putInt(SAVED_CEFF, wp.getWorld().getCeff_money());
        editor.putInt(SAVED_YSPEED, wp.getWorld().getySpeed());
        /*editor.putInt(SAVED_RUSSIA, wp.getWorld().getCountryCount(RUSSIA));
        editor.putInt(SAVED_CHINA, wp.getWorld().getCountryCount(CHINA));
        editor.putInt(SAVED_FRANCE, wp.getWorld().getCountryCount(FRANCE));
        editor.putInt(SAVED_ITALY, wp.getWorld().getCountryCount(ITALY));
        editor.putInt(SAVED_ENGLAND, wp.getWorld().getCountryCount(ENGLAND));
        editor.putInt(SAVED_USA, wp.getWorld().getCountryCount(USA));
        editor.putInt(SAVED_CANADA, wp.getWorld().getCountryCount(CANADA));
        editor.putInt(SAVED_BRAZIL, wp.getWorld().getCountryCount(BRAZIL));
        editor.putInt(SAVED_MEXICO, wp.getWorld().getCountryCount(MEXICO));
        editor.putInt(SAVED_COLOMBIA, wp.getWorld().getCountryCount(COLOMBIA));*/
        editor.apply();
        editor.commit();
    }
    public void lowMoney(){
        Toast.makeText(this, "Недостаточно EcoCoins!", Toast.LENGTH_SHORT).show();
    }
}

/*Meganov Egor*/
