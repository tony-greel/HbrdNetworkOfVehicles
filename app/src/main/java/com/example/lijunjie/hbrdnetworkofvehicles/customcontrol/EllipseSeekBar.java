package com.example.lijunjie.hbrdnetworkofvehicles.customcontrol;

/**
 * 作者：王海洋
 * 时间：2017/2/28 16:37
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.lijunjie.hbrdnetworkofvehicles.R;


 public class EllipseSeekBar extends View {

    private final boolean DEBUG = true;
    private final String TAG = "CircleSeekBar";

    private Context mContext = null;
    private AttributeSet mAttrs = null;

    private Drawable mThumbDrawable = null;
    private int mThumbHeight = 0;
    private int mThumbWidth = 0;
    private int[] mThumbNormal = null;
    private int[] mThumbPressed = null;

    private int mSeekBarMax = 0;
    private Paint mSeekBarBackgroundPaint = null;
    private Paint mSeekbarProgressPaint = null;
    private Paint mWithInPaint = null;
    private Paint mWithOutPaint = null;
    private RectF mArcRectF = null;
    private RectF mWithInArcRectF = null;
    private RectF mWithOutArcRectF = null;
    private Paint mPaintCircleLeft = null;
    private Paint mPaintCircleRiht = null;

    private boolean mIsShowProgressText = false;
    private Paint mProgressTextPaint = null;
    private int mProgressTextSize = 0;

    private int mViewHeight = 0;
    private int mViewWidth = 0;
    private int mSeekBarSize = 0;
    private int mSeekBarRadius = 0;
    private int mSeekBarCenterX = 0;
    private int mSeekBarCenterY = 0;
    private float mThumbLeft = 0;
    private float mThumbTop = 0;

    private float mSeekBarDegree = 1.0f;
    private int mCurrentProgress = 0;
    private double temporaryRadian = -1;
    private int minRadian = 180;
    private int swipRadian = 180;
    private double progreeRadian = 0;
    private boolean isFirst = true;
    private float bottomProgressWidth;

    int bottomCx1 = 0;
    int bottomCy1 = 0;
    int bottomArcRadius = 0;

    public EllipseSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mAttrs = attrs;
        initView();
    }

    public EllipseSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        initView();
    }

    public EllipseSeekBar(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView(){
        if(DEBUG) Log.d(TAG, "initView");
        TypedArray localTypedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.EllipseSeekBar);

        //thumb的属性是使用android:thumb属性进行设置的
        //返回的Drawable为一个StateListDrawable类型，即可以实现选中效果的drawable list
        //mThumbNormal和mThumbPressed则是用于设置不同状态的效果，当点击thumb时设置mThumbPressed，否则设置mThumbNormal

        mThumbDrawable = localTypedArray.getDrawable(R.styleable.EllipseSeekBar_android_thumb);
        mThumbWidth = this.mThumbDrawable.getIntrinsicWidth();
        mThumbHeight = this.mThumbDrawable.getIntrinsicHeight();

        mThumbNormal = new int[]{-android.R.attr.state_focused, -android.R.attr.state_pressed,
                -android.R.attr.state_selected, -android.R.attr.state_checked};
        mThumbPressed = new int[]{android.R.attr.state_focused, android.R.attr.state_pressed,
                android.R.attr.state_selected, android.R.attr.state_checked};

        float progressWidth = localTypedArray.getDimension(R.styleable.EllipseSeekBar_progress_width, 5);
        int progressBackgroundColor = localTypedArray.getColor(R.styleable.EllipseSeekBar_progress_background, Color.GRAY);
        int progressFrontColor = localTypedArray.getColor(R.styleable.EllipseSeekBar_progress_front, Color.BLUE);
        mSeekBarMax = localTypedArray.getInteger(R.styleable.EllipseSeekBar_progress_max, 100);

        mSeekbarProgressPaint = new Paint();
        mSeekBarBackgroundPaint = new Paint();
        mWithInPaint = new Paint();
        mWithOutPaint = new Paint();

        mSeekbarProgressPaint.setColor(progressFrontColor);
        mSeekBarBackgroundPaint.setColor(progressBackgroundColor);
        mWithInPaint.setColor(progressFrontColor);
        mWithOutPaint.setColor(progressFrontColor);

        mSeekbarProgressPaint.setAntiAlias(true);
        mSeekBarBackgroundPaint.setAntiAlias(true);
        mWithInPaint.setAntiAlias(true);
        mWithOutPaint.setAntiAlias(true);

        mSeekbarProgressPaint.setStyle(Paint.Style.STROKE);
        mSeekBarBackgroundPaint.setStyle(Paint.Style.STROKE);
        mWithInPaint.setStyle(Paint.Style.STROKE);
        mWithOutPaint.setStyle(Paint.Style.STROKE);

        mSeekBarBackgroundPaint.setStrokeWidth(80);
        mSeekbarProgressPaint.setStrokeWidth(80);
        mWithInPaint.setStrokeWidth(5);
        mWithOutPaint.setStrokeWidth(5);
        bottomProgressWidth = progressWidth;

        mArcRectF = new RectF();
        mWithInArcRectF = new RectF();
        mWithOutArcRectF = new RectF();

        mIsShowProgressText = localTypedArray.getBoolean(R.styleable.EllipseSeekBar_show_progress_text, false);
        int progressTextStroke = (int) localTypedArray.getDimension(R.styleable.EllipseSeekBar_progress_text_stroke_width, 5);
        int progressTextColor = localTypedArray.getColor(R.styleable.EllipseSeekBar_progress_text_color, Color.alpha(R.color.dark_grey));
        mProgressTextSize = (int) localTypedArray.getDimension(R.styleable.EllipseSeekBar_progress_text_size, 50);
        mProgressTextPaint = new Paint();
        mProgressTextPaint.setColor(progressTextColor);
        mProgressTextPaint.setAntiAlias(true);
        mProgressTextPaint.setStrokeWidth(progressTextStroke);
        mProgressTextPaint.setTextSize(mProgressTextSize);

        // ProgressBar结尾和开始画2个圆，实现ProgressBar的圆角。

        mPaintCircleLeft = new Paint();
        mPaintCircleLeft.setAntiAlias(true);
        mPaintCircleLeft.setColor(progressFrontColor);

        mPaintCircleRiht = new Paint();
        mPaintCircleRiht.setAntiAlias(true);
        mPaintCircleRiht.setColor(progressBackgroundColor);
        temporaryRadian = Math.toRadians(minRadian);

        localTypedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(DEBUG) Log.d(TAG, "onMeasure");
        mViewWidth = getWidth();
        mViewHeight = getHeight();

        mSeekBarSize = mViewWidth > mViewHeight ? mViewHeight : mViewWidth;

        mSeekBarCenterX = mViewWidth / 2;
        mSeekBarCenterY = mViewHeight ;

        mSeekBarRadius = mViewWidth/2;

        int left = 0;
        int right = mViewWidth;
        int top = mSeekBarCenterY - mSeekBarRadius;
        int bottom = mSeekBarCenterY + mSeekBarRadius;
        Log.d("LJJ", String.valueOf(top));
        Log.d("WSS", String.valueOf(bottom));
        mArcRectF.set(left + 70, top + 70, right - 70, bottom - 70);
        mWithInArcRectF.set(left + 140, top + 140, right - 140, bottom - 140);
        mWithOutArcRectF.set(left-10,top-10,right+10,bottom+10);
        // 计算弧形的圆心和半径。
        bottomCx1 = (left + right) / 2;
        bottomCy1 = (top + bottom) / 2;
        bottomArcRadius = (right - left) / 2;


        // 起始位置，左下角
        setThumbPosition(Math.toRadians(minRadian+5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(this.mArcRectF, minRadian, swipRadian, false,
                mSeekBarBackgroundPaint);
        canvas.drawArc(this.mArcRectF, minRadian, mSeekBarDegree, false, mSeekbarProgressPaint);
        canvas.drawArc(this.mWithInArcRectF, minRadian, swipRadian, false, mWithInPaint);
        canvas.drawArc(this.mWithOutArcRectF,minRadian,swipRadian,false,mWithOutPaint);
        drawThumbBitmap(canvas);
        drawProgressText(canvas);

        super.onDraw(canvas);
    }

    private void drawThumbBitmap(Canvas canvas) {
        this.mThumbDrawable.setBounds((int) mThumbLeft, (int) mThumbTop,
                (int) (mThumbLeft + mThumbWidth), (int) (mThumbTop + mThumbHeight));
        this.mThumbDrawable.draw(canvas);
    }

    //设置进度条显示数字
    private void drawProgressText(Canvas canvas) {
        if (true == mIsShowProgressText){
            //
            int temp = 14*mCurrentProgress/100+17;
            float textWidth = mProgressTextPaint.measureText("" +temp);
            canvas.drawText("" + temp,
                    mSeekBarCenterX - textWidth / 2,
                    mSeekBarCenterY - mSeekBarCenterX / 3
                    + mProgressTextSize / 2, mProgressTextPaint);

            listener.move(temp-17);
        }
    }

    private SlideListener listener;
    public void setListener(SlideListener listener) {
        this.listener = listener;
    }

    public interface SlideListener{
        void move(int degree);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                seekTo(eventX, eventY, false);
                break ;

            case MotionEvent.ACTION_MOVE:
                seekTo(eventX, eventY, false);
                break ;

            case MotionEvent.ACTION_UP:
                seekTo(eventX, eventY, true);
                break ;
        }
        invalidate();//刷新图层
        return true;
    }

    private void seekTo(float eventX, float eventY, boolean isUp) {
        if (true == isPointOnThumb(eventX, eventY) && false == isUp) {

            mThumbDrawable.setState(mThumbPressed);
            double radian = Math.atan2(eventY - mSeekBarCenterY, eventX - mSeekBarCenterX);

            if (radian < 0){
                radian = radian + 2* Math.PI;
            }

            double max = Math.toRadians(minRadian);
            int temp = 0;
            if(swipRadian - (360 - minRadian) > 0){
                temp = swipRadian - (360 - minRadian);
            }
            double min = Math.toRadians(temp);

            if(Math.abs(temporaryRadian - radian) > 1 && Math.abs(temporaryRadian - radian) < 6 ){
                Log.e("why", "111111");
                Log.e("why", Math.abs(temporaryRadian - radian) + "!!!!!");
                return;
            }

            Log.e("why", Math.abs(temporaryRadian - radian) + "!!!!!");
            isFirst = false;
            if(min<radian && radian < max){
                radian = temporaryRadian;
            }else {
                temporaryRadian = radian;
            }


            double tempProgreeRadian = radian;
            if(radian == 0){
                tempProgreeRadian = Math.toRadians(360);
            }
            if(0 < radian && radian < 0.9){
                tempProgreeRadian = Math.toRadians(360) + radian;
            }
            setThumbPosition(radian);

            progreeRadian = tempProgreeRadian - Math.toRadians(minRadian);
            if(DEBUG) Log.e(TAG, "seekTo radian = " + progreeRadian + "T" + Math.toRadians(360) + "T" + Math.toRadians(0) + "T" + Math.toRadians(minRadian));
            mSeekBarDegree = (float) Math.round(Math.toDegrees(progreeRadian));
            //因一些机型等于0会画成一个圈
            if(mSeekBarDegree == 0 || mSeekBarDegree < 0){
                mSeekBarDegree = 1.0f;
            }
            mCurrentProgress = (int) (mSeekBarMax * mSeekBarDegree / swipRadian);
            if(DEBUG) Log.e(TAG, "mCurrentProgress = " + mCurrentProgress);
            Log.e("why", "mSeekBarDegree:" + mSeekBarDegree);
            invalidate();
        }else{
            mThumbDrawable.setState(mThumbNormal);
            invalidate();
        }
    }

     @Override
     public boolean dispatchTouchEvent(MotionEvent event) {
         if(isPointOnThumb(event.getX(),event.getY())){
             getParent().requestDisallowInterceptTouchEvent(true);
         }else{
             getParent().requestDisallowInterceptTouchEvent(false);
         }
         return super.dispatchTouchEvent(event);
     }

    private boolean isPointOnThumb(float eventX, float eventY) {
        boolean result = false;
        double distance = Math.sqrt(Math.pow(eventX - mSeekBarCenterX, 2)
                + Math.pow(eventY - mSeekBarCenterY, 2));
        if (distance < mSeekBarRadius +20&& distance > mSeekBarRadius-70){
            result = true;
        }
        return result;
    }

    private void setThumbPosition(double radian) {
        if(DEBUG) Log.v(TAG, "setThumbPosition radian = " + radian);
        int radius = mSeekBarRadius-60;

        double x = mSeekBarCenterX + radius * Math.cos(radian);
        double y = mSeekBarCenterY + radius * Math.sin(radian);
        mThumbLeft = (float) (x - mThumbWidth / 2);
        mThumbTop = (float) (y - mThumbHeight / 2);
    }
}