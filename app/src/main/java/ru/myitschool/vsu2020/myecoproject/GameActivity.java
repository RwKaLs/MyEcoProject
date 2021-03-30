package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.myitschool.vsu2020.myecoproject.drawing.CatcherSurface;
import ru.myitschool.vsu2020.myecoproject.drawing.ClickerSurface;
import ru.myitschool.vsu2020.myecoproject.logic.World;

public class GameActivity extends AppCompatActivity {
    ClickerSurface clickerSurface;
    CatcherSurface catcherSurface;
    SharedPreferences sharedPreferences;
    World w;
    final String SAVED_NUM = "COINS";

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*GETTING SCREEN SIZE
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;*/
        w = new World();
        loadData();
        clickerSurface = new ClickerSurface(this);
        setContentView(clickerSurface);
        View.OnClickListener onclck = v -> clickerSurface.onScreen();
        clickerSurface.setOnClickListener(onclck);
    }
    public void loadData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        w.setMoney(sharedPreferences.getInt(SAVED_NUM, 0));
    }
    public void saveData(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SAVED_NUM, w.getMoney());
        editor.apply();
        editor.commit();
        Toast.makeText(this,"SAVED", Toast.LENGTH_LONG).show();
    }
}