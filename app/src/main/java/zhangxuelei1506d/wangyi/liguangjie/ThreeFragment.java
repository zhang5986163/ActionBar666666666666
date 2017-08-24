package zhangxuelei1506d.wangyi.liguangjie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import zhangxuelei1506d.wangyi.Bean.JsonBean;
import zhangxuelei1506d.wangyi.Content;
import zhangxuelei1506d.wangyi.R;
import zhangxuelei1506d.wangyi.view.XListView;

/**
 * date 2017/8/10
 * author:张学雷(Administrator)
 * functinn:
 */

public class ThreeFragment extends Fragment implements XListView.IXListViewListener{
    private List<JsonBean.ResultBean.DataBean> been;
    private XListView xListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.three_fragment, container, false);

        xListView = (XListView) inflate.findViewById(R.id.tupian_xlistview);

        RequestParams params = new RequestParams("http://v.juhe.cn/toutiao/index?type=tupian&key=e76b62dbe5ce78645516fe866dc7058b");

        x.http().get(params, new Callback.CommonCallback<String>() {

            private List<List<String>> list;

            @Override
            public void onSuccess(String result) {


                Gson gson = new Gson();
                been = new ArrayList<JsonBean.ResultBean.DataBean>();
                JsonBean jsonBean = gson.fromJson(result, JsonBean.class);
                been = jsonBean.result.data;


               myTuPianAdapter adapter=new myTuPianAdapter(been,getActivity());

                xListView.setAdapter(adapter);


                xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       Intent intent=new Intent(getActivity(),Content.class);
                        intent.putExtra("path", been.get(i - 1).url);
                        intent.putExtra("title", been.get(i - 1).title);
                        intent.putExtra("image", been.get(i - 1).thumbnail_pic_s);
                        startActivity(intent);
                    }
                });










            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });









        return inflate;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
