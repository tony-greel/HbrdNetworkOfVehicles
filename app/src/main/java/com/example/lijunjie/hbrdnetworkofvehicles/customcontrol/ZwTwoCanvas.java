package com.example.lijunjie.hbrdnetworkofvehicles.customcontrol;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.lijunjie.hbrdnetworkofvehicles.R;


/**
 * Created by zengwei on 2018/6/12.
 */

public class ZwTwoCanvas extends View {
    private Paint linePaint;   //画笔
    private float Mywidth;     //屏幕宽度
    private float Myheight;    //屏幕高度

    private float x=0;  //按钮移动X
    private float y=0;   //按钮移动Y
    private float x_down,y_down; // 按下x位置y位置

    private int huX;  //长按光圈角度
    private boolean islonghu=true;
    private int haloR=0;    //光弧阔收的半径
    private boolean ishalo=true;   //光弧停止控制器

    private boolean ispingfan=true;      //禁止频繁操作

    private int iAlpha=255;    //按钮透明度
    private int longAlpha=255;  //长按进度条透明度

    private ZwCanvasListener zwCanvasListener;    //监听事件

    private BitmapFactory.Options options;
    private Bitmap bitmap,bitmap1,bitmap2,bitmap3;

    public static final int[] SWEEP_GRADIENT_COLORS = new int[]{Color.parseColor("#25C6FC"),Color.parseColor("#E03636"), Color.parseColor("#E03636"), Color.parseColor("#E03636"),Color.parseColor("#25C6FC")};
    private SweepGradient mColorShader;

    private Handler handler;

    private Vibrator vib ;
    private RelativeLayout relativeLayout;
    private ObjectAnimator anim;

    private boolean isboot=false;
    private int butR=0;
    private boolean isbutR=true;
    private boolean ismove=false;
    public ZwTwoCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //画笔
        linePaint=new Paint();
        linePaint.setStyle(Paint.Style.STROKE );
        linePaint.setColor(Color.parseColor("#25C6FC"));
        linePaint.setStrokeWidth(30);
        //屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Mywidth= wm.getDefaultDisplay().getWidth();
        Myheight= wm.getDefaultDisplay().getHeight();

        options = new BitmapFactory.Options();
        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.start, options);  //可设置图片
        bitmap1= BitmapFactory.decodeResource(getResources(), R.mipmap.but_left, options);  //可设置图片
        bitmap2= BitmapFactory.decodeResource(getResources(), R.mipmap.but_right, options);  //可设置图片
        bitmap3=BitmapFactory.decodeResource(getResources(), R.mipmap.belt, options);  //可设置图片
        //渐变色
        mColorShader=new SweepGradient(Mywidth/2+x-bitmap.getWidth()/2,Myheight-Myheight/5+y-bitmap.getHeight()/2,SWEEP_GRADIENT_COLORS,null);
        //长按控制器
        handler=new Handler();
        vib= (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        linePaint.setShader(null);   //渐变没有
        linePaint.setAlpha(80);      //透明度
        linePaint.setStyle(Paint.Style.STROKE );
        canvas.drawCircle(Mywidth/2,Myheight-Myheight/5,haloR,linePaint);  //扩散圆弧
        if(huX>0) {
            linePaint.setShader(mColorShader);  //渐变颜色
            linePaint.setAlpha(longAlpha);   //透明度
            //位置
            linePaint.setStrokeWidth(80);
            RectF oval = new RectF(100,100, getMywidth()*2-100,getMywidth()*2-100);
//            RectF oval = new RectF(getMywidth()-bitmap3.getWidth()/2-100,getMyheights()-bitmap3.getHeight()/2-100, getMywidth()+bitmap3.getWidth()/2+100,getMyheights()+bitmap3.getHeight()/2+100);
            canvas.drawArc(oval,-90,huX,false,linePaint);   //角度与显示
        //    canvas.drawBitmap(bitmap3,getMywidth()-bitmap3.getWidth()/2,getMywidth()-bitmap3.getHeight()/2,linePaint);
        }
        linePaint.setStrokeWidth(30);
        linePaint.setAlpha(iAlpha);   //透明度
        if(ishalo){
            canvas.drawBitmap(bitmap1,100,Myheight-Myheight/5-bitmap.getHeight()/2,linePaint);
            canvas.drawBitmap(bitmap2,Mywidth/2*2-bitmap2.getWidth()-100,Myheight-Myheight/5-bitmap.getHeight()/2,linePaint);
        }
        canvas.drawBitmap(bitmap,Mywidth/2+x-bitmap.getWidth()/2,Myheight-Myheight/5+y-bitmap.getHeight()/2,linePaint);
        if(isboot){
            linePaint.setAlpha(50);   //透明度
            canvas.drawCircle(bitmap2.getWidth()-50,Myheight-Myheight/5+y,bitmap.getWidth()/2+butR,linePaint);  //扩散圆弧
            canvas.drawCircle(getMywidth()*2-bitmap2.getWidth()+50,Myheight-Myheight/5+y,bitmap.getWidth()/2+butR,linePaint);  //扩散圆弧
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果是按下
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            //判断按下的位置是否在按钮内
            if(event.getX()>(getMywidth()-80)&&
                    event.getX()<(getMywidth()+80)&&
                    event.getY()>(getMyheight()-80)&&
                    event.getY()<(getMyheight()+80)){
                //获取按下按钮的坐标点
                x_down=event.getX();
                y_down=event.getY();
                ishalo=true;
                islonghu=true;ismove=true;
                butHalo();
                handler.postDelayed(runnable,2000);
                bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.start1, options);  //设置按钮图片样子
                invalidate();
            }else{
                //如果没按上按钮，按钮无法移动
                x_down=0;
                y_down=0;
            }
            //移动事件
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            //按下的位置不能为0
            if (x_down!=0&&y_down!=0){
                if(huX==0){
                    haloR=0;ishalo=false;isboot=true;
                    if(isbutR){
                        isbutR=false;
                        lrHalo();
                    }
                    invalidate();
                    //左右移动的距离 大于上移的距离  那么认定为左右移动
                    if(Math.abs(x_down-event.getX())>y_down-event.getY()){
                        invalidate();
                        move(event.getX() - x_down, 0);
                    }
                }
            }
            //抬起复位
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            if(event.getX()<150){
                    if(ispingfan){
                        zwCanvasListener.left();
                    }
                    move(0-getMywidth(),0);

            }else if(event.getX()>(getMywidth()*2-150)){
                    if(ispingfan){
                        zwCanvasListener.right();
                    }
                    move(getMywidth(),0);
            } else{
                up();
            }
            isbutR=true;
            islonghu=false;
            ishalo=true;
            isboot=false;ismove=false;
            handler.removeCallbacks(runnable);
            bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.start, options);  //设置按钮图片样子
            invalidate();
        }
        return true;
    }

   Runnable runnable= new Runnable() {
        @Override
        public void run() {
            if(ishalo){
                relativeLayout.setBackgroundColor(Color.parseColor("#666666"));
                anim= ObjectAnimator.ofFloat(relativeLayout, "alpha", 0f, 0.8f).setDuration(1000);
                anim.start();
                ishalo=false;
                Arc();
            }
        }
    };

    public void lrHalo(){
        new Thread(() -> {
            boolean is=true;
            while (true){
                if(!isbutR){
                    if(is){
                        butR++;
                        if(butR>=20){
                            is=false;
                        }
                    }else{
                        butR--;
                        if(butR<=0){
                            is=true;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    break;
                }
            }
        }).start();
    }
    //光弧阔收
    public void butHalo(){
        new Thread(() -> {
            haloR=bitmap.getWidth()/2;
            boolean thishalos=true;
            while (ishalo){
                if(thishalos){
                    if(haloR>=bitmap.getWidth()/2+50){
                        //  haloR1=bitmap.getWidth()/2-10;
                        thishalos=false;
                    }else {
                        haloR++;
                    }
                }else{
                    if(haloR<=bitmap.getWidth()/2-10){
                        haloR=bitmap.getWidth()/2-10;
                        thishalos=true;
                    }else {
                        haloR--;
                    }
                }

                postInvalidate();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            haloR=0;
            postInvalidate();
        }).start();
    }

    //圆形进度条
    public void Arc(){
        new Thread(() -> {
            Looper.prepare();
           // int zzz=0;
            while (islonghu){
                huX+=2;

                postInvalidate();
                if(huX>=360){
                    vib.vibrate(300L);
                    zwCanvasListener.up();
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            anim= ObjectAnimator.ofFloat(relativeLayout, "alpha", 0.8f, 0f).setDuration(1000);
            anim.start();
                for(int is=0;is<51;is++){
                    longAlpha-=5;
                    postInvalidate();
                    Log.d("zesss","123123");
                    try {
                        Thread.sleep(30);  //每次执行暂停30毫秒    可设置
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            longAlpha=255;
            huX=0;
            Looper.loop();
        }).start();
    }
    /**
     *  按钮移动中所出发的事件
     */
    public void move(float xx, float yy){
        //移动的距离
        x=xx;y=yy;invalidate();
        //判断左右移动的距离 或者 向上移动的距离  是否为X Y 的获取距离
        if (Math.abs(xx)==getMywidth()) {
            //回归原点
            x=0;y=0;
            //判断是否执行过监听事件 true为没有
            if(ispingfan){
                iAlpha=0;   //画笔透明度为0
                linePaint.setAlpha(iAlpha); //设置透明赌
                //执行线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //为false说明执行了监听事件
                        ispingfan=false;
                        //透明度慢慢回归  按钮渐渐显示  这是一个按钮回归原点的过度动画
                        for(int is=0;is<51;is++){
                            iAlpha+=5;
                            postInvalidate();
                            try {
                                Thread.sleep(30);  //每次执行暂停30毫秒    可设置
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //执行完毕后设置监听事件为可以执行
                        ispingfan=true;
                    }
                }).start();
            }else{
                //可以进行一个提示  提醒用户不用频繁操作
            }
        }
    }

    /**
     *  手指抬起复位按钮
     */
    public void up(){
        x = 0;
        y = 0;
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(ismove){
            getParent().requestDisallowInterceptTouchEvent(true);
        }else{
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(event);
    }
    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    //X中心位置
    public float getMywidth() {
        return Mywidth/2;
    }
    //Y轴位置
    public float getMyheight() {
        return Myheight-Myheight/5;
    }

    public float getMyheights() {
        return Myheight/2;
    }

    public void setZwCanvasListener(ZwCanvasListener zwCanvasListener) {
        this.zwCanvasListener = zwCanvasListener;
    }
}
