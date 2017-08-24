package zhangxuelei1506d.wangyi.Bean;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import zhangxuelei1506d.wangyi.R;

import static android.R.id.list;

/**
 * date 2017/8/8
 * author:张学雷(Administrator)
 * functinn:
 */

public class Utils {

    public static void setimage(ImageView image, String path) {

        ImageOptions options = new ImageOptions.Builder()
                //设置图片的大小
                .setSize(300, 300)
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                //设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.ic_launcher)
                //设置使用缓存
                .setUseMemCache(true)
                //设置支持gif
                .setIgnoreGif(false).setCircular(true).build();
        //设置显示圆形图片
        // .setCircular(true).build();
        x.image().bind(image, path);
    }


    public static boolean WifiIscontent(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            return true;

        }

        return false;
    }
}
