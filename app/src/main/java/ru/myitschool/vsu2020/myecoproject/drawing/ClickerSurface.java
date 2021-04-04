package ru.myitschool.vsu2020.myecoproject.drawing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import ru.myitschool.vsu2020.myecoproject.R;
import ru.myitschool.vsu2020.myecoproject.WorldProvider;

@SuppressLint("ViewConstructor")
public class ClickerSurface extends SurfaceView implements SurfaceHolder.Callback {

    private final WorldProvider wp;
    private ClickerThread clickerThread;
    public  Bitmap currentBitmap, cleaner1, cleaner2, cleaner1z, cleaner2z, trash;
    public int height, width, yHigh, yLow, xHigh, xLow;
    public int currentX, currentY;
    public int step, yStep;
    /*public World w;*/
    public String scores;
    public ClickerSurface(Context context, WorldProvider wp) {
        super(context);
        this.wp = wp;
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
        trash = BitmapFactory.decodeResource(getResources(), R.drawable.trash);
        currentBitmap = cleaner1;
        currentX = 50;
        currentY = 70;
        this.height = getHeight() - 350;
        this.width = getWidth() - 350;
        xHigh = width-20;
        xLow = 20;
        yHigh = height;
        yLow = 75;
        step = 20;
        yStep = 100;
        clickerThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.width = getWidth();
        this.height = getHeight();
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
        wp.getWorld().addmoney();
        if (currentX+21 > xHigh){
            currentBitmap = cleaner1z;
            step *= -1;
            currentY += yStep;
        } else if(currentX-21 < xLow){
            currentBitmap = cleaner1;
            step *= -1;
            currentY += yStep;
        }
        if (currentY+46 > yHigh){
            yStep *= -1;
        } else if(currentY-46 < yLow){
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
    }
    public class ClickerThread extends Thread{
        private final SurfaceHolder surfaceHolder;

        private volatile boolean running = true;
        public Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        public ClickerThread(SurfaceHolder surfaceHolder){
            this.surfaceHolder = surfaceHolder;
            p.setColor(Color.GREEN);
            p.setTextSize(50);
            p.setStyle(Paint.Style.FILL);
        }
        public void requestStop(){
            running = false;
        }

        @Override
        public void run() {
            while (running){
                /*if (w.getMoney() > k){
                    Intent i = new Intent(getContext(), CatcherSurface.class);
                    gameActivity.startActivity(i);
                }*/
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null){
                    try {
                        scores = "EcoCoins: "+ wp.getWorld().getMoney();
                        canvas.drawColor(Color.LTGRAY);
                        canvas.drawText(scores, 50, 50, p);
                        canvas.drawBitmap(currentBitmap, (float) currentX, (float) currentY, p);
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
