package zhangxuelei1506d.wangyi.liguangjie;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import zhangxuelei1506d.wangyi.Bean.JsonBean;
import zhangxuelei1506d.wangyi.Bean.Utils;
import zhangxuelei1506d.wangyi.Content;
import zhangxuelei1506d.wangyi.R;

/**
 * date 2017/8/15
 * author:张学雷(Administrator)
 * functinn:
 */

public class myTuPianAdapter extends BaseAdapter{

    private List<JsonBean.ResultBean.DataBean> list;
    private Context content;

    public myTuPianAdapter(List<JsonBean.ResultBean.DataBean> list, Context content) {
        this.list = list;
        this.content = content;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder=null;

        if(view==null){
            holder=new viewHolder();
            view=view.inflate(content, R.layout.item_tupian,null);
            holder.imageView= (ImageView) view.findViewById(R.id.image_tupian);
            view.setTag(holder);



        }else{
           holder= (viewHolder) view.getTag();

        }

        Utils.setimage(holder.imageView,list.get(i).thumbnail_pic_s);

        return view;
    }

    class viewHolder{
        ImageView imageView;

    }
}
