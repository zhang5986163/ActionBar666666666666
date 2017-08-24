package zhangxuelei1506d.wangyi;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import zhangxuelei1506d.wangyi.Bean.JsonBean;
import zhangxuelei1506d.wangyi.Bean.myBaseAdapter;
import zhangxuelei1506d.wangyi.view.XListView;

import static android.R.attr.color;
import static android.R.attr.name;
import static android.R.id.list;
import static com.umeng.socialize.a.b.d.i;
import static zhangxuelei1506d.wangyi.R.id.action_settings;
import static zhangxuelei1506d.wangyi.R.id.action_settings_lixian;
import static zhangxuelei1506d.wangyi.R.id.action_settings_saoyisao;
import static zhangxuelei1506d.wangyi.R.id.action_settings_sousuo;
import static zhangxuelei1506d.wangyi.R.id.action_settings_tianqi;
import static zhangxuelei1506d.wangyi.R.id.action_settings_yejian;
import static zhangxuelei1506d.wangyi.R.id.webView;

public class MainActivity extends AppCompatActivity implements ThemeManager.OnThemeChangeListener, NaviFragment.CallBackValue {
    public static final int REQUEST_CODE = 1;
    private DrawerLayout mActivityMain;
    private ActionBarDrawerToggle mtoggle;
    private SQLiteDatabase db;
    private List<JsonBean.ResultBean.DataBean> been;
    private ActionBar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySqliteDataBase dataBase = new mySqliteDataBase(this);
        db = dataBase.getWritableDatabase();
        initView();
        initActionBar();
        supportActionBar = getSupportActionBar();
        ThemeManager.registerThemeChangeListener(this);


    }

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        mtoggle = new ActionBarDrawerToggle(this, mActivityMain, R.string.open, R.string.close);
        mtoggle.syncState();
        mActivityMain.addDrawerListener(mtoggle);
    }

    private void initView() {
        mActivityMain = (DrawerLayout) findViewById(R.id.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActivityMain.isDrawerOpen(GravityCompat.END)) {
            mActivityMain.closeDrawer(GravityCompat.END);//关闭抽屉
            return super.onOptionsItemSelected(item);
        }


        switch (item.getItemId()) {
            case action_settings_tianqi:
                startActivity(new Intent(MainActivity.this, Weather.class));

                break;
            case action_settings_lixian:
                Toast.makeText(this, "当前页面离线成功", Toast.LENGTH_SHORT).show();

                RequestParams params = new RequestParams("http://v.juhe.cn/toutiao/index?type=top&key=e76b62dbe5ce78645516fe866dc7058b");

                x.http().get(params, new Callback.CommonCallback<String>() {


                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        been = new ArrayList<JsonBean.ResultBean.DataBean>();
                        JsonBean jsonBean = gson.fromJson(result, JsonBean.class);
                        been = jsonBean.result.data;

                        for (int i = 0; i < been.size(); i++) {
                            ContentValues values = new ContentValues();
                            values.put("title", been.get(i).title);
                            values.put("data", been.get(i).date);
                            values.put("thumbnail_pic_s", been.get(i).thumbnail_pic_s);
                            values.put("thumbnail_pic_s02", been.get(i).thumbnail_pic_s02);
                            values.put("thumbnail_pic_s03", been.get(i).thumbnail_pic_s03);
                            db.insert("lixian", null, values);

                        }
                    }


                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });


                break;
            case action_settings_yejian:
                ThemeManager.setThemeMode(ThemeManager.getThemeMode() == ThemeManager.ThemeMode.DAY
                        ? ThemeManager.ThemeMode.NIGHT : ThemeManager.ThemeMode.DAY);
                break;
            case action_settings_sousuo:
                startActivity(new Intent(MainActivity.this, sousuo.class));

                break;
            case action_settings_saoyisao:


                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //权限发生了改变 true  //  false 小米
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {


                        new AlertDialog.Builder(this).setTitle("title")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 请求授权
                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

                                    }
                                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();


                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);

                    }

                } else {

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                    startActivityForResult(intent, 5);

                }


                break;
            case action_settings:
                startActivity(new Intent(MainActivity.this, shezhi_activity.class));


                break;
            case R.id.image_actionbar:


                mActivityMain.openDrawer(GravityCompat.END);

                if (mActivityMain.isDrawerOpen(GravityCompat.START)) {
                    mActivityMain.closeDrawer(GravityCompat.START);//关闭抽屉
                    return super.onOptionsItemSelected(item);
                }

                break;

        }


        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    //记得要重写这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5) {


            //处理扫描结果（在界面上显示）


            if (null != data) {


                Bundle bundle = data.getExtras();


                if (bundle == null) {


                    return;


                }


                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {


                    String result = bundle.getString(CodeUtils.RESULT_STRING);


                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();


                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {


                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();


                }


            }


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            // camear 权限回调

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // 表示用户授权
                Toast.makeText(this, " user Permission", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                startActivityForResult(intent, 5);


            } else {

                //用户拒绝权限
                Toast.makeText(this, " no Permission", Toast.LENGTH_SHORT).show();

            }


        }

    }

    public void initTheme() {
        mActivityMain.setBackgroundColor(getResources().getColor(ThemeManager.getCurrentThemeRes(MainActivity.this, R.color.backgroundColor)));
        // 设置标题栏颜色
        if (supportActionBar != null) {
            supportActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(ThemeManager.getCurrentThemeRes(MainActivity.this, R.color.colorPrimary))));
        }
        // 设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(ThemeManager.getCurrentThemeRes(MainActivity.this, R.color.colorPrimary)));
        }
    }

    @Override
    public void onThemeChanged() {
        initTheme();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.unregisterThemeChangeListener(this);
    }


    @Override
    public void SendManagerValue() {
        mActivityMain.closeDrawer(GravityCompat.START);//关闭抽屉
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final AlertDialog alert = new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                .setMessage("您确定要离开我们了么？")
                .setPositiveButton("含泪离开", new DialogInterface.OnClickListener() {//设置确定按钮
                    @Override//处理确定按钮点击事件
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("再看一会儿", null).create();

        alert.show();
        return super.onKeyDown(keyCode, event);
    }


}
