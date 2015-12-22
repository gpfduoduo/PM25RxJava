package com.guo.duoduo.pm25rxjava.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by 郭攀峰 on 2015/12/22.
 */
public class CustomGaugeView extends View
{
    private static final String tag = CustomGaugeView.class.getSimpleName();
    private static final int DEFAULT_MIN_WIDTH = 400;
    private static final int FONT_SIZE = 50;
    private float mCurValue = 0f;
    private Paint mPaintWhite;
    private Paint mPaintColor;
    private Paint mPaintText;
    private int width, height;
    private float mPaintWidth;
    private RectF mRect;
    //圆环颜色
    private int[] mColors = new int[]{Color.GREEN, Color.YELLOW, Color.RED};

    public CustomGaugeView(Context context)
    {
        super(context);
        init();
    }

    public CustomGaugeView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomGaugeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        mPaintWhite = new Paint();
        mPaintWhite.setAntiAlias(true);
        mPaintWhite.setStyle(Paint.Style.STROKE);
        mPaintWhite.setColor(Color.WHITE);

        mPaintText = new Paint();
        mPaintText.setColor(Color.RED);
        mPaintText.setTextSize(FONT_SIZE);
        mPaintText.setTextAlign(Paint.Align.CENTER);

        mPaintColor = new Paint();
    }

    public void setValue(float value)
    {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mCurValue, value);
        valueAnimator.setDuration(1 * 1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                mCurValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    protected void onDraw(Canvas canvas)
    {
        canvas.rotate(-90, width / 2, height / 2);
        initParams();
        canvas.drawArc(mRect, 0, 360, false, mPaintWhite);
        canvas.drawArc(mRect, 0, mCurValue, false, mPaintColor);

        canvas.rotate(90, width / 2, height / 2);
        float baseLine = height / 2
            - (mPaintText.getFontMetrics().descent + mPaintText.getFontMetrics().ascent)
            / 2;
        canvas.drawText(Integer.toString((int) mCurValue), width / 2, baseLine,
            mPaintText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int resultWidth = 0;
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (modeWidth == MeasureSpec.EXACTLY)
            resultWidth = sizeWidth;
        else
        {
            resultWidth = DEFAULT_MIN_WIDTH;
            if (modeWidth == MeasureSpec.AT_MOST)
                resultWidth = Math.min(resultWidth, sizeWidth);
        }
        int resultHeight = resultWidth;
        setMeasuredDimension(resultWidth, resultHeight);
    }

    private void initParams()
    {
        if (width == 0)
            width = getWidth();
        if (height == 0)
            height = getHeight();
        if (mPaintWidth == 0)
        {
            mPaintWidth = Math.min(width, height) / 2 * 0.15f;
            mRect = new RectF((width > height ? Math.abs(width - height) / 2 : 0)
                + mPaintWidth / 2, (height > width ? Math.abs(height - width) / 2 : 0)
                + mPaintWidth / 2, width
                - (width > height ? Math.abs(width - height) / 2 : 0) - mPaintWidth / 2,
                height - (height > width ? Math.abs(height - width) / 2 : 0)
                    - mPaintWidth / 2);
            mPaintWhite.setStrokeWidth(mPaintWidth);

            mPaintColor.setAntiAlias(true);
            mPaintColor.setStrokeWidth(mPaintWidth);
            mPaintColor.setStyle(Paint.Style.STROKE);
            mPaintColor
                    .setShader(new SweepGradient(width / 2, height / 2, mColors, null));
        }
    }
}
