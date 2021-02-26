package com.example.mycontacts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomContact extends LinearLayout {
    TextView leftTV, centerTV, rightTV;



    public CustomContact(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomContact(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public CustomContact(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    public TextView getLeftTV() {
        return leftTV;
    }

    public void setLeftTV(TextView leftTV) {
        this.leftTV = leftTV;
    }

    public TextView getCenterTV() {
        return centerTV;
    }

    public void setCenterTV(TextView centerTV) {
        this.centerTV = centerTV;
    }

    public TextView getRightTV() {
        return rightTV;
    }

    public void setRightTV(TextView rightTV) {
        this.rightTV = rightTV;
    }

    public CustomContact(@NonNull Context context) {
        super(context);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {

            }
            case MotionEvent.ACTION_UP:  {

            }
            case MotionEvent.ACTION_MOVE: {

            }
            case MotionEvent.ACTION_CANCEL: {

            }
        }
        return true;
    }

    private void init(){
        inflate(getContext(), R.layout.contact_row,this);
        leftTV = (TextView) findViewById(R.id.make_call);
        centerTV = (TextView) findViewById(R.id.contact_name);
        rightTV = (TextView) findViewById(R.id.cancel_call);

        centerTV.setBackgroundColor(Color.CYAN);
        //centerTV.setWidth(500);
        centerTV.setGravity(Gravity.CENTER);


        leftTV.setVisibility(GONE);
        rightTV.setVisibility(GONE);
        centerTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        this.invalidate();
    }
}
