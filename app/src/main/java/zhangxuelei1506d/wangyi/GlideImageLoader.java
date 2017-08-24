package zhangxuelei1506d.wangyi;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

/**
 * date 2017/8/8
 * author:张学雷(Administrator)
 * functinn:
 */

public class GlideImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }

    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;
    }


}
