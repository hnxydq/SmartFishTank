package com.tfxiaozi.smartfishtank.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tfxiaozi.smartfishtank.R;

/**
 * Created by Administrator on 2016/09/12.
 */
public class LabelLayout extends LinearLayout{

    private View dividerView;
    private View indicatorView;
    private ImageView iconView;
    private TextView titleView;
    private TextView subtitleView;
    private LinearLayout layout;
    private float FULL_SCREEN_MIN_SCALE = 1.0f;

    public LabelLayout(Context context) {
        this(context, null);
    }

    public LabelLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LabelLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutParams layoutParams;
        setOrientation(LinearLayout.VERTICAL);
        setMinimumHeight(dp2px(60));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LabelLayout, 0, 0);
        Drawable iconDrawable = obtainStyledAttributes.getDrawable(R.styleable.LabelLayout_layout_icon);
        CharSequence title = obtainStyledAttributes.getString(R.styleable.LabelLayout_layout_title);
        CharSequence subTitle = obtainStyledAttributes.getString(R.styleable.LabelLayout_layout_subTitle);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        int paddingVal = dp2px(2);
        if (iconDrawable != null) {
            this.iconView = new ImageView(context);
            this.iconView.setImageDrawable(iconDrawable);
            this.iconView.setPadding(0, paddingVal, 0, paddingVal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = dp2px(20);
            linearLayout.addView(this.iconView, params);
        }
        this.layout = new LinearLayout(context);
        this.layout.setOrientation(LinearLayout.VERTICAL);
        this.titleView = new TextView(context);
        this.titleView.setText(title);
        this.titleView.setSingleLine();
        this.titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f);
        this.titleView.setTextColor(getResources().getColor(R.color.label_title_color));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = dp2px(8);
        this.layout.addView(this.titleView, params);
        if (subTitle != null) {
            this.subtitleView = new TextView(context);
            this.subtitleView.setText(subTitle);
            this.subtitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
            this.subtitleView.setTextColor(getResources().getColor(R.color.label_subtitle_color));
            this.layout.addView(this.subtitleView);
        }
        LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, FULL_SCREEN_MIN_SCALE);
        if (iconDrawable != null) {
            layoutParams2.leftMargin = paddingVal;
        }
        linearLayout.addView(this.layout, layoutParams2);

        this.indicatorView = new ImageView(context);
        ((ImageView) this.indicatorView).setImageResource(R.mipmap.ic_arrows_right);
        layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = paddingVal;
        layoutParams.rightMargin = dp2px(20);
        linearLayout.addView(this.indicatorView, layoutParams);

        layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, FULL_SCREEN_MIN_SCALE);
        layoutParams.topMargin = paddingVal;
        layoutParams.bottomMargin = paddingVal;
        addView(linearLayout, layoutParams);

        this.dividerView = new View(context);
        this.dividerView.setBackgroundColor(obtainStyledAttributes.getColor(R.styleable.LabelLayout_devider_line_color, getResources().getColor(R.color.line_color)));
        layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
        layoutParams.topMargin = dp2px(2);
        addView(this.dividerView, layoutParams);

        obtainStyledAttributes.recycle();
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }
}
