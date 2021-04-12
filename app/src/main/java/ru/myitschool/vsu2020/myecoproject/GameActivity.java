package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.myitschool.vsu2020.myecoproject.drawing.ClickerSurface;
import ru.myitschool.vsu2020.myecoproject.logic.World;

public class GameActivity extends AppCompatActivity {
    ClickerSurface clickerSurface;
    SharedPreferences sharedPreferences;
    public World w = new World();
    public int height;
    public int width;
    public WorldProvider wp;
    public SavingClicker sc;
    public Button upd, country;
    final String SAVED_COINS = "COINS", SAVED_K = "K", SAVED_LEVEL = "LEVEL", SAVED_CEFF = "CEFF";

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        /*GETTING SCREEN SIZE*/
        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;*/
        upd = findViewById(R.id.btnClickerUpd);
        country = findViewById(R.id.btnGameCountry);
        wp = () -> w;
        sc = this::saveData;
        clickerSurface = new ClickerSurface(this, wp, sc, height, width);
        loadData();
        //setContentView(clickerSurface);
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
                Intent i = new Intent();
            }
        };
        clickerSurface.setOnClickListener(onclck);
        upd.setOnClickListener(onclck);
    }

    public void loadData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        w.setMoney(sharedPreferences.getInt(SAVED_COINS, 0));
        w.setK(sharedPreferences.getInt(SAVED_K, 1000));
        w.setLevel(sharedPreferences.getInt(SAVED_LEVEL, 1));
        w.setCeff_money(sharedPreferences.getInt(SAVED_CEFF, 1));
        //Toast.makeText(this, "LOADED", Toast.LENGTH_LONG).show();
    }

    public void saveData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVED_COINS, wp.getWorld().getMoney());
        editor.putInt(SAVED_K, wp.getWorld().getK());
        editor.putInt(SAVED_LEVEL, wp.getWorld().getLevel());
        editor.putInt(SAVED_CEFF, wp.getWorld().getCeff_money());
        editor.apply();
        editor.commit();
        //Toast.makeText(this,"SAVED", Toast.LENGTH_LONG).show();
    }
    public void lowMoney(){
        Toast.makeText(this, "Недостаточно EcoCoins!", Toast.LENGTH_SHORT).show();
    }
}