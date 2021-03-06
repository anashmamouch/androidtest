package com.benzino.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Anas on 05/07/2015.
 */
public class Loop extends Thread {

    private long time;
    private final long fps = 40;
    private boolean run = false;
    private AnimationView view;
    private SurfaceHolder holder;


    /**
     * Constructs a new {@code Thread} with no {@code Runnable} object and a
     * newly generated name. The new {@code Thread} will belong to the same
     * {@code ThreadGroup} as the {@code Thread} calling this constructor.
     *
     * @see ThreadGroup
     * @see Runnable
     */
    public Loop(AnimationView view) {
        this.view = view;
        holder = view.getHolder();
    }

    public long getTime() {
        return time;
    }

    /**
     * gives permission to the thread to run or not
     * @param run if set to true the thread is running stops the thread if set to false
     */
    public void isRunning(boolean run) {
        this.run = run;
    }

    /**
     * Check if it has permission to run
     * Check if the required time has passed to keep in line with the FPS
     * Set the canvas to empty
     * Get a reference to the canvas and lock it to prepare for drawing
     * Update the physics of the ball
     * Draw the ball in the new position
     * if it is safe to do so lock and update the canvas
     *
     * @see Thread#start
     */
    @Override
    public void run() {
        Canvas canvas;
        while(run){
            long cTime = System.currentTimeMillis();
            if((cTime - time) <= (1000/fps)){
                canvas = null;
                try{
                    canvas = holder.lockCanvas(null);
                    view.updatePhysics();
                    view.onDraw(canvas);
                }finally{
                    if(canvas !=null)
                        holder.unlockCanvasAndPost(canvas);
                }
            }
            time = cTime;
        }
    }
}
