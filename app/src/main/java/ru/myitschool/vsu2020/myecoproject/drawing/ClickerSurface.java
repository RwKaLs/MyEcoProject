package ru.myitschool.vsu2020.myecoproject.drawing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import ru.myitschool.vsu2020.myecoproject.GameActivity;
import ru.myitschool.vsu2020.myecoproject.R;

public class ClickerSurface extends SurfaceView implements SurfaceHolder.Callback {

    private ClickerThread clickerThread;
    public  Bitmap currentBitmap, cleaner1, cleaner2, cleaner1z, cleaner2z;
    public int height, width, yHigh, yLow, xHigh, xLow;
    public int currentX, currentY;
    public int step = 20, yStep = 45;
    public ClickerSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @SuppressLint("ResourceType")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        clickerThread = new ClickerThread(getHolder());
        cleaner1 = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner1);
        cleaner2 = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner2);
        cleaner1z = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner1z);
        cleaner2z = BitmapFactory.decodeResource(getResources(), R.drawable.cleaner2z);
        GameActivity gameActivity = new GameActivity();
        currentBitmap = cleaner1;
        height = gameActivity.height;
        width = gameActivity.width;
        yHigh = gameActivity.yHigh;
        xHigh = gameActivity.xHigh;
        yLow = gameActivity.yLow;
        xLow = gameActivity.xLow;
        clickerThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        clickerThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                clickerThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }
    public void onScreen(){
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
        if (currentBitmap == cleaner1){
            currentBitmap = cleaner2;
        } else if (currentBitmap == cleaner2){
            currentBitmap = cleaner1;
        } else if (currentBitmap == cleaner1z){
            currentBitmap = cleaner2z;
        } else if (currentBitmap == cleaner2z){
            currentBitmap = cleaner1z;
        }
        currentX += step;
        currentY += yStep;
    }
    public class ClickerThread extends Thread{
        private SurfaceHolder surfaceHolder;

        private volatile boolean running = true;
        public Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        @SuppressLint("ResourceAsColor")
        public ClickerThread(SurfaceHolder surfaceHolder){
            this.surfaceHolder = surfaceHolder;
        }
        public void requestStop(){
            running = false;
        }



        @Override
        public void run() {
            while (running){
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null){
                    try {
                        canvas.drawBitmap(currentBitmap, (float) currentX, (float) currentY, p);
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
