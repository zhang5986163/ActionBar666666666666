package zhangxuelei1506d.wangyi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * date 2017/8/17
 * author:张学雷(Administrator)
 * functinn:
 */

public class yijianfankui extends Activity {
    private ImageView mYijianfankui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yijianfankui);
        initView();


        Glide.with(yijianfankui.this).load(R.drawable.timg).into(mYijianfankui);



    }

    private void initView() {
        mYijianfankui = (ImageView) findViewById(R.id.yijianfankui);
    }
}
