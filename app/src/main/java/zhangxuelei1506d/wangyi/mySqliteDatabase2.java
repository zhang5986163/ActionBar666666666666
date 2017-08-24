package zhangxuelei1506d.wangyi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * date 2017/8/17
 * author:张学雷(Administrator)
 * functinn:
 */

public class mySqliteDatabase2 extends SQLiteOpenHelper {

    public mySqliteDatabase2(Context context) {
        super(context, "item.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table item(id integer primary key autoincrement,title text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
