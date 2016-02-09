package com.example.jesper.surfaceviewdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


/**
 * Created by jesper on 2/2/16.
 */
public class CustomView extends SurfaceView implements SurfaceView.OnTouchListener, SurfaceHolder.Callback{
    private Paint paint;
    private Path path;
    private Paint textPaint;
    private Paint backPaint;
    private long currentTimeStamp;
    private long previousTimeStamp;

    public void setPaint(int color, int width) {
        this.paint.setColor(color);
        this.paint.setStrokeWidth(width);
    }

    public CustomView(Context context) {
        super(context);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.BEVEL);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);

        backPaint = new Paint();
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(Color.BLUE);
        this.setOnTouchListener(this); //Enable OnTouchListener events
        this.getHolder().addCallback(this);//Enable SurfaceHolder events
    }

    @Override //Will be called by Worker Thread
    protected void onDraw(Canvas canvas) {
        Log.wtf("Current Thread", Thread.currentThread().getName());
        Log.wtf("Thread Count", Integer.toString(Thread.activeCount()));

        if(path != null) {
            canvas.drawPath(path, paint);
            currentTimeStamp = System.currentTimeMillis();
            if(previousTimeStamp != 0) {
                float fps = 1000 / (currentTimeStamp - previousTimeStamp);
                canvas.drawRect(5, 100, 100, 150, backPaint);
                canvas.drawText("Fps: " + fps, 5, 150, textPaint);
            }
            previousTimeStamp = currentTimeStamp;
        }
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                path.lineTo(event.getX(), event.getY());
                break;
            default:
                return false;
        }

        new WorkerThread(this).start();
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.wtf("", "");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

}
