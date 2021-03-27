package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.myitschool.vsu2020.myecoproject.drawing.ClickerSurface;

public class GameActivity extends AppCompatActivity{
    ClickerSurface clickerSurface;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*cleaner1 = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner1);
        cleaner2 = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner2);
        cleaner1z = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner1z);
        cleaner2z = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner2z);
        /*GETTING SCREEN SIZE
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;*/
        clickerSurface = new ClickerSurface(this);
        setContentView(clickerSurface);
        View.OnClickListener onclck = v -> clickerSurface.onScreen();
        clickerSurface.setOnClickListener(onclck);
    }
}