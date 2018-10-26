package com.hencoder.plus.ljuns;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.hencoder.plus.R;
import com.hencoder.plus.Utils;

/**
 * @author ljuns
 * Created at 2018/10/26.
 */
public class SportView extends View {

    private static final int SIZE = (int) Utils.dp2px(160);
    private static final int RADIUS = (int) Utils.dp2px(70);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mRect = new Rect();
    private Paint.FontMetrics mFontMetrics = new Paint.FontMetrics();

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextSize(Utils.dp2px(30));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.getFontMetrics(mFontMetrics);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(SIZE, SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Utils.dp2px(10));
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(SIZE / 2, SIZE / 2, RADIUS, mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawArc(SIZE / 2 - RADIUS, SIZE / 2 - RADIUS,
                       SIZE / 2 + RADIUS, SIZE / 2 + RADIUS,
                       -45, 135, false, mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);

        String text = "文字居中";

        // 使用 getTextBounds()
        mPaint.getTextBounds(text, 0, text.length(), mRect);
        int centerPoint = (mRect.bottom - mRect.top) / 2;

        // 使用 getFontMetrics()
        //float centerPoint = (mFontMetrics.descent - mFontMetrics.ascent) / 2;

        canvas.drawText(text, SIZE / 2, SIZE / 2 + centerPoint, mPaint);
        canvas.restore();
    }
}
