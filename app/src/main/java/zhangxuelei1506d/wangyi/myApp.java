package zhangxuelei1506d.wangyi;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.xutils.x;

/**
 * date 2017/8/8
 * author:张学雷(Administrator)
 * functinn:
 */

public class myApp extends Application {
    @Override
    public void onCreate() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=58155541");
        super.onCreate();
        x.Ext.init(this);
        Fresco.initialize(this);
        UMShareAPI.get(this);
        ZXingLibrary.initDisplayOpinion(this);
        PlatformConfig.setQQZone("1106036236", "mjFCi0oxXZKZEWJs");


    }

}
