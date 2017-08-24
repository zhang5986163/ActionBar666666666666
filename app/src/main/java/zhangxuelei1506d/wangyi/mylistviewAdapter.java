package zhangxuelei1506d.wangyi;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;
import java.util.Set;

import zhangxuelei1506d.wangyi.Bean.Utils;
import zhangxuelei1506d.wangyi.R;

/**
 * date 2017/8/20
 * author:张学雷(Administrator)
 * functinn:
 */

public class mylistviewAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private UMShareAPI mShareAPI = null;

    public mylistviewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
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
        viewHolder holder = null;
        if (convertView == null) {
            holder = new viewHolder();
            convertView = convertView.inflate(context, R.layout.navifragment_right, null);

            holder.dansangfangdenglu_tupian = (ImageView) convertView.findViewById(R.id.dansangfangdenglu_tupian);
            holder.disanfangdenglu_lijidenglu = (TextView) convertView.findViewById(R.id.disanfangdenglu_lijidenglu);
            holder.disanfdenglu = (LinearLayout) convertView.findViewById(R.id.disanfdenglu);
            convertView.setTag(holder);

        } else {
            holder = (viewHolder) convertView.getTag();
        }

        mShareAPI = UMShareAPI.get(context);
        final viewHolder finalHolder = holder;
        holder.disanfdenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UMAuthListener umAuthListener = new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        //回调成功，即登陆成功后这里返回Map<String, String> map，map里面就是用户的信息，可以拿出来使用了
                        Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
                        if (map != null) {
                            Set<String> set = map.keySet();
                            String string = "";
                            for (String str : set) {
                                String s = map.get(str);

                                string += s;

                            }
                            String profile_image_url = map.get("profile_image_url");
                            String screen_name = map.get("screen_name");
                            finalHolder.disanfangdenglu_lijidenglu.setText(screen_name);


                            Utils.setimage(finalHolder.dansangfangdenglu_tupian, profile_image_url);


                            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Toast.makeText(context, "授权取消", Toast.LENGTH_SHORT).show();
                    }
                };
                mShareAPI.getPlatformInfo((Activity) context, SHARE_MEDIA.QQ, umAuthListener);

            }
        });


        return convertView;
    }

    class viewHolder {
        TextView tvContent;
        ImageView dansangfangdenglu_tupian;
        TextView disanfangdenglu_lijidenglu;
        LinearLayout disanfdenglu;

    }

}
