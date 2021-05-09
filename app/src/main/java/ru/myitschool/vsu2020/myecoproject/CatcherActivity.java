package ru.myitschool.vsu2020.myecoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import ru.myitschool.vsu2020.myecoproject.drawing.CatcherSurface;

public class CatcherActivity extends AppCompatActivity implements View.OnTouchListener {

    CatcherSurface catcherSurface;
    SavingCatcher sc;
    private int costRes;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catcher);
        sc = this::setRes;
        costRes = getIntent().getIntExtra("COSTRES", 0);
        catcherSurface = new CatcherSurface(this, getIntent().getDoubleExtra("YSPEED", 10.0), sc);
        catcherSurface.setOnTouchListener(this);
        ((FrameLayout)findViewById(R.id.catcher_area)).addView(catcherSurface);
        //catcherSurface.setOnTouchListener(this);
    }

    public void setRes(int result){
        Intent i = new Intent();
        i.putExtra("RESULT", result);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent iback = new Intent(CatcherActivity.this, GameActivity.class);
        iback.putExtra("BACKBTN", 1492);
        iback.putExtra("COSTRESULT", costRes);
        startActivity(iback);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        int x = (int) event.getX();
        if(x < catcherSurface.trashcan.getWidth()/2){
            catcherSurface.setXtrashcan(0);
        } else if (x > catcherSurface.width - catcherSurface.trashcan.getWidth()/2){
            catcherSurface.setXtrashcan(catcherSurface.width - catcherSurface.trashcan.getWidth());
        } else {
            catcherSurface.setXtrashcan(x-catcherSurface.trashcan.getWidth()/2);
        }
        //catcherSurface.setXtrashcan(Math.min(x-catcherSurface.trashcan.getWidth()/2, (catcherSurface.width - catcherSurface.trashcan.getWidth())));
        return true;
    }
}