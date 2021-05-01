package ru.myitschool.vsu2020.myecoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import ru.myitschool.vsu2020.myecoproject.drawing.CatcherSurface;

public class CatcherActivity extends AppCompatActivity implements View.OnTouchListener {

    CatcherSurface catcherSurface;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catcher);
        catcherSurface = new CatcherSurface(this, getIntent().getDoubleExtra("YSPEED", 10.0));
        ((FrameLayout)findViewById(R.id.catcher_area)).addView(catcherSurface);
        catcherSurface.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        int x = (int) event.getX();
        catcherSurface.setXtrashcan(Math.min(x-catcherSurface.trashcan.getWidth()/2, (catcherSurface.width - catcherSurface.trashcan.getWidth())));
        return true;
    }
}