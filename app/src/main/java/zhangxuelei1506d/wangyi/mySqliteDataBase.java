package zhangxuelei1506d.wangyi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * date 2017/8/11
 * author:张学雷(Administrator)
 * functinn:
 */

public class mySqliteDataBase extends SQLiteOpenHelper{
    public mySqliteDataBase(Context context) {
        super(context, "online.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table lixian(id integer primary key autoincrement,title text,data text,thumbnail_pic_s text,thumbnail_pic_s02 text,thumbnail_pic_s03 text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
