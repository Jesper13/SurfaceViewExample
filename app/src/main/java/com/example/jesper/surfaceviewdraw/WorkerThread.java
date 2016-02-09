package com.example.jesper.surfaceviewdraw;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by jesper on 2/2/16.
 */
public class WorkerThread extends Thread {
    private CustomView customView;
    private Canvas canvas;
    private Rect dirtyRect;
    private WeakReference<CustomView> customViewWeakReference; //To ensure GC

    public WorkerThread(CustomView customView) {
        this.customViewWeakReference = new WeakReference<>(customView);
        //this.customView = customView;
    }

    public WorkerThread(CustomView customView, Rect dirty) {
        //this.customViewWeakReference = new WeakReference<>(customView);
        this.dirtyRect = dirty;
        //this.customView = customView;
    }


    @Override
    public void run() {
        try{
            if(dirtyRect != null)
                canvas = customViewWeakReference.get().getHolder().lockCanvas(new Rect(0, 0, 1, 1));
            else {
                canvas = customViewWeakReference.get().getHolder().lockCanvas(); //Prepare canvas for drawing
            }

            synchronized (customViewWeakReference.get().getHolder()){
                customViewWeakReference.get().onDraw(canvas);
            }
        }catch(Exception e){
            Log.wtf("Thread Exception", e.getMessage());
        }finally{
            if(canvas != null) {
                customViewWeakReference.get().getHolder().unlockCanvasAndPost(canvas); //Release canvas to UI
            }
        }
    }
}
