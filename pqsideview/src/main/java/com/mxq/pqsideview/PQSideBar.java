package com.mxq.pqsideview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;



/*
 *Create by 11626 on 2019/7/12
 */

public class PQSideBar extends View {
    private float mW, mH;
    private Paint mPaint;
    private int mTextNormalColor;
    private int mTextSelectColor;
    private int mHintTextColor;
    private int mHintBgColor;
    private int mBgColor;
    private int mBgSelectColor;
    public static String[] indexes;
    private int mChoose = -1;
    private Path mPath;
    private float mBarW;

    private float mLine, mDialogW, mDialogH;

    public PQSideBar(Context context) {
        this (context, null);
    }

    public PQSideBar(Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);
        indexes = new String[]{"A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
        TypedArray array = context.obtainStyledAttributes (attrs, R.styleable.PQSideBar);
        mTextNormalColor = array.getColor (R.styleable.PQSideBar_text_normal_color, Color.BLACK);
        mTextSelectColor = array.getColor (R.styleable.PQSideBar_text_select_color, Color.RED);
        mHintTextColor = array.getColor (R.styleable.PQSideBar_hint_text_color, Color.WHITE);
        mHintBgColor = array.getColor (R.styleable.PQSideBar_hint_bg_color, Color.GRAY);
        mBgColor = array.getColor (R.styleable.PQSideBar_bg_color, Color.GRAY);
        mBgSelectColor = array.getColor (R.styleable.PQSideBar_bg_select_color, Color.BLUE);
        mPaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias (true);
        mPath = new Path ();
        mBarW = dp2px (90);
        mDialogH = dp2px (50);

    }



    public PQSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw (canvas);
        setPadding (0, 0, 0, 0);
        mPath.reset ();
        mPaint.setColor (mBgColor);
        canvas.drawRoundRect (0, 0, mW, mH, 0, 0, mPaint);
        mPaint.setColor (mTextNormalColor);
        float a = (mH - mDialogH) / indexes.length;
        mPaint.setTextSize (a - 20);//10 为上下padding
        mPaint.setTextAlign (Paint.Align.CENTER);
        for (int i = 0; i < indexes.length; i++) {
            canvas.drawText (indexes[i], mBarW + (mW - mBarW) / 2, a / 2 - (mPaint.descent () + mPaint.ascent ()) / 2 + a * i + mDialogH / 2, mPaint);
        }
        if (mChoose >= 0) {
            mPaint.setColor (mBgSelectColor);
            canvas.drawCircle (mBarW + (mW - mBarW) / 2, a * mChoose + a / 2 + mDialogH / 2, a / 2, mPaint);
            mPaint.setColor (mTextSelectColor);
            canvas.drawText (indexes[mChoose], mBarW + (mW - mBarW) / 2, a / 2 - (mPaint.descent () + mPaint.ascent ()) / 2 + a * mChoose + mDialogH / 2, mPaint);

            mPaint.setColor (mHintBgColor);
            canvas.drawCircle (mDialogH / 2 + 5, a * mChoose + a / 2 + mDialogH / 2, mDialogH / 2, mPaint);
            mPath.moveTo (mDialogH / 2 + mLine + 5, a * mChoose + a / 2 + mDialogH / 2 - mLine);
            mPath.lineTo (mDialogW + 5, a * mChoose + a / 2 + mDialogH / 2);
            mPath.lineTo (mDialogH / 2 + mLine + 5, a * mChoose + a / 2 + mLine + mDialogH / 2);
            mPaint.setStrokeJoin (Paint.Join.MITER);
            canvas.drawPath (mPath, mPaint);
            mPaint.setColor (Color.WHITE);
            mPaint.setTextAlign (Paint.Align.CENTER);
            int size = sp2px (25);
            if (size > mDialogH) {
                size = (int) mDialogH;
            }
            mPaint.setTextSize (size);
            mPaint.setColor (mHintTextColor);
            canvas.drawText (indexes[mChoose], mDialogH / 2 + 5, a * mChoose + a / 2 - (mPaint.descent () + mPaint.ascent ()) / 2 + mDialogH / 2, mPaint);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);
        mW = MeasureSpec.getSize (widthMeasureSpec);
        mH = MeasureSpec.getSize (heightMeasureSpec);

        mLine = (float) (mDialogH / 2 / Math.sqrt (2));
        mDialogW = (int) (mDialogH / 2 + 2 * mLine);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //1为放开，2为滑动，3为事件被父控件拦截
        int action = event.getAction ();
        float y = event.getY ();
        float x = event.getX ();
        int oldChoose = mChoose;

        int c = (int) ((y - mDialogH / 2) / (mH - mDialogH) * indexes.length);
        if ((x > mBarW) || (x < mBarW && mChoose != -1)) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mChoose = -1;
                    invalidate ();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (c != oldChoose && c >= 0 && c < indexes.length) {
                        if (listener != null) {
                            listener.onClick (c, indexes[c]);
                        }

                        mChoose = c;
                        invalidate ();
                    }
                    break;
                default:
            }
            return true;
        } else {
            return super.dispatchTouchEvent (event);
        }


    }

    public interface onTouchListener {
        void onClick(int choose, String str);
    }

    private onTouchListener listener;

    public void setOnListener(onTouchListener listener) {
        this.listener = listener;
    }
    /**
     * dp转px
     */
    public  int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,getResources().getDisplayMetrics());
    }
    protected int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,getResources().getDisplayMetrics());
    }


}
