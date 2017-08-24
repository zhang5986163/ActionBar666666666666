package zhangxuelei1506d.wangyi;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * date 2017/8/16
 * author:张学雷(Administrator)
 * functinn:继承式控件
 * 1.继承listview，复写构造方法
 * 2.覆写overScrollBy方法，重点关注deltay，isTouchEvent方法
 * 3.暴漏一个方法，去得到外界的ImageView，并测量ImageView控件的高度
 * 4.覆写OnTouchEvent方法
 */

public class ParallaxListView extends ListView {


    private ImageView iv_head;
    private int drawable;
    private int orignalHeight;

    public ParallaxListView(Context context) {
        this(context, null);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIv_head(ImageView iv_head) {

        this.iv_head = iv_head;
        //B.获取图片的原始高度
        drawable = iv_head.getDrawable().getIntrinsicHeight();
        //C.获取imageview控件的原始高度，以便回弹时，回弹到原始的高度
        orignalHeight = iv_head.getHeight();

    }

    /**
     * A,滑动到位listview两端的时候才会被调用
     *
     * @param deltaX
     * @param deltaY:竖直方向，滑动的瞬间变化量，顶部下拉为—；顶部上拉为+ $$$$$$$$
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY
     * @param isTouchEvent                       是否是用户触摸拉动，true表示用户手指拉动，false是惯性 $$$$$$$$$
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY
            , int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

       /* //A,通过LOG来验证参数的作用
        System.out.println("deltaX"+deltaX+"");
        System.out.println("deltaY"+deltaY+"");
        System.out.println("scrollX"+scrollX+"");
        System.out.println("scrollY"+scrollY+"");
        System.out.println("scrollRangeX"+scrollRangeX+"");
        System.out.println("scrollRangeY"+scrollRangeY+"");
        System.out.println("maxOverScrollX"+maxOverScrollX+"");
        System.out.println("maxOverScrollY"+maxOverScrollY+"");
        System.out.println("isTouchEvent"+isTouchEvent+"");*/
        //A.顶部下拉，用户触摸的操作才执行视差效果
        if (deltaY < 0 && isTouchEvent) {
            //A.deltaY是负值，我们要改为绝对值，累加给我们的iv_headle高度
            int newHeight = iv_head.getHeight() + Math.abs(deltaY);
            //B.避免图片的无限放大，使图片最大不能够超过图片本身的高度
            //把新的高度值赋值给控件，改变控件的高度
            if (newHeight <= drawable) {
                iv_head.getLayoutParams().height = newHeight;
                iv_head.requestLayout();
            }
        }


        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    //:c.覆写触摸事件，让滑动图片重新恢复原有的样子
    //触摸事件的监听
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                //把当前的头布局的高度恢复初始高度
                int currentHeight = iv_head.getHeight();
                //属性动画,改变高度的值，把我们当前头布局的高度，改为原始时的高度
                final ValueAnimator animator = ValueAnimator.ofInt(currentHeight, orignalHeight);

                //动画更新的监听
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //获取动画执行过程中的分度值
                        float fraction = animator.getAnimatedFraction();
                        //获取中间的值，并赋给控件的新高度，可以使控件平稳回弹的效果
                        Integer animatedValue = (Integer) animator.getAnimatedValue();
                        //让新的高度值去生效
                        iv_head.getLayoutParams().height = animatedValue;
                        iv_head.requestLayout();

                    }
                });

                //动画的回弹效果，值越大，回弹越厉害
                animator.setInterpolator(new OvershootInterpolator(2));
                //设置动画的执行时间
                animator.setDuration(500);
                //动画执行
                animator.start();

                break;


        }

        return super.onTouchEvent(ev);

    }
}
