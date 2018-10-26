package com.hencoder.plus.ljuns;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.hencoder.plus.R;
import com.hencoder.plus.Utils;

/**
 * @author ljuns
 * Created at 2018/10/26.
 */
public class CameraView extends View {

    private static final int SIZE = (int) Utils.dp2px(300);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Camera mCamera = new Camera();
    private Bitmap mBitmap;

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mCamera.rotateX(45);
        mBitmap = getAvatar((int) Utils.dp2px(150));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.save();
        //canvas.clipRect(0, 0, getWidth(), Utils.dp2px(150) / 2);
        //canvas.drawBitmap(mBitmap, Utils.dp2px(100), 0, mPaint);
        //canvas.restore();
        //
        //canvas.save();
        //canvas.translate(Utils.dp2px(100) + Utils.dp2px(150) / 2,  Utils.dp2px(150) / 2);
        //mCamera.rotateX(30);
        //mCamera.applyToCanvas(canvas);
        //canvas.translate(- (Utils.dp2px(100) + Utils.dp2px(150) / 2), - Utils.dp2px(150) / 2);
        //canvas.clipRect(Utils.dp2px(100), Utils.dp2px(150) / 2, Utils.dp2px(100) + Utils.dp2px(150), Utils.dp2px(150));
        //canvas.drawBitmap(mBitmap, Utils.dp2px(100), 0, mPaint);
        //canvas.restore();

        canvas.save();
        canvas.translate(Utils.dp2px(100) + Utils.dp2px(150) / 2,  Utils.dp2px(150) / 2);
        canvas.rotate(-20);
        canvas.clipRect(-Utils.dp2px(150), -Utils.dp2px(150), Utils.dp2px(150), 0);
        canvas.rotate(20);
        canvas.translate(- (Utils.dp2px(100) + Utils.dp2px(150) / 2), - Utils.dp2px(150) / 2);
        canvas.drawBitmap(mBitmap, Utils.dp2px(100), 0, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(Utils.dp2px(100) + Utils.dp2px(150) / 2,  Utils.dp2px(150) / 2);
        canvas.rotate(-20);
        mCamera.applyToCanvas(canvas);
        canvas.clipRect(-Utils.dp2px(150), 0, Utils.dp2px(150), Utils.dp2px(150));
        canvas.rotate(20);
        canvas.translate(- (Utils.dp2px(100) + Utils.dp2px(150) / 2), - Utils.dp2px(150) / 2);
        canvas.drawBitmap(mBitmap, Utils.dp2px(100), 0, mPaint);
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
