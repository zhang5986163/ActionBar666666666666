package zhangxuelei1506d.wangyi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.view.BannerViewPager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import zhangxuelei1506d.wangyi.Bean.JsonBean;
import zhangxuelei1506d.wangyi.Bean.myBaseAdapter;
import zhangxuelei1506d.wangyi.view.XListView;

import static android.R.attr.path;
import static android.R.id.list;

/**
 * date 2017/8/7
 * author:张学雷(Administrator)
 * functinn:
 */

public class Toutiao_Fragment extends Fragment {
    private XListView mToutiaoListview;
    private List<JsonBean.ResultBean.DataBean> lists;
    private String path;
    private View view;
    private TabLayout mTabLayOutMain;
    private ViewPager mViewPagerMain;
    private int theme = R.style.AppTheme;
    private ImageView mImageView;
    private GridView gridView_my;
    private GridView gridView_other;
    private List<String> list_my;
    private List<String> list_other;
    private myBaseAdapter2 adapter_other;
    private myBaseAdapter2 adapter_my;
    private SQLiteDatabase db;
    private List<Fragment> list;
    private wangluoFragment fragmentt;
    private HorizontalScrollView ho;
    private View inflate;
    private View inflate1;
    private RelativeLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.toutiao, container, false);

        x.view().inject(getActivity());
        initView(view);
        list_my = new ArrayList<>();

        list_my.add("头条");

        list_my.add("娱乐");
        list_my.add("热点");
        list_my.add("体育");
        list_my.add("北京");
        list_my.add("财经");
        list_my.add("科技");
        list_my.add("段子");
        list_my.add("图片");
        list_my.add("汽车");
        list_my.add("时尚");

        list_other = new ArrayList<>();


        list_other.add("轻松一刻");
        list_other.add("军事");
        list_other.add("历史");
        list_other.add("房产");
        list_other.add("游戏");
        list_other.add("彩票");
        list_other.add("独家");
        list_other.add("电台");
        list_other.add("政务");
        list_other.add("中国足球");
        list_other.add("冰雪运动");
        list_other.add("家居");
        list_other.add("教育");


        mySqliteDatabase2 database = new mySqliteDatabase2(getActivity());
        db = database.getWritableDatabase();


        DrawLayouy();


        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        //重新设置按下时的背景图片
                       mImageView.setImageDrawable(getResources().getDrawable(R.drawable.channel_glide_press));
                    }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        //再修改为抬起时的正常图片
                        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.channel_glide));
                    }
                    return false;
                }

        });

        mImageView.setOnClickListener(new View.OnClickListener() {

            private PopupWindow mPopuWindow;

            //这边需要一个布局就是频道管理的东西
            @Override
            public void onClick(View view) {



                LayoutInflater inflater = LayoutInflater.from(getContext());
                inflate1 = inflater.inflate(R.layout.pindaogaunli, null);

                gridView_my = (GridView) inflate1.findViewById(R.id.my_pindao);
                gridView_other = (GridView) inflate1.findViewById(R.id.other_pindao);

                adapter_other = new myBaseAdapter2(list_other, getActivity());
                gridView_other.setAdapter(adapter_other);
                adapter_my = new myBaseAdapter2(list_my, getActivity());
                gridView_my.setAdapter(adapter_my);

                //这个是其他频道的处理
                gridView_other.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = (String) adapter_other.getItem(i);
                        ContentValues values = new ContentValues();
                        values.put("title", item);
                        db.insert("item", null, values);
                        list_other.remove(i);
                        adapter_other.notifyDataSetChanged();
                        //这下面是我的频道的处理
                        Cursor cursor = db.query("item", null, null, null, null, null, null);
                        String str = null;
                        while (cursor.moveToNext()) {
                            str = cursor.getString(cursor.getColumnIndex("title"));
                        }
                        list_my.add(str);


                        DrawLayouy();


                        //获取imageview的对象
                        final ImageView moveImageView = getView(view);
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        //通过getLocationInWindow得到动画开起时的坐标 存入startLocation数组里
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        adapter_my.notifyDataSetChanged();
                        //里用Handler解决图片错位的Bug
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    //通过getLocationInWindow得到动画结束时的坐标 存入startLocation数组里
                                    int[] endLocation = new int[2];
                                    gridView_my.getChildAt(gridView_my.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation);
                                    db.delete("item", null, null);
                                    adapter_my.notifyDataSetChanged();

                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);


                    }
                });

                gridView_my.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String item = (String) adapter_my.getItem(i);

                        ContentValues values = new ContentValues();
                        values.put("title", item);
                        db.insert("item", null, values);
                        list_my.remove(i);

                        adapter_my.notifyDataSetChanged();
                        //这下面是我的频道的处理
                        Cursor cursor = db.query("item", null, null, null, null, null, null);
                        String str = null;
                        while (cursor.moveToNext()) {
                            str = cursor.getString(cursor.getColumnIndex("title"));
                        }
                        list_other.add(str);

                        DrawLayouy();

                        final ImageView moveImageView = getView(view);
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        adapter_other.notifyDataSetChanged();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    gridView_other.getChildAt(gridView_other.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation);
                                    db.delete("item", null, null);
                                    adapter_other.notifyDataSetChanged();

                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
//                adapter_other.notifyDataSetChanged();
//                db.delete("item", null,  refresh();

                    }
                });


                mPopuWindow = new PopupWindow(inflate1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mPopuWindow.setOutsideTouchable(true);
                //设置创建popuwindow里面的listview有焦点
                mPopuWindow.setFocusable(true);
                //如果想让创建popuwindow有这个动画效果,就必须有这段代码，没有这段代码动画执行不了
                mPopuWindow.setBackgroundDrawable(new ColorDrawable());

                mPopuWindow.showAsDropDown(mImageView, 0, 0);



            }
        });


        return view;
    }

    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(getActivity());
        iv.setImageBitmap(cache);
        return iv;
    }


    private void MoveAnim(ImageView moveView, int[] startLocation, int[] endLocation) {
        //获取布局对象

        linearLayout = (RelativeLayout) inflate1.findViewById(R.id.linnnnnnnnnnnnddd);
        linearLayout.addView(moveView);

        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        //启动动画

        moveView.startAnimation(moveAnimationSet);
        //当动画执行完后将图片设为不可见

        moveView.setVisibility(View.INVISIBLE);
    }


    private void initView(View view) {
        mToutiaoListview = (XListView) view.findViewById(R.id.toutiao_Listview);
        mImageView = (ImageView) view.findViewById(R.id.image_tianjiajianting);
        mTabLayOutMain = (TabLayout) view.findViewById(R.id.tab_lay_out_main);
        mViewPagerMain = (ViewPager) view.findViewById(R.id.view_pager_main);
        ho = (HorizontalScrollView) view.findViewById(R.id.hriaksjhdkasjdhkas);
    }

    private void DrawLayouy() {


        list = new ArrayList<>();
        List<String> listPath=new ArrayList<>();

        listPath.add("http://v.juhe.cn/toutiao/index?type=top&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=yule&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=redian&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=tiyu&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=beijing&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=caijing&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=keji&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=duanzi&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=tupian&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=qiche&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=shishang&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=qingsongyike&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=junshi&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=lishi&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=fangchan&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=youxi&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=caipiao&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=dujia&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=diantai&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=zhengwu&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=zhongguozuqiu&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=bingxueyundong&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=jiaju&key=e76b62dbe5ce78645516fe866dc7058b");
        listPath.add("http://v.juhe.cn/toutiao/index?type=jiaoyu&key=e76b62dbe5ce78645516fe866dc7058b");

        for (int i = 0; i < list_my.size(); i++) {



            wangluoFragment fragment = new wangluoFragment();
            fragment.setPath(listPath.get(i));
            //fragment.setPath("http://v.juhe.cn/toutiao/index?type=top&key=e76b62dbe5ce78645516fe866dc7058b");
            list.add(fragment);
        }


        myAdapter adapter = new myAdapter(getActivity().getSupportFragmentManager());
        adapter.setFragment(list);


        mViewPagerMain.setAdapter(adapter);

        for (int i = 0; i < list_my.size(); i++) {

            mTabLayOutMain.addTab(mTabLayOutMain.newTab());
        }

        mTabLayOutMain.setupWithViewPager(mViewPagerMain);
        for (int i = 0; i < list_my.size(); i++) {
            mTabLayOutMain.getTabAt(i).setText(list_my.get(i));


        }


    }


}
