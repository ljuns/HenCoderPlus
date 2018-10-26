package com.hencoder.plus.ljuns;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class NewsView extends View {

    private static final String TEXT =
        "最近听说，家门口在拆违时，工人们在墙壁里看到了两具尸体，腐烂时间长达二十年。如果不是此番整治，也许没有人会发现这一切。这不是我第一次听说家附近的杀人案。好多年前，在电视台的法制节目里，有一位妇女因为不堪忍受家暴，杀害了自己的丈夫。她每天活动的地点我都会经过，我已经不记得具体的是非，但每天走到那里，还是会感到恐惧。恐惧的不是杀人本身，而是忍耐的人在不堪忍耐以前，所度过的日复一日是那么平常。";

    private static final int SIZE = (int) Utils.dp2px(200);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    float[] cutWidth = new float[1];
    private Paint.FontMetrics mFontMetrics = new Paint.FontMetrics();

    public NewsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mPaint.setTextSize(Utils.dp2px(15));
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.getFontMetrics(mFontMetrics);
        mBitmap = getAvatar((int) Utils.dp2px(100));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 自动换行
        //TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        //textPaint.setTextSize(Utils.dp2px(15));
        //StaticLayout staticLayout = new StaticLayout(TEXT, textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        //staticLayout.draw(canvas);

        // 图文混排
        /**
         * 重点难点：
         * 1、测量可以绘制的长度 maxWidth
         * 2、文字的坐标（主要是纵坐标 verticalOffset)
         */
        canvas.drawBitmap(mBitmap, Utils.dp2px(5), Utils.dp2px(50), mPaint);
        int length = TEXT.length();
        int verticalOffset = (int) (mFontMetrics.bottom - mFontMetrics.top);
        int horizontalOffset;

        int top = 0;
        int bottom = 0;
        for (int start = 0; start < length; ) {
            bottom += verticalOffset;
            top = bottom - verticalOffset;

            float maxWidth;
            if (bottom > Utils.dp2px(50) && top < Utils.dp2px(50) + Utils.dp2px(100)) {
                maxWidth = getWidth() - Utils.dp2px(100) - Utils.dp2px(2);
                horizontalOffset = (int) (Utils.dp2px(5) + Utils.dp2px(100));
            } else {
                maxWidth = getWidth() - Utils.dp2px(2);
                horizontalOffset = (int) Utils.dp2px(5);
            }

            // breakText() 参数分别为：整段文本、开始测量的起始、整段文本长度、true、可以测量的长度、float[]
            int count = mPaint.breakText(TEXT, start, length, true, maxWidth, cutWidth);
            // drawText() 参数分别为：整段文本、开始绘制的文本起始、绘制的文本长度、左下角 x 坐标、左下角 y 坐标、Paint
            canvas.drawText(TEXT, start, start + count, horizontalOffset, bottom, mPaint);

            start += count;
        }
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
