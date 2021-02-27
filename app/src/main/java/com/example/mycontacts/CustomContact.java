package com.example.mycontacts;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    int left_rightBottom, right_leftBottom;
    int left_x, left_y, left_width, left_heigth;
    int right_x, right_y, right_width, right_heigth;
    int viewHight = 100;
    String contact;

    SwipeDirection swipeDirection = SwipeDirection.STAY;
    SwipeDirection prevDirection = SwipeDirection.STAY;
    float startX, startY, endX, endY;

    public CustomContact(Context context) {
        super(context);
        init(((MainActivity) context).contactRV.getWidth());
    }

    public CustomContact(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomContact);
        int name = typedArray.getInt(R.styleable.CustomContact_user_name, 0);
        typedArray.recycle();
        init(((MainActivity) context).contactRV.getWidth());
    }

    public CustomContact(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(((MainActivity) context).contactRV.getWidth());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, viewHight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.WHITE);
        left.set(left_rightBottom - getWidth(), 0, left_rightBottom, left_heigth);
        right.set(right_leftBottom, 0, right_leftBottom + getWidth(), right_heigth);
        canvas.drawRect(left, leftPaint);
        canvas.drawRect(right, rightPaint);
        Resources resources = getResources();
        Bitmap callSource = BitmapFactory.decodeResource(resources, R.drawable.call);
        Bitmap call = Bitmap.createScaledBitmap(callSource, left_heigth - 20, left_heigth - 20, false);
        canvas.drawBitmap(call,
                left_rightBottom - getWidth() / 2 - call.getWidth() / 2,
                left_heigth / 2 - call.getHeight() / 2,
                textPaint);
        Bitmap cancelSource = BitmapFactory.decodeResource(resources, R.drawable.cancel);
        Bitmap cancel = Bitmap.createScaledBitmap(cancelSource, left_heigth - 20, left_heigth - 20, false);
        canvas.drawBitmap(cancel,
                right_leftBottom + getWidth() / 2 - call.getWidth() / 2,
                right_heigth / 2 - call.getHeight() / 2,
                textPaint);
        Rect textRect = new Rect();
        textPaint.getTextBounds(contact, 0, contact.length(), textRect); // для расположения текста посередине
        if(swipeDirection == SwipeDirection.LEFT) {
            canvas.drawText(contact, left_rightBottom + getWidth() / 2 - textRect.width() / 2, getHeight() / 2 + textRect.height() / 2, textPaint);
            prevDirection = SwipeDirection.LEFT;
        }
        if (swipeDirection == SwipeDirection.RIGHT){
            canvas.drawText(contact, right_leftBottom - getWidth()/2 - textRect.width()/2, getHeight() / 2 + textRect.height() / 2, textPaint);
            prevDirection = SwipeDirection.RIGHT;
        }
        if (swipeDirection == SwipeDirection.STAY)
            if (prevDirection == SwipeDirection.LEFT) {
                canvas.drawText(contact, left_rightBottom + getWidth() / 2 - textRect.width() / 2, getHeight() / 2 + textRect.height() / 2, textPaint);
            } else if (prevDirection == SwipeDirection.RIGHT) {
                canvas.drawText(contact, right_leftBottom - getWidth()/2 - textRect.width()/2, getHeight() / 2 + textRect.height() / 2, textPaint);
            } else {
                canvas.drawText(contact, getWidth() / 2 - textRect.width() / 2, getHeight() / 2 + textRect.height() / 2, textPaint);
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
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

    public void setSwipeDirection() {
        float deltaX = endX - startX;
        float deltaY = endY - startY;
        if (deltaX > 100) {
            rightSwipe();
        } else if (deltaX < -100) {
            leftSwipe();
        } else {
        }
        Log.d("SWIPE", swipeDirection.name());
    }

    public void leftSwipe() {
        switch (swipeDirection) {
            case LEFT: {
                drawContact();
                break;
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

    public void rightSwipe() {
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
        ValueAnimator cancelAnimator = ValueAnimator.ofInt(getWidth(), 0);
        cancelAnimator.setDuration(1000);
        cancelAnimator.setInterpolator(new DecelerateInterpolator());
        cancelAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animateCancel((int) cancelAnimator.getAnimatedValue());
            }
        });
        swipeDirection = SwipeDirection.RIGHT;
        cancelAnimator.start();
    }

    private void animateCancel(int animatedValue) {
        right_leftBottom = animatedValue;
        invalidate();
    }

    private void drawCall() {
        ValueAnimator callAnimator = ValueAnimator.ofInt(0, getWidth());
        callAnimator.setDuration(1000);
        callAnimator.setInterpolator(new DecelerateInterpolator());
        callAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animateCall((int) callAnimator.getAnimatedValue());
            }
        });
        swipeDirection = SwipeDirection.LEFT;
        callAnimator.start();

    }

    private void animateCall(int animatedValue) {
        left_rightBottom = animatedValue;
        invalidate();
    }

    private void drawContact() {
        ValueAnimator closeCallAnimator = ValueAnimator.ofInt(getWidth(), 0);
        closeCallAnimator.setDuration(1000);
        closeCallAnimator.setInterpolator(new DecelerateInterpolator());
        closeCallAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animateCall((int) closeCallAnimator.getAnimatedValue());
            }
        });
        ValueAnimator closeCancelAnimator = ValueAnimator.ofInt(0, getWidth());
        closeCancelAnimator.setDuration(1000);
        closeCancelAnimator.setInterpolator(new DecelerateInterpolator());
        closeCancelAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animateCancel((int) closeCancelAnimator.getAnimatedValue());
            }
        });
        if (swipeDirection == SwipeDirection.LEFT) {
            closeCallAnimator.start();
        } else if (swipeDirection == SwipeDirection.RIGHT) {
            closeCancelAnimator.start();
        }
        swipeDirection = SwipeDirection.STAY;
    }

    public void init(int recycle_view_width) {
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
        left_width = getWidth();
        left_heigth = viewHight;
        right_width = getWidth();
        right_heigth = viewHight;

        left_rightBottom = 0;
        right_leftBottom = recycle_view_width;
    }
}
