package zhangxuelei1506d.wangyi;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qzone.QZone;

import static android.R.attr.button;

/**
 * date 2017/8/9
 * author:张学雷(Administrator)
 * functinn:
 */

public class Content extends Activity {

    private String path;
    private String title;
    private String image;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        webView = (WebView) findViewById(R.id.webview_webview);


        myButton button = (myButton) findViewById(R.id.share_SSSSSSSSSS);


        path = getIntent().getStringExtra("path");
        title = getIntent().getStringExtra("title");
        image = getIntent().getStringExtra("image");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(path);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        button.setmOnLetterUpdateListener(new myButton.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(LinearLayout fenxiang) {
                fenxiang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //    private void showShare() {
                        OnekeyShare oks = new OnekeyShare();
                        //关闭sso授权
                        oks.disableSSOWhenAuthorize();

                        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
                        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                        oks.setTitle(path);
                        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                        oks.setTitleUrl(path);
                        // text是分享文本，所有平台都需要这个字段
                        oks.setText(title);
                        // imagePath是图片的本地路径，Linked-In以外的平台都
                        oks.setImageUrl(image);
                        // url仅在微信（包括好友和朋友圈）中使用
                        oks.setUrl("http://sharesdk.cn");
                        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                        oks.setComment("我是测试评论文本");
                        // site是分享此内容的网站名称，仅在QQ空间使用
                        oks.setSite(getString(R.string.app_name));
                        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                        oks.setSiteUrl(path);

                        // 启动分享GUI
                        oks.show(Content.this);


                    }

                });
            }
        });


    }
    // 设置回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
