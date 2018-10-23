package com.hencoder.plus.ljuns;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import com.hencoder.plus.Utils;

/**
 * @author ljuns
 * Created at 2018/10/23.
 * I am just a developer.
 */

/**
 * 1、PathEffect 可以理解为轨迹样式
 * 2、PathMeasure 这里用来测量 path 的周长
 */
public class DashboardView extends View {

    private static final int RADIUS = (int) Utils.dp2px(80);
    private static final int ANGLE = 120;
    private static final int LENGTH = (int) Utils.dp2px(60);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PathDashPathEffect mEffect;
    private Path mDash = new Path();

    public DashboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Utils.dp2px(2));

        // 获取弧的长度
        Path arc = new Path();
        arc.addArc(Utils.dp2px(180) / 2 - RADIUS, Utils.dp2px(180) / 2 - RADIUS,
                   Utils.dp2px(180) / 2 + RADIUS, Utils.dp2px(180) / 2 + RADIUS,
                   ANGLE + 30, 360 - ANGLE);
        PathMeasure measure = new PathMeasure(arc, false);
        float length = measure.getLength();

        // 刻度轨迹
        mDash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CW);
        mEffect = new PathDashPathEffect(mDash, (length - Utils.dp2px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) Utils.dp2px(180), (int) Utils.dp2px(180));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画弧
        canvas.drawArc(Utils.dp2px(180) / 2 - RADIUS, Utils.dp2px(180) / 2 - RADIUS,
                       Utils.dp2px(180) / 2 + RADIUS, Utils.dp2px(180) / 2 + RADIUS,
                       ANGLE + 30, 360 - ANGLE, false, mPaint);

        // 画刻度
        mPaint.setPathEffect(mEffect);
        canvas.drawArc(Utils.dp2px(180) / 2 - RADIUS, Utils.dp2px(180) / 2 - RADIUS,
                       Utils.dp2px(180) / 2 + RADIUS, Utils.dp2px(180) / 2 + RADIUS,
                       ANGLE + 30, 360 - ANGLE, false, mPaint);
        mPaint.setPathEffect(null);

        // 画指针
        canvas.drawLine(Utils.dp2px(180) / 2, Utils.dp2px(180) / 2,
                        (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + Utils.dp2px(180) / 2,
                        (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + Utils.dp2px(180) / 2,
                        mPaint);
    }

    /**
     * 通过刻度获取对应的角度
     * @param mark
     * @return
     */
    private float getAngleFromMark(int mark) {
        return (360 - ANGLE) / 20 * mark + 30 + ANGLE;
    }
}
