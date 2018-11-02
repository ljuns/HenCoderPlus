package com.hencoder.a10_layout.ljuns;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.hencoder.a10_layout.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljuns
 * Created at 2018/11/2.
 */
public class TagLayout extends ViewGroup {

    private static final int PADDING = (int) Utils.dpToPixel(5);

    // 存储子 view 的具体布局（位置、尺寸）
    private List<Rect> mChildrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthUsed = 0;
        int heightUsed = 0;
        int lineMaxHeight = 0;
        int lineWidthUsed = 0;

        // 父 view 被测量的宽度
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentMode = MeasureSpec.getMode(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            //int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
            //int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
            //int childWidthMode = 0;
            //int childWidthSize = 0;
            //LayoutParams params = child.getLayoutParams();
            //switch (params.width) {
            //    // 开发者对子 view 的要求（在 xml 中的要求）
            //    case LayoutParams.MATCH_PARENT:
            //        switch (specWidthMode) {
            //            // 父 view 自己的要求
            //            case MeasureSpec.EXACTLY:
            //            case MeasureSpec.AT_MOST:
            //                childWidthSize = specWidthSize;
            //                childWidthMode = MeasureSpec.EXACTLY;
            //                break;
            //            case MeasureSpec.UNSPECIFIED:
            //                childWidthMode = MeasureSpec.UNSPECIFIED;
            //                childWidthSize = 0;
            //                break;
            //
            //        }
            //        break;
            //
            //}

            measureChildWithMargins(child, widthMeasureSpec, 0 + PADDING, heightMeasureSpec, heightUsed + PADDING);
            // 如果已经用过的宽度和此时子 view 的宽度大于父 view 的宽度就换行
            if (parentMode != MeasureSpec.UNSPECIFIED &&
                lineWidthUsed + child.getMeasuredWidth() + PADDING * 2 > parentWidth) {
                heightUsed = heightUsed + lineMaxHeight + PADDING;
                lineMaxHeight = 0;
                lineWidthUsed = 0;
                measureChildWithMargins(child, widthMeasureSpec, 0 + PADDING, heightMeasureSpec, heightUsed + PADDING);
            }

            // 缓存子 view 的尺寸信息
            Rect childBounds = null;
            if (mChildrenBounds.size() <= i) {
                childBounds = new Rect();
                mChildrenBounds.add(childBounds);
            } else {
                childBounds = mChildrenBounds.get(i);
            }
            childBounds.set(lineWidthUsed + PADDING, heightUsed + PADDING,
                            lineWidthUsed + child.getMeasuredWidth() + PADDING,
                            heightUsed + child.getMeasuredHeight() + PADDING);

            lineWidthUsed = lineWidthUsed + child.getMeasuredWidth() + PADDING;
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
            // 每一次测量完都比较下宽度，取较大的那个
            widthUsed = Math.max(widthUsed, lineWidthUsed);
        }

        int width = widthUsed;
        int height = heightUsed + lineMaxHeight + PADDING;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBounds = mChildrenBounds.get(i);
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
