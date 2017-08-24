package zhangxuelei1506d.wangyi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import zhangxuelei1506d.wangyi.Bean.Utils;

import static android.R.id.list;
import static com.umeng.qq.handler.a.s;
import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * date 2017/8/7
 * author:张学雷(Administrator)
 * functinn:
 */

public class NaviFragment_Right extends Fragment {
    private ImageView mDansangfangdengluTupian;
    /**
     * 立即登录
     */
    private UMShareAPI mShareAPI = null;
    private TextView mDisanfangdengluLijidenglu;
    private LinearLayout mDisanfdenglu;
    private ParallaxListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right, container, false);

        listView = (ParallaxListView) view.findViewById(R.id.listview_listview_lisitview);


        List<String> list = new ArrayList<>();
        list.add("fuck");



        View headView = View.inflate(getActivity(), R.layout.layout_head, null);
        listView.addHeaderView(headView);
        final ImageView iv_header = (ImageView) headView.findViewById(R.id.iv_header);
        //等View界面全部绘制完毕的时候去得到已经绘制完控件的宽和高，查一下这个方法并做一个笔记出来
        iv_header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                //宽和高已经测量完毕
                listView.setIv_head(iv_header);
                //释放资源
                iv_header.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mylistviewAdapter adapter = new mylistviewAdapter(list, getActivity());
        listView.setAdapter(adapter);



        return view;
    }


}
