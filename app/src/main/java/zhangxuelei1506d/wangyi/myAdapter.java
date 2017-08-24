package zhangxuelei1506d.wangyi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * date 2017/8/3
 * author:张学雷(Administrator)
 * functinn:
 */

public class myAdapter extends FragmentPagerAdapter {
    public myAdapter(FragmentManager fm) {
        super(fm);
    }

    private List<Fragment> list;




   public void setFragment( List<Fragment> fragments){
                list=fragments;

   }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


}
