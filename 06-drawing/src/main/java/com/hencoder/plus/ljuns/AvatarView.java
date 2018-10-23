package com.hencoder.plus.ljuns;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.hencoder.plus.R;
import com.hencoder.plus.Utils;

/**
 * @author ljuns
 * Created at 2018/10/23.
 */

/**
 * 1、PorterDuff.Mode 各种模式
 * 2、Xfermode 是原图像和目标图像的组合，所以应该作用在原图像上
 */
public class AvatarView extends View {

    private static final int WIDTH = (int) Utils.dp2px(150);
    private static final int PADDING = (int) Utils.dp2px(15);
    private static final int EDGE_WIDTH = (int) Utils.dp2px(10);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private Xfermode mXfermode;

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mBitmap = getAvatar(WIDTH);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) Utils.dp2px(180), (int) Utils.dp2px(180));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, mPaint);
        int save = canvas.saveLayer(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, mPaint);

        canvas.drawOval(PADDING + EDGE_WIDTH, PADDING + EDGE_WIDTH,
                        PADDING + WIDTH - EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, mPaint);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mBitmap, PADDING, PADDING, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(save);
    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
    }
}
