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

@SuppressLint("ViewConstructor")
public class CatcherSurface extends SurfaceView implements SurfaceHolder.Callback {

    public int height, width;
    public Bitmap trashcan, trash;
    public int xtrashcan, ytrashcan;
    public CatcherThread catcherThread;
    public int trashCount;
    public double xSpeed, ySpeed, departure, destination;
    public double currentX, currentY;
    public int points;

    public CatcherSurface(Context context, double ySpeed) {
        super(context);
        getHolder().addCallback(this);
        this.ySpeed = ySpeed;
        points = 0;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        catcherThread = new CatcherThread(getHolder());
        catcherThread.start();
        trashcan = BitmapFactory.decodeResource(getResources(), R.drawable.trashcan);
        trash = BitmapFactory.decodeResource(getResources(), R.drawable.trash_small);
        trashCount = 10; //должно меняться
        currentY = 50.0;
        xtrashcan = width/2;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
        departure = (Math.random() * (width-100) + 50);
        destination = (Math.random() * (width-100) + 50);
        currentX = departure;
        xSpeed = ySpeed*(destination-departure)/(height-30);
        ytrashcan = height-trashcan.getHeight()-25;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        catcherThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                catcherThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setXtrashcan(int x){
        this.xtrashcan = x;
    }
    public class CatcherThread extends Thread{
        private final SurfaceHolder surfaceHolder;

        private volatile boolean running = true;
        public Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        public CatcherThread(SurfaceHolder surfaceHolder){
            this.surfaceHolder = surfaceHolder;
            p.setColor(Color.RED);
            p.setTextSize(50);
        }

        public void requestStop(){running = false; }

        @Override
        public void run() {
            while (running){
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null){
                    try {
                        canvas.drawColor(getContext().getResources().getColor(R.color.catcher_background));
                        canvas.drawBitmap(trashcan, (float)xtrashcan, (float)ytrashcan, p);
                        if ((double)currentY >= (double) (height-trashcan.getHeight()-24 + ySpeed)) {
                            if (currentX <= (double)(xtrashcan + trashcan.getWidth()/2) && currentX >= (double)(xtrashcan - trashcan.getWidth()/2)) {
                                points++;
                                currentY = 0;
                                departure = (Math.random() * (width-100)+50);
                                destination = (Math.random() * (width-100)+50);
                                currentX = departure;
                                xSpeed = ySpeed*(destination-departure)/(height-30);
                            } else {
                                canvas.drawText("looser", 100, 100, p);
                            }
                        }else{
                            canvas.drawBitmap(trash, (float) currentX, (float) currentY, p);
                        }
                        canvas.drawText(String.valueOf(points), 50, 50, p);
                        currentX += xSpeed;
                        currentY += ySpeed;
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
