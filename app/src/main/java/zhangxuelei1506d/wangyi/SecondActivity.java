package zhangxuelei1506d.wangyi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * date 2017/8/11
 * author:张学雷(Administrator)
 * functinn:
 */

public class SecondActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);

        CaptureFragment captureFragment= new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();


    }
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {


        @Override


        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {


            Intent resultIntent = new Intent();


            Bundle bundle = new Bundle();


            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);


            bundle.putString(CodeUtils.RESULT_STRING, result);


            resultIntent.putExtras(bundle);


            SecondActivity.this.setResult(RESULT_OK, resultIntent);


            SecondActivity.this.finish();


        }


        @Override


        public void onAnalyzeFailed() {


            Intent resultIntent = new Intent();


            Bundle bundle = new Bundle();


            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);


            bundle.putString(CodeUtils.RESULT_STRING, "");


            resultIntent.putExtras(bundle);


            SecondActivity.this.setResult(RESULT_OK, resultIntent);


            SecondActivity.this.finish();


        }


    };

    public void exit(View view){  finish();  }
}
