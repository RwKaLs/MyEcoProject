package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    private static final int COUNTRY_ACTIVITY = 1, COUNTRY_RU = 2;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        upd = findViewById(R.id.btnClickerUpd);
        country = findViewById(R.id.btnGameCountry);
        sc = this::saveData;
        clickerSurface = new ClickerSurface(this, wp, sc);
        loadData();
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
            case COUNTRY_ACTIVITY: //переход в разные активити
                /*проверить на страну*/
                 //тут был if в maybemaybe
                try {
                    int cost = data.getIntExtra("COST", 0);
                    wp.getWorld().setMoney(wp.getWorld().getMoney() - cost);
                    String retcountry = data.getStringExtra("COUNTRY");
                    //Toast.makeText(this, retcountry, Toast.LENGTH_LONG).show();
                    switch (retcountry){
                        case "Россия":
                            Intent ic = new Intent(GameActivity.this, CatcherActivity.class);
                            ic.putExtra("YSPEED", (double)wp.getWorld().getySpeed());
                            startActivityForResult(ic, COUNTRY_RU);
                            Toast.makeText(this, "ru", Toast.LENGTH_LONG).show();
                            break;
                        case "Китай":
                            Toast.makeText(this, "chi", Toast.LENGTH_LONG).show();
                            break;
                        case "Франция":
                            Toast.makeText(this, "fra", Toast.LENGTH_LONG).show();
                            break;
                        case "Италия":
                            Toast.makeText(this, "ita", Toast.LENGTH_LONG).show();
                            break;
                        case "Англия":
                            Toast.makeText(this, "eng", Toast.LENGTH_LONG).show();
                            break;
                        case "США":
                            Toast.makeText(this, "us", Toast.LENGTH_LONG).show();
                            break;
                        case "Канада":
                            Toast.makeText(this, "ca", Toast.LENGTH_LONG).show();
                            break;
                        case "Бразилия":
                            Toast.makeText(this, "bra", Toast.LENGTH_LONG).show();
                            break;
                        case "Мексика":
                            Toast.makeText(this, "mex", Toast.LENGTH_LONG).show();
                            break;
                        case "Колумбия":
                            Toast.makeText(this, "col", Toast.LENGTH_LONG).show();
                            break;
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
            case COUNTRY_RU:
                //считывание результата выполнения catcherSurface
            }
    }

    public void loadData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        w.setMoney(sharedPreferences.getInt(SAVED_COINS, 0));
        w.setK(sharedPreferences.getInt(SAVED_K, 1000));
        w.setLevel(sharedPreferences.getInt(SAVED_LEVEL, 1));
        w.setCeff_money(sharedPreferences.getInt(SAVED_CEFF, 1));
        w.setySpeed(sharedPreferences.getInt(SAVED_YSPEED, 10));
    }

    public void saveData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVED_COINS, wp.getWorld().getMoney());
        editor.putInt(SAVED_K, wp.getWorld().getK());
        editor.putInt(SAVED_LEVEL, wp.getWorld().getLevel());
        editor.putInt(SAVED_CEFF, wp.getWorld().getCeff_money());
        editor.putInt(SAVED_YSPEED, wp.getWorld().getySpeed());
        editor.apply();
        editor.commit();
    }
    public void lowMoney(){
        Toast.makeText(this, "Недостаточно EcoCoins!", Toast.LENGTH_SHORT).show();
    }
}