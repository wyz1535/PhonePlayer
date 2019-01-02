package com.leyifu.phoneplayer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyifu.phoneplayer.R;


/**
 * Created by hahaha on 2018/12/26 0026.
 */

public class ButtonLoading extends RelativeLayout {


    private static final int DEFUALT_COLOR = android.R.color.white;
    private TextView mText;
    private ProgressBar mProgress;
    private String textName;
    private int textColor;
    private int progressColor;
    private int textSize;
    private int mDefaultTextSize;
    private String textLoading;
    private boolean isLoading;

    public ButtonLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mDefaultTextSize = getResources().getDimensionPixelSize(R.dimen.text_default_size);
        isLoading = false;

        View view = LayoutInflater.from(context).inflate(R.layout.view_loading_button, this, true);
//        view.setBackgroundColor(Color.BLUE);
        mText = ((TextView) view.findViewById(R.id.text));
        mProgress = ((ProgressBar) view.findViewById(R.id.progress));

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.button_loading);

            textName = typedArray.getString(R.styleable.button_loading_textName);
            setTextName(textName);

            textLoading = typedArray.getString(R.styleable.button_loading_textLoading);
//            setTextName(textLoading);

            int textColor = typedArray.getColor(R.styleable.button_loading_textColor, Color.RED);
            setTextColor(textColor);

            int progressColor = typedArray.getColor(R.styleable.button_loading_progressColor, Color.RED);
            setProgressColor(progressColor);

            float textSize = typedArray.getDimensionPixelSize(R.styleable.button_loading_textSize, mDefaultTextSize);
            setTextSize(textSize);
        }

    }


    public void setTextName(String textName) {
        mText.setText(textName);

    }

    public void setTextColor(int textColor) {
        mText.setTextColor(textColor);
    }

    public void setProgressColor(int progressColor) {
        mProgress.getIndeterminateDrawable().mutate().setColorFilter(progressColor, PorterDuff.Mode.SRC_ATOP);
    }

    public void setTextSize(float textSize) {
        mText.setTextSize(textSize);
    }

    public void showLoading() {
        if (!isLoading) {
            mProgress.setVisibility(VISIBLE);
            setTextName(textLoading);
            isLoading = true;
            setEnabled(false);
        }

    }

    public void showButtonText() {
        if (isLoading) {
            mProgress.setVisibility(INVISIBLE);
            setTextName(textName);
            isLoading = false;
            setEnabled(true);
        }

    }
}
