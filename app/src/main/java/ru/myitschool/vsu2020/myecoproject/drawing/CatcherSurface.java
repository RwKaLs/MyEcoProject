package ru.myitschool.vsu2020.myecoproject.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import ru.myitschool.vsu2020.myecoproject.GameActivity;

public class CatcherSurface extends SurfaceView implements SurfaceHolder.Callback {

    public CatcherThread catcherThread;
    public CatcherSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

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
                //
            }
        }
    }
    public class CatcherThread extends Thread{
        private final SurfaceHolder surfaceHolder;

        private volatile boolean running = true;
        public Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        public CatcherThread(SurfaceHolder surfaceHolder){
            this.surfaceHolder = surfaceHolder;

        }
        public void requestStop(){running = false; }

        @Override
        public void run() {
            while (running){
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null){
                    try {
                        //
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);

                    }
                }
            }
        }
    }
}
