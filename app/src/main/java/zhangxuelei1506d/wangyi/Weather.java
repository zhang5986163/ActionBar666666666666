package zhangxuelei1506d.wangyi;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * date 2017/8/14
 * author:张学雷(Administrator)
 * functinn:
 */

public class Weather extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        new Thread(){
            private String feng;
            private String hight;
            private String wendu1;
            private String tianqi1;
            private String day;
            @Override
            public void run() {

                try {
                    //从一个URL加载一个Document对象。
                    Document doc = Jsoup.connect("http://www.weather.com.cn/weather/101010100.shtml").get();
                    //选择天气节点
                    Elements elements1 = doc.select("div.c7d");
                    Elements elements2 = doc.select("div.c7d");
                    Elements elements3 = doc.select("div.c7d");
                    Elements elements4 = doc.select("div.c7d");
                    Elements elements5 = doc.select("div.c7d");
                    //打印 <a>标签里面的title
                    day = elements1.select("h1").get(0).text();//获取的是日期
                    tianqi1 = elements2.select("p").attr("title");//获取的天气
                    wendu1 = elements3.select("span").get(0).text();//获取最低温度
                    hight = elements4.select("i").get(0).text();//获取最高温度
                    feng = elements5.select("span").attr("title");//获取风向
                    Weather.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Weather.this,"日期"+day+"天气"+tianqi1+"对低温度"+wendu1+"最高温度"+hight+"风向"+feng,Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (Exception e) {
                }
            }
        }.start();
        WebView webView= (WebView) findViewById(R.id.webview_weather);

        webView.loadUrl("http://weather1.sina.cn/?vt=4");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });




    }
}
