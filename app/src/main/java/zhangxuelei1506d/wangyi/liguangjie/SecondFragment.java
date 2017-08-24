package zhangxuelei1506d.wangyi.liguangjie;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhangxuelei1506d.wangyi.Bean.myBaseAdapter;
import zhangxuelei1506d.wangyi.MainActivity;
import zhangxuelei1506d.wangyi.R;
import zhangxuelei1506d.wangyi.myBaseAdapter2;
import zhangxuelei1506d.wangyi.mySqliteDataBase;
import zhangxuelei1506d.wangyi.mySqliteDatabase2;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    private ListView gridView_my;
    private ListView gridView_other;
    private SQLiteDatabase db;
    private myBaseAdapter2 adapter_other;
    private myBaseAdapter2 adapter_my;
    private List<String> list_my;
    private List<String> list_other;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second, container, false);

        init();
        mySqliteDatabase2 database = new mySqliteDatabase2(getActivity());
        db = database.getWritableDatabase();
        list_my = new ArrayList<>();



        list_my.add("中国普法");




        list_other = new ArrayList<>();
        list_other.add("钛媒体APP");
        list_other.add("NASA中文");
        list_other.add("刘柠");
        list_other.add("德新社");
        list_other.add("六神磊磊读金庸");
        list_other.add("闫红和陈思呈");
        list_other.add("葡萄酒智库");
        list_other.add("穷游网");
        list_other.add("懂个球");
        list_other.add("杨红旭评楼");



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
//                db.delete("item", null, null);
            }
        });






        return view;
    }

    private void init() {
        gridView_my = (ListView) view.findViewById(R.id.my_pindao);
        gridView_other = (ListView) view.findViewById(R.id.other_pindao);

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
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
//        int x = initLocation[0];
//        int y = initLocation[1];
        //将控件放入布局里
        viewGroup.addView(view);
        //设置宽高
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mLayoutParams.leftMargin = x;
//        mLayoutParams.topMargin = y;
        //让imageView使用设置的宽高
        view.setLayoutParams(mLayoutParams);
        return view;
    }
    private ViewGroup getMoveViewGroup() {
        //创建一个居于所有Activity之上控件集合
        ViewGroup moveViewGroup = (ViewGroup)getActivity(). getWindow().getDecorView();
        // 创建一个布局
        LinearLayout moveLinearLayout = new LinearLayout(getActivity());
        //设置布局的宽高
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //将布局放入控件集合里
        moveViewGroup.addView(moveLinearLayout);
        //返回布局
        return moveLinearLayout;
    }
    private void MoveAnim(ImageView moveView, int[] startLocation, int[] endLocation) {
        //获取布局对象
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, startLocation);
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
        mMoveView.startAnimation(moveAnimationSet);
        //当动画执行完后将图片设为不可见
        mMoveView.setVisibility(View.INVISIBLE);
    }

}
