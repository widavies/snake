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

    /*
     *
     *
     * Put variables here
     *
     *
     */

    // The size of the snake, in pixels
    public int snakeSize = 50;

    // The position of the snake's head
    public int snakeX = 500, snakeY = 600;
    public int snakeDirection = 0; // 0 is up, 1 is right, 2 is down, 3 is left

    /*
     *
     *
     *  Functions and logic goes here
     *
     */

    // This is your update function, it's called rapidly
    // (about 60 times a second, or 60 FPS). You can do all your
    // logic checking here
    public void update() {
        // Step 1: Update snakeX and snakeY by 5 depending on what snakeDirection is

    }

    // Here is where you draw things to the screen,
    // you can set the characteristics of what you draw
    // with "paint", you can use paint.setColor(Color.GREEN)
    // to change the color for example. To draw shapes, text, or other
    // objects, use "canvas", canvas.drawRect(..) will put a rectangle on
    // the screen. This function is also called 60 times per second, right
    // after the update function.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
         * Clear the screen to black
         */

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        /*
         * Draw the snake head
         */
        paint.setColor(Color.GREEN);

        canvas.drawRect(snakeX, snakeY, snakeX + snakeSize, snakeY + snakeSize, paint);


    }

    // Input functions

    public void onSwipeLeft() {
        // user swiped left, what should we do?
        snakeDirection = 3;
    }

    public void onSwipeRight() {
    }

    public void onSwipeDown() {

    }

    public void onSwipeUp() {

    }


    /*
     * DO NOT EDIT PAST HERE
     *
     *
     * You don't need to modify anything down here,
     * look over it though if you're curious how it works
     *
     *
     * DO NOT EDIT HERE
     */

    public float downX, downY;
    public float upX, upY;
    private Paint paint = new Paint();
    public Renderer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        new Thread(this).start();
    }

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
                onSwipeRight();
            }
            else{
                onSwipeLeft();
            }
        }
        else if(Math.abs(dy) > 100 && Math.abs(dx) < 100){
            if(dy > 0){
                onSwipeDown();
            }
            else{
                onSwipeUp();
            }
        }
    }

    @Override
    public void run() {
        int FPS = 60;

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
