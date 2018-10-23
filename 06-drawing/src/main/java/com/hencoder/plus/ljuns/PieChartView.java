package com.hencoder.plus.ljuns;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.hencoder.plus.Utils;

/**
 * @author ljuns
 * Created at 2018/10/23.
 * I am just a developer.
 */
public class PieChartView extends View {

    private static final int RADIUS = (int) Utils.dp2px(80);
    private static final int PADDING = (int) Utils.dp2px(10);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int[] colors = {Color.parseColor("#ffff00"), Color.parseColor("#ff0099"),
        Color.parseColor("#6666ff"), Color.parseColor("#339966")};
    int[] angles = {60, 120, 80, 100};

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) Utils.dp2px(180), (int) Utils.dp2px(180));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int currentAngle = 0;
        for (int i = 0; i < colors.length; i++) {
            mPaint.setColor(colors[i]);

            canvas.save();
            if (i == 2) {
                // 画布偏移，达到扇形被抽出的效果
                canvas.translate(-(float) Math.cos(Math.toRadians(angles[i] / 2)) * PADDING,
                                 -(float) Math.sin(Math.toRadians(angles[i] / 2)) * PADDING);
            }

            // 画扇形
            canvas.drawArc(Utils.dp2px(180) / 2 - RADIUS, Utils.dp2px(180) / 2 - RADIUS,
                           Utils.dp2px(180) / 2 + RADIUS, Utils.dp2px(180) / 2 + RADIUS,
                           currentAngle, angles[i], true, mPaint);

            canvas.restore();
            currentAngle += angles[i];
        }

    }
}
