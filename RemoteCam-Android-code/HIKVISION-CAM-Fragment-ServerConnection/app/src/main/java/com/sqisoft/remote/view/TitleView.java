package com.sqisoft.remote.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by SQISOFT on 2016-10-12.
 */
public class TitleView extends TextView {

    private boolean stroke = false;
    private float strokeWidth = 0.0f;
    private int strokeColor;

    public TitleView(Context context) {
        super(context);
        initView();
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);
    }
}
