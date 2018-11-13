package com.hencoder.a12_scalable.ljuns;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import com.hencoder.a12_scalable.Utils;

/**
 * @author ljuns
 * Created at 2018/11/13.
 */
public class ScalableImageView extends View
    implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, Runnable {

    private static final float IMAGE_WIDTH = Utils.dpToPixel(200);
    private static final int IMAGE_SCALE_LEVEL = 2;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private GestureDetectorCompat mDetector;
    private ObjectAnimator mScaleAnimator;
    private OverScroller mScroller;

    private float mOriginalOffsetX;
    private float mOriginalOffsetY;

    // 缩放比例
    private float mSmallScale;
    private float mBigScale;

    private float mTranslateX;
    private float mTranslateY;

    private boolean mBig;

    private float mScaleFraction;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mBitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
        mDetector = new GestureDetectorCompat(context, this);
        mScroller = new OverScroller(context);
        // 可以省略
        //mDetector.setOnDoubleTapListener(this);

        // 关闭或打开长按
        //mDetector.setIsLongpressEnabled(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mOriginalOffsetX = (getWidth() - mBitmap.getWidth()) / 2f;
        mOriginalOffsetY = (getHeight() - mBitmap.getHeight()) / 2f;

        // 以 view 的宽高比为基准，大于基准表示宽大于高，否则高大于宽
        if ((float) mBitmap.getWidth() / mBitmap.getHeight() > (float) getWidth() / getHeight()) {
            mSmallScale = (float) getWidth() / mBitmap.getWidth();
            mBigScale = (float) getHeight() / mBitmap.getHeight() * IMAGE_SCALE_LEVEL;
        } else {
            mSmallScale = (float) getHeight() / mBitmap.getHeight();
            mBigScale = (float) getWidth() / mBitmap.getWidth() * IMAGE_SCALE_LEVEL;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scale = mSmallScale + (mBigScale - mSmallScale) * mScaleFraction;
        canvas.translate(mTranslateX, mTranslateY);
        canvas.scale(scale, scale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(mBitmap, mOriginalOffsetX, mOriginalOffsetY, mPaint);
    }

    private ObjectAnimator getScaleAnimator() {
        if (mScaleAnimator == null) {
            mScaleAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1).setDuration(1500);
        }
        return mScaleAnimator;
    }

    private float getScaleFraction() {
        return mScaleFraction;
    }

    private void setScaleFraction(float scaleFraction) {
        mScaleFraction = scaleFraction;
        invalidate();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // 预按下，100 毫秒
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // 如果没有双击事件，在这里处理单击事件；如果有双击事件，在 onSingleTapConfirmed 处理单击事件
        return false;
    }

    /**
     * move
     * @param down      ACTION_DOWN
     * @param event     当前事件
     * @param distanceX 旧事件减去当前事件
     * @param distanceY 旧事件减去当前事件
     *
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
        if (mBig) {
            mTranslateX -= distanceX;
            mTranslateY -= distanceY;

            float maxX = (mBitmap.getWidth() * mBigScale - getWidth()) / 2f;
            float maxY = (mBitmap.getHeight() * mBigScale - getHeight()) / 2f;

            //if (mTranslateX > 0f) { // 右移
            //    if (mTranslateX > maxX) {
            //        mTranslateX = maxX;
            //    }
            //} else { // 左移
            //    if (- mTranslateX > maxX) {
            //        mTranslateX = - maxX;
            //    }
            //}

            // 简化
            mTranslateX = Math.min(mTranslateX, maxX);
            mTranslateX = Math.max(mTranslateX, -maxX);

            //if (mTranslateY > 0f) { // 上移
            //    if (mTranslateY > maxY) {
            //        mTranslateY = maxY;
            //    }
            //} else { // 下移
            //    if (- mTranslateY > maxY) {
            //        mTranslateY = - maxY;
            //    }
            //}

            // 简化
            mTranslateY = Math.min(mTranslateY, maxY);
            mTranslateY = Math.max(mTranslateY, -maxY);

            invalidate();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // 长按，500 毫秒
    }

    @Override
    public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {
        if (mBig) {
            mScroller.fling((int) mTranslateX, (int) mTranslateY, (int) velocityX, (int) velocityY,
                            -(int) (mBitmap.getWidth() * mBigScale - getWidth()) / 2,
                            (int) (mBitmap.getWidth() * mBigScale - getWidth()) / 2,
                            -(int) (mBitmap.getHeight() * mBigScale - getHeight()) / 2,
                            (int) (mBitmap.getHeight() * mBigScale - getHeight()) / 2);

            postOnAnimation(this);
        }
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        // 如果有双击事件，在这处理单击事件
        return false;
    }

    /**
     * 双击事件，间隔时间大于 40 毫秒，小于 300 毫秒
     * @param event 事件
     * @return
     */
    @Override
    public boolean onDoubleTap(MotionEvent event) {
        mBig = !mBig;
        if (mBig) {
            getScaleAnimator().start();
        } else {
            getScaleAnimator().reverse();
        }
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void run() {
        if (mScroller.computeScrollOffset()) {
            mTranslateX = mScroller.getCurrX();
            mTranslateY = mScroller.getCurrY();
            invalidate();

            postOnAnimation(this);
        }
    }
}
