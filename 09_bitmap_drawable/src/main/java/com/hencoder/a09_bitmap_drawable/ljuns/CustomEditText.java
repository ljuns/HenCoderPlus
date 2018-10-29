package com.hencoder.a09_bitmap_drawable.ljuns;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import com.hencoder.a09_bitmap_drawable.R;
import com.hencoder.a09_bitmap_drawable.Utils;

/**
 * @author ljuns
 * Created at 2018/10/29.
 */
public class CustomEditText extends android.support.v7.widget.AppCompatEditText {

    private static final int CONTENT_PADDING_TOP = (int) Utils.dpToPixel(25);
    private static final int TEXT_MARGIN_LEFT = (int) Utils.dpToPixel(5);
    private static final int TEXT_MARGIN_TOP = (int) Utils.dpToPixel(5);
    private static final int TEXT_SIZE = (int) Utils.dpToPixel(15);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ObjectAnimator mAnimator;

    private boolean mFloatingLabelShowed;
    private float mFloatingLabelAlpha = 0f;
    private int mFloatingLabelColor;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        mFloatingLabelColor =
            array.getColor(R.styleable.CustomEditText_floatingLabelColor, getResources().getColor(R.color.colorAccent));
        array.recycle();

        mPaint.setTextSize(TEXT_SIZE);
        mPaint.setColor(mFloatingLabelColor);
        setPadding(getPaddingLeft(), getPaddingTop() + CONTENT_PADDING_TOP, getPaddingRight(), getPaddingBottom());

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mFloatingLabelShowed && !TextUtils.isEmpty(s)) {
                    mFloatingLabelShowed = true;
                    getAnimator().start();
                } else if (mFloatingLabelShowed && TextUtils.isEmpty(s)) {
                    mFloatingLabelShowed = false;
                    getAnimator().reverse();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public float getFloatingLabelAlpha() {
        return mFloatingLabelAlpha;
    }

    public void setFloatingLabelAlpha(float floatingLabelAlpha) {
        mFloatingLabelAlpha = floatingLabelAlpha;
        invalidate();
    }

    public void setFloatingLabelColor(int floatingLabelColor) {
        mFloatingLabelColor = floatingLabelColor;
    }

    /**
     * 获取动画
     * @return
     */
    private ObjectAnimator getAnimator() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(CustomEditText.this, "floatingLabelAlpha", 0f, 1f)
                .setDuration(1000);
        }
        return mAnimator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAlpha((int) (0xff * mFloatingLabelAlpha));
        float offset = (TEXT_SIZE + TEXT_MARGIN_TOP) * (1 - mFloatingLabelAlpha);
        canvas.drawText(getHint().toString(), TEXT_MARGIN_LEFT, (TEXT_SIZE + TEXT_MARGIN_TOP) + offset, mPaint);
    }
}
