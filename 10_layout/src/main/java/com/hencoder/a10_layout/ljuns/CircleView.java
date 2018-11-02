package com.hencoder.a10_layout.ljuns;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.hencoder.a10_layout.Utils;

/**
 * @author ljuns
 * Created at 2018/11/2.
 */
public class CircleView extends View {

    private static final int RADIUS = (int) Utils.dpToPixel(80);
    private static final int PADDING = (int) Utils.dpToPixel(0);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mSize = (RADIUS + PADDING) * 2;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = (RADIUS + PADDING) * 2;
        int height = (RADIUS + PADDING) * 2;

        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        mSize = Math.min(width, height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.RED);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        canvas.drawCircle(mSize / 2, mSize / 2, mSize / 2 - paddingLeft - PADDING, mPaint);

    }
}
