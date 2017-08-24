package zhangxuelei1506d.wangyi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import zhangxuelei1506d.wangyi.liguangjie.FiveFragment;
import zhangxuelei1506d.wangyi.liguangjie.FourFragment;
import zhangxuelei1506d.wangyi.liguangjie.SecondFragment;
import zhangxuelei1506d.wangyi.liguangjie.ThreeFragment;

import static zhangxuelei1506d.wangyi.R.drawable.sss;
import static zhangxuelei1506d.wangyi.R.layout.item;

/**
 * date 2017/8/3
 * author:张学雷(Administrator)
 * functinn:
 */

public class NaviFragment extends Fragment implements View.OnClickListener {
    private Toutiao_Fragment main_fragment;
    private SecondFragment second_fragment;
    private ThreeFragment three_fragment;
    private FourFragment four_fragment;
    private FiveFragment five_fragment;
    private View view;

    private DrawerLayout sss;
    /**
     * 新闻
     */
    private TextView mXinwenNavi;
    /**
     * 订阅
     */
    private TextView mDingyueNavi;
    /**
     * 图片
     */
    private TextView mTupianNavi;
    /**
     * 视频
     */
    private TextView mShipinNavi;
    /**
     * 跟帖
     */
    private TextView mGentieNavi;
    private CallBackValue callBackValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        initView(view);
        main_fragment = new Toutiao_Fragment();
        second_fragment = new SecondFragment();
        three_fragment = new ThreeFragment();
        four_fragment = new FourFragment();
        five_fragment = new FiveFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.linear, main_fragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.linear, second_fragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.linear, three_fragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.linear, four_fragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.linear, five_fragment).commit();
        getActivity().getSupportFragmentManager().beginTransaction().hide(second_fragment).hide(three_fragment).hide(four_fragment).hide(five_fragment).commit();
        return view;
    }
    private void initView(View view) {
        mXinwenNavi = (TextView) view.findViewById(R.id.xinwen_Navi);
        mXinwenNavi.setOnClickListener(this);
        mDingyueNavi = (TextView) view.findViewById(R.id.dingyue_Navi);
        mDingyueNavi.setOnClickListener(this);
        mTupianNavi = (TextView) view.findViewById(R.id.tupian_Navi);
        mTupianNavi.setOnClickListener(this);
        mShipinNavi = (TextView) view.findViewById(R.id.shipin_Navi);
        mShipinNavi.setOnClickListener(this);
        mGentieNavi = (TextView) view.findViewById(R.id.gentie_Navi);
        mGentieNavi.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xinwen_Navi:


                callBackValue.SendManagerValue();


                getActivity().getSupportFragmentManager().beginTransaction().show(main_fragment).hide(second_fragment).hide(three_fragment).hide(four_fragment).hide(five_fragment).commit();

                break;
            case R.id.dingyue_Navi:
                callBackValue.SendManagerValue();
                getActivity().getSupportFragmentManager().beginTransaction().show(second_fragment).hide(main_fragment).hide(three_fragment).hide(four_fragment).hide(five_fragment).commit();

                break;
            case R.id.tupian_Navi:
                callBackValue.SendManagerValue();
                getActivity().getSupportFragmentManager().beginTransaction().show(three_fragment).hide(main_fragment).hide(second_fragment).hide(four_fragment).hide(five_fragment).commit();

                break;
            case R.id.shipin_Navi:
                callBackValue.SendManagerValue();
                getActivity().getSupportFragmentManager().beginTransaction().show(four_fragment).hide(main_fragment).hide(second_fragment).hide(three_fragment).hide(five_fragment).commit();

                break;
            case R.id.gentie_Navi:
                callBackValue.SendManagerValue();

                getActivity().getSupportFragmentManager().beginTransaction().show(five_fragment).hide(main_fragment).hide(second_fragment).hide(three_fragment).hide(four_fragment).commit();

                break;
        }



    }
    public interface CallBackValue{
        void SendManagerValue();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackValue = (CallBackValue) getActivity();
    }



}
