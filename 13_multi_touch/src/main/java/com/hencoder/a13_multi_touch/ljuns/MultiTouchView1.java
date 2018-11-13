package com.hencoder.a13_multi_touch.ljuns;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.Nullable;
import com.hencoder.a13_multi_touch.Utils;

/**
 * @author ljuns
 * Created at 2018/11/12.
 */
public class MultiTouchView1 extends View {

    private static final float IMAGE_WIDTH = Utils.dpToPixel(200);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;

    // Bitmap 位置
    private float mOffsetX;
    private float mOffsetY;

    // 点击位置
    private float mDownX;
    private float mDownY;

    // 缓存上次的 Bitmap 位置
    private float mOriginalOffsetX;
    private float mOriginalOffsetY;

    // 跟踪的 pointer id
    private int mTrackingPointerId;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ViewConfiguration.getDoubleTapTimeout()
                mTrackingPointerId = event.getPointerId(0);
                mDownX = event.getX();
                mDownY = event.getY();
                // 缓存位置
                mOriginalOffsetX = mOffsetX;
                mOriginalOffsetY = mOffsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                // 通过 pointer id 找到对应的 index
                int index = event.findPointerIndex(mTrackingPointerId);
                // 基于上次的位置
                // - mDown 是因为绘制时坐标是左上角
                mOffsetX = mOriginalOffsetX + event.getX(index) - mDownX;
                mOffsetY = mOriginalOffsetY + event.getY(index) - mDownY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // 获取当前 pointer 的 index
                int actionIndex = event.getActionIndex();
                // 根据 index 拿到对应的 id
                mTrackingPointerId = event.getPointerId(actionIndex);
                mDownX = event.getX(actionIndex);
                mDownY = event.getY(actionIndex);
                // 缓存位置
                mOriginalOffsetX = mOffsetX;
                mOriginalOffsetY = mOffsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // 获取当前事件的 pointer index
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                // 通过 ID 判断当前事件的 pointer 是不是正在跟踪的 pointer
                if (pointerId == mTrackingPointerId) {
                    // 自定义规则：把事件交给最后一个 pointer
                    int newIndex;
                    // 用 index 来遍历，因为 index 是连续的，id 不是连续的
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    mTrackingPointerId = event.getPointerId(newIndex);
                    mDownX = event.getX(actionIndex);
                    mDownY = event.getY(actionIndex);
                    // 缓存位置
                    mOriginalOffsetX = mOffsetX;
                    mOriginalOffsetY = mOffsetY;
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mOffsetX, mOffsetY, mPaint);
    }
}
