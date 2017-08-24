package zhangxuelei1506d.wangyi;

import java.util.ArrayList;

/**
 * date:2016/11/3
 * author:易宸锋(dell)
 * function:用来存放讯飞语音的识别结果的JSON数据
 */
public class XFBean {
    public ArrayList<WS> ws;
    public class WS{
        public ArrayList<CW> cw;
    }
    public class CW{
        public String w;
    }

}
