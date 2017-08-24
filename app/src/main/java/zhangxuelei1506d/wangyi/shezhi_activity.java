package zhangxuelei1506d.wangyi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

import static zhangxuelei1506d.wangyi.R.drawable.shezhi;

/**
 * date 2017/8/17
 * author:张学雷(Administrator)
 * functinn:
 */

public class shezhi_activity extends Activity {

    private ListView mShezhiListview;
    private ImageView mImageZuoDongtaitu;
    private ImageView mImageYouDongtaitu;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);
        initView();
        Glide.with(shezhi_activity.this).load(R.drawable.keyboard).into(mImageZuoDongtaitu);
        Glide.with(shezhi_activity.this).load(R.drawable.keyboard).into(mImageYouDongtaitu);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Toast.makeText(shezhi_activity.this,"本宝宝QQ:630840595",Toast.LENGTH_LONG).show();
            }
        });

        List<String> list = new ArrayList<>();
        list.add("分享软件");
        list.add("清除缓存");
        list.add("意见反馈");
        list.add("关于我们");


        shezhiAdapter adapter = new shezhiAdapter(list, this);
        mShezhiListview.setAdapter(adapter);
        mShezhiListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:

                        OnekeyShare oks = new OnekeyShare();
                        //关闭sso授权
                        oks.disableSSOWhenAuthorize();

                        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
                        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                        oks.setTitle("本宝宝做的网易新闻");
                        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                        oks.setTitleUrl("https://user.qzone.qq.com/630840595/infocenter");
                        // text是分享文本，所有平台都需要这个字段
                        oks.setText("你是我的小丫么小苹果，怎么爱你都不嫌多");
                        // imagePath是图片的本地路径，Linked-In以外的平台都
                        oks.setImageUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1502953062&di=d431436e0f7e12663f25828901d27031&src=http://image.tianjimedia.com/uploadImages/2015/285/24/586K2UOWHG9D.jpg");
                        // url仅在微信（包括好友和朋友圈）中使用
                        oks.setUrl("http://sharesdk.cn");
                        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                        oks.setComment("我是测试评论文本");
                        // site是分享此内容的网站名称，仅在QQ空间使用
                        oks.setSite(getString(R.string.app_name));
                        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                        oks.setSiteUrl("全天下我最帅");

                        // 启动分享GUI
                        oks.show(shezhi_activity.this);


                        break;
                    case 1:


                         Toast.makeText(shezhi_activity.this,"清除缓存成功",Toast.LENGTH_SHORT).show();
                        CleanMessageUtil.clearAllCache(getApplicationContext());

                        break;
                    case 2:

                        startActivity(new Intent(shezhi_activity.this,yijianfankui.class));

                        break;
                    case 3:

                        startActivity(new Intent(shezhi_activity.this,guanyuwomen.class));
                        break;


                }




            }
        });



    }

    private void initView() {
        mShezhiListview = (ListView) findViewById(R.id.shezhi_listview);
        mImageZuoDongtaitu = (ImageView) findViewById(R.id.image_zuo_dongtaitu);
        mImageYouDongtaitu = (ImageView) findViewById(R.id.image_you_dongtaitu);
        button = (Button) findViewById(R.id.lianxiwomenoqin);
    }
}
