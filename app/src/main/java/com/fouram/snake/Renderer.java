package com.fouram.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Renderer extends View {
    private Path path = new Path();
    private Paint paint = new Paint();


    //Constructor
    public Renderer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        PaintSettings();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
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
                path.moveTo(pointX, pointY);
                downX = pointX;
                downY = pointY;
                break;
            case MotionEvent.ACTION_MOVE:
                // Draws line between last point and this point
                path.lineTo(pointX, pointY);
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


    private void PaintSettings() {

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);


    }

}
