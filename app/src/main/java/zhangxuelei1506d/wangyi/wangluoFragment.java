package zhangxuelei1506d.wangyi;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import zhangxuelei1506d.wangyi.Bean.JsonBean;
import zhangxuelei1506d.wangyi.Bean.Utils;
import zhangxuelei1506d.wangyi.Bean.myBaseAdapter;
import zhangxuelei1506d.wangyi.view.XListView;

import static android.R.attr.y;
import static android.R.id.list;
import static com.umeng.qq.handler.a.n;
import static com.umeng.qq.handler.a.p;


/**
 * A simple {@link Fragment} subclass.
 */
public class wangluoFragment extends Fragment implements XListView.IXListViewListener {
    private XListView mToutiaoListview;
    private List<JsonBean.ResultBean.DataBean> been;
    private myBaseAdapter adapter;
    private List<List<String>> list;
    String path;
    private View view;
    private SQLiteDatabase db;
    private Window window = null;
    private ImageView iv;
    private Bitmap bms;
    private FrameLayout frameLayout;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wangluo, container, false);
        initView(view);
        mySqliteDataBase dataBase = new mySqliteDataBase(getActivity());
        db = dataBase.getWritableDatabase();

        boolean wifiIscontent = Utils.WifiIscontent(getActivity());
        if (wifiIscontent == true) {

            RequestParams params = new RequestParams(path);

            x.http().get(params, new Callback.CommonCallback<String>() {


                @Override
                public void onSuccess(String result) {


                    Gson gson = new Gson();
                    been = new ArrayList<JsonBean.ResultBean.DataBean>();
                    JsonBean jsonBean = gson.fromJson(result, JsonBean.class);
                    been = jsonBean.result.data;


                    list = new ArrayList<List<String>>();
                    final List<String> list2 = new ArrayList<String>();
                    for (int i = 0; i < been.size(); i++) {
                        List<String> list_str = new ArrayList<String>();
                        if (been.get(i).thumbnail_pic_s != null) {
                            String s = been.get(i).thumbnail_pic_s;
                            list_str.add(s);
                        }
                        if (been.get(i).thumbnail_pic_s02 != null) {
                            String s = been.get(i).thumbnail_pic_s02;
                            list_str.add(s);
                        }
                        if (been.get(i).thumbnail_pic_s03 != null) {
                            String s = been.get(i).thumbnail_pic_s03;
                            list_str.add(s);
                        }
                        list.add(list_str);

                    }


                    adapter = new myBaseAdapter(getActivity(), been, list);

                    for (int i = 5; i < 10; i++) {

                        list2.add(been.get(i).thumbnail_pic_s);

                    }

                    View view = View.inflate(getActivity(), R.layout.bannerfragment, null);

                    final Banner banner = (Banner) view.findViewById(R.id.bannnnnnnnnner);
                    banner.setImageLoader(new GlideImageLoader());
                    //设置图片集合
                    banner.setImages(list2);
                    banner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            Intent intent = new Intent(getActivity(), PhotoViewCla.class);
                            intent.putExtra("ppp", list2.get(position));
                            startActivity(intent);
                        }
                    });
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();


                    mToutiaoListview.addHeaderView(view);
                    mToutiaoListview.setAdapter(adapter);


                    mToutiaoListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getActivity(), Content.class);
                            intent.putExtra("path", been.get(i - 2).url);
                            intent.putExtra("title", been.get(i - 2).title);
                            intent.putExtra("image", been.get(i - 2).thumbnail_pic_s);

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

        } else {
            Toast.makeText(getActivity(), "网络已断开", Toast.LENGTH_SHORT).show();
            Cursor cursor = db.query("lixian", null, null, null, null, null, null);
            List<JsonBean.ResultBean.DataBean> list = new ArrayList<>();
            while (cursor.moveToNext()) {

                JsonBean.ResultBean.DataBean dataBean = new JsonBean.ResultBean.DataBean();
                dataBean.title = cursor.getString(cursor.getColumnIndex("title"));
                dataBean.date = cursor.getString(cursor.getColumnIndex("data"));
                dataBean.thumbnail_pic_s = cursor.getString(cursor.getColumnIndex("thumbnail_pic_s"));
                dataBean.thumbnail_pic_s02 = cursor.getString(cursor.getColumnIndex("thumbnail_pic_s02"));
                dataBean.thumbnail_pic_s03 = cursor.getString(cursor.getColumnIndex("thumbnail_pic_s03"));

                list.add(dataBean);
            }


            List listt = new ArrayList<List<String>>();
            List<String> list2 = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                List<String> list_str = new ArrayList<String>();
                if (list.get(i).thumbnail_pic_s != null) {
                    String s = list.get(i).thumbnail_pic_s;
                    list_str.add(s);
                }
                if (list.get(i).thumbnail_pic_s02 != null) {
                    String s = list.get(i).thumbnail_pic_s02;
                    list_str.add(s);
                }
                if (list.get(i).thumbnail_pic_s03 != null) {
                    String s = list.get(i).thumbnail_pic_s03;
                    list_str.add(s);
                }
                listt.add(list_str);

            }

            myBaseAdapter adapter = new myBaseAdapter(getActivity(), list, listt);
            mToutiaoListview.setAdapter(adapter);
            for (int i = 5; i < 10; i++) {

                list2.add(list.get(i).thumbnail_pic_s);

            }

            View view = View.inflate(getActivity(), R.layout.bannerfragment, null);

            Banner banner = (Banner) view.findViewById(R.id.bannnnnnnnnner);
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(list2);
            //banner设置方法全部调用完毕时最后调用
            banner.start();

            mToutiaoListview.addHeaderView(view);
            mToutiaoListview.setAdapter(adapter);


        }


        return view;
    }

    private void initView(View view) {
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        mToutiaoListview = (XListView) view.findViewById(R.id.toutiao_Listview);
        mToutiaoListview.setPullLoadEnable(true);
        mToutiaoListview.setPullRefreshEnable(true);

        mToutiaoListview.setXListViewListener(this);
    }

    public void setPath(String str) {

        this.path = str;

    }

    @Override
    public void onRefresh() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    URL url = new URL("http://v.juhe.cn/toutiao/index?type=beijing&key=e76b62dbe5ce78645516fe866dc7058b");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        while ((len = inputStream.read(buffer)) != -1) {
                            outputstream.write(buffer, 0, len);

                        }
                        return outputstream.toString();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                List<JsonBean.ResultBean.DataBean> been2 = new ArrayList<JsonBean.ResultBean.DataBean>();
                JsonBean jsonBean = gson.fromJson(s, JsonBean.class);
                been2 = jsonBean.result.data;


                for (int i = 0; i < been2.size(); i++) {
                    List<String> list_str = new ArrayList<String>();
                    if (been2.get(i).thumbnail_pic_s != null) {
                        String ss = been2.get(i).thumbnail_pic_s;
                        list_str.add(ss);
                    }
                    if (been2.get(i).thumbnail_pic_s02 != null) {
                        String ss = been2.get(i).thumbnail_pic_s02;
                        list_str.add(ss);
                    }
                    if (been2.get(i).thumbnail_pic_s03 != null) {
                        String ss = been2.get(i).thumbnail_pic_s03;
                        list_str.add(ss);
                    }
                    list.add(0, list_str);

                }


                for (int i = 0; i < been2.size(); i++) {
                    JsonBean.ResultBean.DataBean b = new JsonBean.ResultBean.DataBean();
                    b.title = been2.get(i).title;
                    b.date = been2.get(i).date;
                    b.thumbnail_pic_s = been2.get(i).thumbnail_pic_s;
                    b.thumbnail_pic_s02 = been2.get(i).thumbnail_pic_s02;
                    b.thumbnail_pic_s03 = been2.get(i).thumbnail_pic_s03;
                    been.add(0, b);
                }

                adapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                mToutiaoListview.stopRefresh();


            }
        }.execute();


    }

    @Override
    public void onLoadMore() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {

                try {
                    URL url = new URL("http://v.juhe.cn/toutiao/index?type=zhengwu&key=e76b62dbe5ce78645516fe866dc7058b");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        while ((len = inputStream.read(buffer)) != -1) {
                            outputstream.write(buffer, 0, len);

                        }
                        return outputstream.toString();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                List<JsonBean.ResultBean.DataBean> been2 = new ArrayList<JsonBean.ResultBean.DataBean>();
                JsonBean jsonBean = gson.fromJson(s, JsonBean.class);
                been2 = jsonBean.result.data;

                for (int i = 0; i < been2.size(); i++) {
                    List<String> list_str = new ArrayList<String>();
                    if (been2.get(i).thumbnail_pic_s != null) {
                        String ss = been2.get(i).thumbnail_pic_s;
                        list_str.add(ss);
                    }
                    if (been2.get(i).thumbnail_pic_s02 != null) {
                        String ss = been2.get(i).thumbnail_pic_s02;
                        list_str.add(ss);
                    }
                    if (been2.get(i).thumbnail_pic_s03 != null) {
                        String ss = been2.get(i).thumbnail_pic_s03;
                        list_str.add(ss);
                    }
                    list.add(list_str);

                }


                for (int i = 0; i < been2.size(); i++) {
                    JsonBean.ResultBean.DataBean b = new JsonBean.ResultBean.DataBean();
                    b.title = been2.get(i).title;
                    b.date = been2.get(i).date;
                    b.thumbnail_pic_s = been2.get(i).thumbnail_pic_s;
                    b.thumbnail_pic_s02 = been2.get(i).thumbnail_pic_s02;
                    b.thumbnail_pic_s03 = been2.get(i).thumbnail_pic_s03;
                    been.add(b);
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                mToutiaoListview.stopRefresh();


            }
        }.execute();
    }


}