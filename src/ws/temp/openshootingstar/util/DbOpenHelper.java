package ws.temp.openshootingstar.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
    private DbListner dbListner;

    public DbOpenHelper(Context context, String db_name, int db_version){
        super(context, db_name, null, db_version);
    }

    public void setDbListner(DbListner dbListner){
        this.dbListner = null;
        this.dbListner = dbListner;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        this.dbListner.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        this.dbListner.onUpgrade(db, oldVersion, newVersion);
    }

    public interface DbListner {
        public void onCreate(SQLiteDatabase db);

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }
}
