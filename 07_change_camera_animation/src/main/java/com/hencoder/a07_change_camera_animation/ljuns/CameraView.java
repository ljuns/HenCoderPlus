package com.hencoder.a07_change_camera_animation.ljuns;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.hencoder.a07_change_camera_animation.R;
import com.hencoder.a07_change_camera_animation.Utils;

/**
 * @author ljuns
 * Created at 2018/10/26.
 */
public class CameraView extends View {

    private static final int IMAGE_SIZE = (int) Utils.dpToPixel(200);

    private float mRotateWithZ = 0;
    private float mDeg = 0;
    private float mDegTop = 0;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Camera mCamera = new Camera();
    private Bitmap mBitmap;

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mBitmap = getAvatar(IMAGE_SIZE);
    }

    public float getRotateWithZ() {
        return mRotateWithZ;
    }

    public void setRotateWithZ(float rotateWithZ) {
        mRotateWithZ = rotateWithZ;
        invalidate();
    }

    public float getDeg() {
        return mDeg;
    }

    public void setDeg(float deg) {
        mDeg = deg;
        invalidate();
    }

    public float getDegTop() {
        return mDegTop;
    }

    public void setDegTop(float degTop) {
        mDegTop = degTop;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getWidth() / 2,  getHeight() / 2);
        canvas.rotate(-mRotateWithZ);
        mCamera.save();
        mCamera.rotateX(mDegTop);
        mCamera.applyToCanvas(canvas);
        mCamera.restore();
        canvas.clipRect(-IMAGE_SIZE, -IMAGE_SIZE, IMAGE_SIZE, 0);
        canvas.rotate(mRotateWithZ);
        canvas.translate(- getWidth() / 2, - getHeight() / 2);
        canvas.drawBitmap(mBitmap, getWidth() / 2 - IMAGE_SIZE / 2, getHeight() / 2 - IMAGE_SIZE / 2, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(getWidth() / 2,  getHeight() / 2);
        canvas.rotate(-mRotateWithZ);
        mCamera.save();
        mCamera.rotateX(mDeg);
        mCamera.applyToCanvas(canvas);
        mCamera.restore();
        canvas.clipRect(-IMAGE_SIZE, 0, IMAGE_SIZE, IMAGE_SIZE);
        canvas.rotate(mRotateWithZ);
        canvas.translate(- getWidth() / 2, - getHeight() / 2);
        canvas.drawBitmap(mBitmap, getWidth() / 2 - IMAGE_SIZE / 2, getHeight() / 2 - IMAGE_SIZE / 2, mPaint);
        canvas.restore();

    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
    }
}
