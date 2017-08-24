package zhangxuelei1506d.wangyi;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ContentView;

import java.util.zip.Inflater;

import cn.sharesdk.onekeyshare.OnekeyShare;

import static android.R.attr.width;
import static android.R.attr.x;
import static android.R.attr.y;

/**
 * date 2017/8/15
 * author:张学雷(Administrator)
 * functinn:
 */

public class myButton extends View{
    

    private int x;
    private int y;
    int dwonx;
    int dwony;

    private int mradius = 50;
    private boolean isonBall;
    public myButton(Context context) {
      this(context,null);
    }

    public myButton(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public myButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    //进行测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取当前控件的宽和高
       int  height = this.getHeight();
       int  width = this.getWidth();
        //获取屏幕的正中心点
        int x = width / 2;
        int y = height / 2;


    }

    //此方法就是进行绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        //画圆。根据他需要什么参数（ctr+Q），我就添加什么参数
        canvas.drawCircle(x, y, mradius, paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取手按下时的坐标
                dwonx = (int) event.getX();
                dwony = (int) event.getY();
                isonBall = isonBall(dwonx, dwony);

                if(isonBall){
                    return true;

                }

                break;
            case MotionEvent.ACTION_MOVE:

                if (isonBall) {
                    // 获取手移动后的坐标
                    x= (int) event.getX();
                    y = (int) event.getY();

                    postInvalidate();

                }


                break;
            case MotionEvent.ACTION_UP:

                if(isonBall){

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View convertView = inflater.inflate(R.layout.itempopuwindow, null);
                    PopupWindow popupWindow = new PopupWindow(convertView,800,200);
                    LinearLayout fenxiang= (LinearLayout) convertView.findViewById(R.id.image_fenxiang);
                    ImageView imageView= (ImageView) convertView.findViewById(R.id.image_dongtaitu);
                    ImageView imageView2= (ImageView) convertView.findViewById(R.id.image_dongtaitu2);
                    Glide.with(getContext()).load(R.drawable.loading).into(imageView);
                    Glide.with(getContext()).load(R.drawable.loading).into(imageView2);
                    if (mOnLetterUpdateListener!=null){
                        mOnLetterUpdateListener.onLetterUpdate(fenxiang);
                    }
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.showAsDropDown(new View(getContext()),-1000,500, Gravity.CENTER);
                }

                break;


        }

        return super.onTouchEvent(event);


    }
    private boolean isonBall(int dwonx, int dwony) {
        //勾股定理得到按下的半径
        double sqrt = Math.sqrt((dwonx - x) * (dwonx - x) + (dwony - y) * (dwony - y));
        //对应远的半径和按下的半径进行判断，看用户的手是否点在圆上
        if (sqrt <= mradius) {
            return true;

        }
        return false;
    }



    //c.定义接口
    public interface OnLetterUpdateListener {


        void onLetterUpdate(LinearLayout fenxiang);
    }
    //定义接口对象
    private OnLetterUpdateListener mOnLetterUpdateListener;

    //暴露方法,让外界传过来一个实现接口的类对象
    public void setmOnLetterUpdateListener(OnLetterUpdateListener onLetterUpdateListener) {
        mOnLetterUpdateListener = onLetterUpdateListener;

    }




}



