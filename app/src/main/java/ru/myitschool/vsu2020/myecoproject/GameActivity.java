package ru.myitschool.vsu2020.myecoproject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.myitschool.vsu2020.myecoproject.drawing.ClickerSurface;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener{
    public int currentX, currentY;
    public int height, width;
    public int yHigh, yLow, xHigh, xLow; // верхняя граница подъёма bitmap
    ClickerSurface clickerSurface;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*cleaner1 = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner1);
        cleaner2 = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner2);
        cleaner1z = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner1z);
        cleaner2z = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner2z);*/
        /*GETTING SCREEN SIZE*/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        yHigh = height/4; yLow = height - 30; xHigh = width - 30; xLow = 30; //screen params
        currentX = xLow; currentY = yLow;
        clickerSurface = new ClickerSurface(this);
        setContentView(clickerSurface);
        clickerSurface.setOnTouchListener(this);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event){
        clickerSurface.onScreen();
        Log.d("SCREEN", "SCREENTOUCH");
        Toast.makeText(this, ""+clickerSurface.currentX, Toast.LENGTH_SHORT).show();
        return true;
    }
    /*private void updateBitmap(Canvas canvas, Paint p){
        if (currentX >= xHigh){
            currentBitmap = cleaner1z;
            step *= -1;
        } else if(currentX <= xLow){
            currentBitmap = cleaner1;
            step *= -1;
        }
        if (currentY >= yHigh){
            yStep *= -1;
        } else if(currentY <= yLow){
            yStep *= -1;
        }
        canvas.drawBitmap(currentBitmap, currentX, currentY, p);
        if (currentBitmap == cleaner1){
            currentBitmap = cleaner2;
        } else if (currentBitmap == cleaner2){
            currentBitmap = cleaner1;
        } else if (currentBitmap == cleaner1z){
            currentBitmap = cleaner2z;
        } else if (currentBitmap == cleaner2z){
            currentBitmap = cleaner1z;
        }
    }*/
}