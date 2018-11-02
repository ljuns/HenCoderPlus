package com.hencoder.a10_layout.ljuns;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author ljuns
 * Created at 2018/11/2.
 */
public class SquareImage extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "SquareImage";

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 这行可省略，前提是需要调用 setMeasuredDimension(size, size);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 在父 view 的 onMeasure 中根据 xml 和自己的可用空间得出 view 的具体尺寸要求

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = Math.min(width, height);

        setMeasuredDimension(size, size);
    }
}