package zhangxuelei1506d.wangyi.liguangjie;


import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iflytek.thirdparty.S;

import zhangxuelei1506d.wangyi.R;

import static zhangxuelei1506d.wangyi.R.id.webView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends Fragment {
    private String Path="http://news.163.com/special/00012AE9/videonews.html";
    private WebView webView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_four, container, false);

        webView = (WebView) view.findViewById(R.id.shipin_web);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
//        settings.setUseWideViewPort(true);
//       settings.setLoadWithOverviewMode(true);
        webView.loadUrl(Path);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        // 设置回退

        return view;

    }


}
