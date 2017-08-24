package zhangxuelei1506d.wangyi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import zhangxuelei1506d.wangyi.R;

/**
 * date 2017/8/17
 * author:张学雷(Administrator)
 * functinn:
 */

public class shezhiAdapter extends BaseAdapter{
     private List<String> list;
         private Context context;

         public shezhiAdapter(List<String> list, Context context) {
             this.list = list;
             this.context=context;
         }

         @Override
         public int getCount() {
             return list.size();
         }

         @Override
         public Object getItem(int position) {
             return null;
         }

         @Override
         public long getItemId(int position) {
             return 0;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             viewHolder holder=null;
             if (convertView==null){
                 holder=new viewHolder();
                 convertView=convertView.inflate(context, R.layout.item_shezhi,null);
                 holder.tvContent= (TextView) convertView.findViewById(R.id.text_shezhi);
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
