package zhangxuelei1506d.wangyi;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

/**
 * date 2017/8/14
 * author:张学雷(Administrator)
 * functinn:
 */

public class sousuo extends Activity{
    private StringBuilder mStringBuilder;
    private Button btn_yuyinshibie;
    private EditText edit_text_shibieneirong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuo);


        btn_yuyinshibie = (Button) findViewById(R.id.btn_yuyinshibie);
        edit_text_shibieneirong = (EditText) findViewById(R.id.edit_text_shibieneirong);


        btn_yuyinshibie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecognizerDialog mDialog = new RecognizerDialog(sousuo.this, null);
                mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//设置为中文模式
                mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//设置普通话模式
                mStringBuilder = new StringBuilder();
                mDialog.setListener(new RecognizerDialogListener() {
                    @Override
                    public void onResult(RecognizerResult recognizerResult, boolean b) {
                        String resultString = recognizerResult.getResultString();
                        String content= parseData(resultString);
                        mStringBuilder.append(content);
                        if(b){
                            String result = mStringBuilder.toString();
                            edit_text_shibieneirong.setText(result);
                            System.out.println(result);
                            String anwser="张学雷最帅";
                            if(result.contains("你好")){
                                anwser="你好,我是你的智能语音助手,很高兴为你服务";
                            }else if(result.contains("打开蓝牙")){
                                anwser="正在打开蓝牙";
                                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                                        .getDefaultAdapter();
                                if (mBluetoothAdapter == null) {
                                    Toast.makeText(sousuo.this, "本机没有找到蓝牙硬件或驱动！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                if (!mBluetoothAdapter.isEnabled()) {
                                    Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                    startActivityForResult(mIntent, 1);
                                }

                            }else if(result.contains("今天星期几")){
                                anwser="今天星期八";
                            }else if(result.contains("打开设置")){
                                startActivity(new Intent(Settings.ACTION_SETTINGS));

                            }else if(result.contains("帅哥")){
                                String [] anwserList=new String[]{"对你就是个大帅哥","我主人最帅了","帅！帅！帅！","帅哥　你好呦！"};
                                int random = (int)(Math.random() * anwserList.length);
                                anwser=anwserList[random];
                            }
                            shuo(anwser);
                        }

                    }

                    @Override
                    public void onError(SpeechError speechError) {
                        System.out.println("出错喽 " + speechError);
                    }
                });

                mDialog.show();
            }
        });


    }

    /**
     * 把文字转换为声音
     *
     * @param view
     */
    public void Talk(View view) {
        shuo("至远教育,让你月薪过万不是梦");
    }

    public void shuo(String result){
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(this, null);
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        // 设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端,这些功能用到了讯飞服务器,所以要有网络
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        // 3.开始合成,第一个参数就是转换成声音的文字,自定义,第二个参数就是合成监听器对象,我们不需要对声音有什么特殊处理,就传null
        mTts.startSpeaking(result, null);
    }

    private String parseData(String resultString){
        //创建gson对象.记得要关联一下gson.jar包,方可以使用
        Gson gson = new Gson();
        //参数1 String类型的json数据   参数2.存放json数据对应的bean类
        XFBean xfBean = gson.fromJson(resultString, XFBean.class);
        //创建集合,用来存放bean类里的对象
        ArrayList<XFBean.WS> ws=xfBean.ws;
        //创建一个容器,用来存放从每个集合里拿到的数据,使用StringBUndle效率高
        StringBuilder stringBuilder = new StringBuilder();
        for (XFBean.WS w : ws) {
            String text= w.cw.get(0).w;
            stringBuilder.append(text);
        }
        //把容器内的数据转换为字符串返回出去
        return stringBuilder.toString();
    }


}
