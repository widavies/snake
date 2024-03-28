package com.fouram.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class Renderer extends View implements Runnable {

    // This is your update function, it's called rapidly
    public void update() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
         * Clear
         */

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);


    }

    public float downX, downY;
    public float upX, upY;
    // Get x and y and follow user motion events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        // Checks for the event that occurs
        switch (event.getAction()) {
                    // track when finger goes down (1 point to another, measure x and y delta
            case MotionEvent.ACTION_DOWN:
                // Starts a new line in the path
                downX = pointX;
                downY = pointY;
                break;
            case MotionEvent.ACTION_MOVE:
                // Draws line between last point and this point
                break;
            case MotionEvent.ACTION_UP:
                upX = pointX;
                upY = pointY;
                detectSwipe();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }
    public void detectSwipe(){
        float dx = upX - downX;
        float dy = upY - downY;
        if(Math.abs(dx) > 100 && Math.abs(dy) < 100){
            if(dx > 0){
                System.out.println("Right swipe");
            }
            else{
                System.out.println("Left swipe");
            }
        }
        else if(Math.abs(dy) > 100 && Math.abs(dx) < 100){
            if(dy > 0){
                System.out.println("Down swipe");
            }
            else{
                System.out.println("Up swipe");
            }
        }
    }

    /*
     *
     */

    private Paint paint = new Paint();
    public Renderer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void run() {
        int FPS = 500;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
                postInvalidate();
            }
        }, 0, (int)(1000.0 / (double)FPS));
    }
}
