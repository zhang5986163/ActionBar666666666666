package zhangxuelei1506d.wangyi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * date 2017/8/16
 * author:张学雷(Administrator)
 * functinn:
 */

public class myBaseAdapter2 extends BaseAdapter{
     private List<String> list;
         private Context context;

         public myBaseAdapter2(List<String> list, Context context) {
             this.list = list;
             this.context=context;
         }

         @Override
         public int getCount() {
             return list.size();
         }

         @Override
         public Object getItem(int position) {
             return list.get(position);
         }

         @Override
         public long getItemId(int position) {
             return position;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             viewHolder holder=null;
             if (convertView==null){
                 holder=new viewHolder();
                 convertView=convertView.inflate(context,R.layout.adapter_mygridview_item,null);
                 holder.tvContent= (TextView) convertView.findViewById(R.id.text_item);
                 convertView.setTag(holder);

             }else{
                 holder= (viewHolder) convertView.getTag();
             }

             holder.tvContent.setText(list.get(position));
             return convertView;
         }
         class viewHolder{
             TextView tvContent;

         }



}
