package com.example.mycontacts;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.util.Measure;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomContact extends View {

    enum SwipeDirection {
        LEFT, RIGHT, STAY
    }


    Rect left = new Rect();
    Rect right = new Rect();
    Paint textPaint, leftPaint, rightPaint;
    int left_x, left_y, left_width, left_heigth;
    int right_x, right_y, right_width, right_heigth;
    int viewHight = 100;
    String contact;

    SwipeDirection swipeDirection = SwipeDirection.STAY;
    float startX, startY, endX, endY;

    public CustomContact(Context context) {
        super(context);
        init();
    }

    public CustomContact(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomContact);
        int name = typedArray.getInt(R.styleable.CustomContact_user_name,0);
        typedArray.recycle();
        init();
    }

    public CustomContact(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, viewHight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(canvas == null) {
            return;
        }
        canvas.drawColor(Color.WHITE);
        left.set(0, 0 ,left_width, left_heigth);
        right.set(getWidth() - right_width, 0 , getWidth(), right_heigth);
        canvas.drawRect(left, leftPaint);
        canvas.drawRect(right, rightPaint);
        Rect textRect = new Rect();
        textPaint.getTextBounds(contact,0,contact.length(),textRect); // для расположения текста посередине
        canvas.drawText(contact, getWidth()/2 - textRect.width()/2, getHeight()/2 + textRect.height()/2, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                startX = event.getX();
                startY = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                endX = event.getX();
                endY = event.getY();
                setSwipeDirection();
                break;
            }
        }
        return true;
    }

    public void setSwipeDirection(){
        float deltaX = endX - startX;
        float deltaY = endY - startY;
        if (deltaX > 100) {
            rightSwipe();
            swipeDirection = SwipeDirection.RIGHT;
        } else if (deltaX < -100) {
            leftSwipe();
            swipeDirection = SwipeDirection.LEFT;
        } else {
            swipeDirection = SwipeDirection.STAY;

        }
        Log.d("SWIPE", swipeDirection.name());
    }

    public void leftSwipe(){
        switch (swipeDirection) {
            case LEFT: {
                drawContact();
            }
            case RIGHT: {
                break;
            }
            case STAY: {
                drawCancel();
                break;
            }
        }
    }

    public void rightSwipe(){
        switch (swipeDirection) {
            case RIGHT: {
                drawContact();
                break;
            }
            case LEFT: {
                break;
            }
            case STAY: {
                drawCall();
                break;
            }
        }
    }

    private void drawCancel() {
        ValueAnimator cancelAnimator = ValueAnimator.ofInt(0, getWidth());
        cancelAnimator.setDuration(1000);
        cancelAnimator.setInterpolator(new DecelerateInterpolator());
        cancelAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animateCancel((int)cancelAnimator.getAnimatedValue());
            }
        });
        cancelAnimator.start();
        swipeDirection = SwipeDirection.RIGHT;
    }

    private void animateCancel(int animatedValue) {
        right_width = animatedValue;
        invalidate();
    }

    private void drawCall() {
        ValueAnimator callAnimator = ValueAnimator.ofInt(0, getWidth());
        callAnimator.setDuration(1000);
        callAnimator.setInterpolator(new DecelerateInterpolator());
        callAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animateCall((int)callAnimator.getAnimatedValue());
            }
        });
        callAnimator.start();
        swipeDirection = SwipeDirection.RIGHT;
    }

    private void animateCall(int animatedValue) {
        left_width = animatedValue;
        invalidate();
    }

    private void drawContact() {
    }

    public void init(){
         contact = "";
         textPaint = new Paint();
         textPaint.setColor(Color.BLACK);
         textPaint.setAntiAlias(true);
         textPaint.setTextSize(70.0f);
         textPaint.setStrokeWidth(2.0f);
         textPaint.setStyle(Paint.Style.FILL_AND_STROKE);


         leftPaint = new Paint();
         leftPaint.setColor(getResources().getColor(R.color.call_color));
         rightPaint = new Paint();
         rightPaint.setColor(getResources().getColor(R.color.cancel_color));
         left_width = 0;
         left_heigth = viewHight;
         right_width = 0;
         right_heigth = viewHight;
    }
}
