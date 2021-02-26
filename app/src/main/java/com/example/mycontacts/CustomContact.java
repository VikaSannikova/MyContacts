package com.example.mycontacts;

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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomContact extends View {
    Rect left = new Rect();
    Rect right = new Rect();
    Paint textPaint, leftPaint, rightPaint;
    int left_x, left_y, left_width, left_heigth;
    int right_x, right_y, right_width, right_heigth;
    int viewHight = 100;

    String contact;

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
        canvas.drawText(contact, getWidth()/2, getHeight()/2, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    public void init(){
         contact = "";
         textPaint = new Paint();
         textPaint.setColor(Color.BLACK);
         leftPaint = new Paint();
         leftPaint.setColor(getResources().getColor(R.color.call_color));
         rightPaint = new Paint();
         rightPaint.setColor(getResources().getColor(R.color.cancel_color));
         left_width = 200;
         left_heigth = viewHight;
         right_width = 200;
         right_heigth = viewHight;
    }
}
