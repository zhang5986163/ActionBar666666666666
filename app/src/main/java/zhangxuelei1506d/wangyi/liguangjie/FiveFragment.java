package zhangxuelei1506d.wangyi.liguangjie;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zhangxuelei1506d.wangyi.R;
import zhangxuelei1506d.wangyi.myAdapter;
import zhangxuelei1506d.wangyi.wangluoFragment;

import static com.umeng.socialize.a.b.d.i;


/**
 * A simple {@link Fragment} subclass.
 */
public class FiveFragment extends Fragment {

    private View view;
    private TabLayout mFiveTablayoutD;
    private ViewPager mViewpagerFive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_five, container, false);
        mFiveTablayoutD = (TabLayout) view.findViewById(R.id.five_tablayout_ddasdasd);
        mViewpagerFive = (ViewPager) view.findViewById(R.id.viewpager_five);

        List<Fragment> list=new ArrayList<>();

        wangluoFragment fragment = new wangluoFragment();
        fragment.setPath("http://v.juhe.cn/toutiao/index?type=tiyu&key=e76b62dbe5ce78645516fe866dc7058b");
        list.add(fragment);
        wangluoFragment fragment2 = new wangluoFragment();
        fragment2.setPath("http://v.juhe.cn/toutiao/index?type=duanzi&key=e76b62dbe5ce78645516fe866dc7058b");
        list.add(fragment2);
        wangluoFragment fragment3 = new wangluoFragment();
        fragment3.setPath("http://v.juhe.cn/toutiao/index?type=beijing&key=e76b62dbe5ce78645516fe866dc7058b");
        list.add(fragment3);


        myAdapter adapter = new myAdapter(getActivity().getSupportFragmentManager());
        adapter.setFragment(list);


        mViewpagerFive.setAdapter(adapter);
        for (int i = 0; i < 3; i++) {

            mFiveTablayoutD.addTab(mFiveTablayoutD.newTab());
        }

       mFiveTablayoutD.setupWithViewPager(mViewpagerFive);

        mFiveTablayoutD.getTabAt(0).setText("跟帖策划");
        mFiveTablayoutD.getTabAt(1).setText("精彩跟贴");
        mFiveTablayoutD.getTabAt(2).setText("今日排行");





        return view;


    }



}
