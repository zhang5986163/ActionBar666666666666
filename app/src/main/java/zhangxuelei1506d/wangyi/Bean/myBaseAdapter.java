package zhangxuelei1506d.wangyi.Bean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import zhangxuelei1506d.wangyi.R;

import static com.umeng.socialize.a.b.d.i;
import static zhangxuelei1506d.wangyi.R.id.thumbnail_pic_s_1;


/**
 * date 2017/8/8
 * author:张学雷(Administrator)
 * functinn:
 */

public class myBaseAdapter extends BaseAdapter {
    public Context context;
    public List<JsonBean.ResultBean.DataBean> list;
    public List<List<String>> listS;
    public int type1 = 1;
    public int type2 = 2;
    public int type3 = 3;


    public myBaseAdapter(Context context, List<JsonBean.ResultBean.DataBean> list, List<List<String>> listS) {
        this.context = context;
        this.list = list;
        this.listS = listS;
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
    public int getItemViewType(int position) {
        if (position == 1) {
            return type1;
        } else if (listS.get(position).size() == 2) {
            return type2;
        } else if (listS.get(position).size() == 3) {
            return type3;
        }
        return type1;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler1 hodler1 = null;
        ViewHodler2 hodler2 = null;
        ViewHodler3 hodler3 = null;
        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {

                case 1:
                    hodler1 = new ViewHodler1();
                    convertView = convertView.inflate(context, R.layout.item, null);
                    hodler1.thumbnail_pic_s_1 = (PhotoView) convertView.findViewById(thumbnail_pic_s_1);
                    hodler1.title_title_1 = (TextView) convertView.findViewById(R.id.title_title_1);
                    hodler1.data_data_1 = (TextView) convertView.findViewById(R.id.data_data_1);
                    convertView.setTag(hodler1);

                    break;
                case 2:
                    hodler2 = new ViewHodler2();
                    convertView = convertView.inflate(context, R.layout.item2, null);
                    hodler2.thumbnail_pic_s01_2 = (PhotoView) convertView.findViewById(R.id.thumbnail_pic_s01_2);
                    hodler2.thumbnail_pic_s02_2 = (PhotoView) convertView.findViewById(R.id.thumbnail_pic_s02_2);
                    hodler2.title_title2 = (TextView) convertView.findViewById(R.id.title_title2);
                    hodler2.data_data2 = (TextView) convertView.findViewById(R.id.data_data2);
                    convertView.setTag(hodler2);

                    break;
                case 3:
                    hodler3 = new ViewHodler3();
                    convertView = convertView.inflate(context, R.layout.item3, null);
                    hodler3.thumbnail_pic_s01_3 = (PhotoView) convertView.findViewById(R.id.thumbnail_pic_s01_3);
                    hodler3.thumbnail_pic_s02_3 = (PhotoView) convertView.findViewById(R.id.thumbnail_pic_s02_3);
                    hodler3.thumbnthumbnail_pic_s03_3 = (PhotoView) convertView.findViewById(R.id.thumbnthumbnail_pic_s03_3);
                    hodler3.title_title3 = (TextView) convertView.findViewById(R.id.title_title3);
                    hodler3.data_data3 = (TextView) convertView.findViewById(R.id.data_data3);
                    convertView.setTag(hodler3);

                    break;
                default:
                    break;
            }
        } else {
            switch (type) {


                case 1:
                   hodler1= (ViewHodler1) convertView.getTag();
                    break;
                case 2:
                    hodler2= (ViewHodler2) convertView.getTag();
                    break;
                case 3:
                    hodler3= (ViewHodler3) convertView.getTag();
                    break;
                default:
                    break;
            }
        }
        switch (type) {

            case 1:
                hodler1.title_title_1.setText(list.get(position).title);
                hodler1.data_data_1.setText(list.get(position).date);
                Utils.setimage(hodler1.thumbnail_pic_s_1,listS.get(position).get(0));

                break;
            case 2:
                hodler2.title_title2.setText(list.get(position).title);
                hodler2.data_data2.setText(list.get(position).date);
                Utils.setimage(hodler2.thumbnail_pic_s01_2,listS.get(position).get(0));
                Utils.setimage(hodler2.thumbnail_pic_s02_2,listS.get(position).get(1));

                break;
            case 3:
                hodler3.title_title3.setText(list.get(position).title);
                hodler3.data_data3.setText(list.get(position).date);
                Utils.setimage(hodler3.thumbnail_pic_s01_3,listS.get(position).get(0));
                Utils.setimage(hodler3.thumbnail_pic_s02_3,listS.get(position).get(1));
                Utils.setimage(hodler3.thumbnthumbnail_pic_s03_3,listS.get(position).get(2));

                break;
            default:
                break;
        }

        return convertView;
    }


    class ViewHodler1 {
        PhotoView thumbnail_pic_s_1;
        TextView title_title_1, data_data_1;
    }

    class ViewHodler2 {
        PhotoView thumbnail_pic_s01_2, thumbnail_pic_s02_2;
        TextView title_title2, data_data2;
    }

    class ViewHodler3 {
        PhotoView thumbnail_pic_s01_3, thumbnail_pic_s02_3, thumbnthumbnail_pic_s03_3;
        TextView title_title3, data_data3;
    }
}