package zhangxuelei1506d.wangyi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * date 2017/8/21
 * author:张学雷(Administrator)
 * functinn:
 */

public class PhotoViewCla extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myphotoview);
       PhotoView photoView= (PhotoView) findViewById(R.id.myPhotoView_View);

        String ppp = getIntent().getStringExtra("ppp");

        Glide.with(this).load(ppp).into(photoView);

        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {


                finish();

            }
        });


    }
}
