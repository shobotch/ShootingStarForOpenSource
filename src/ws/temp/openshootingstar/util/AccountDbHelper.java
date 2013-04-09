package ws.temp.openshootingstar.util;

import java.util.ArrayList;
import java.util.List;

import ws.temp.openshootingstar.util.DbOpenHelper.DbListner;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AccountDbHelper {
    public final String          TAG                       = "AccountDbHelper";

    private DbOpenHelper         helper;
    private SQLiteDatabase       db;

    public static final String   DB_NAME                   = "preference.db";
    public static final String   DB_TABLE                  = "account";
    public static final int      DB_VERSION                = 1;

    public static final String   COLUM_C_ID                = "c_id";
    public static final String   COLUM_ACCOUNT_TYPE        = "a_type";
    public static final String   COLUM_ID                  = "a_id";
    public static final String   COLUM_SCREEN_NAME         = "a_screen_name";
    public static final String   COLUM_CONSUMER_KEY        = "a_consumer_key";
    public static final String   COLUM_CONSUMER_SECRET     = "a_consumer_secret";
    public static final String   COLUM_ACCESS_TOKEN        = "a_access_token";
    public static final String   COLUM_ACCESS_TOKEN_SECRET = "a_access_token_secret";

    public static final String[] COLUMNS                   = {
                                                           COLUM_C_ID,
                                                           COLUM_ACCOUNT_TYPE,
                                                           COLUM_ID,
                                                           COLUM_SCREEN_NAME,
                                                           COLUM_CONSUMER_KEY,
            COLUM_CONSUMER_SECRET,
            COLUM_ACCESS_TOKEN,
            COLUM_ACCESS_TOKEN_SECRET                     };

    public static class AccountTable {
        public long   c_id;                 // �A�J�E���g�̃��[�J�����ł�id
        public int    a_type;               // �A�J�E���g�̎�� (0:twitter, 1:ADN)
        public long   a_id;                 // Account��ID(Twitter�Ō���user_id�Ȃ�)
        public String a_screen_name;        // Account��Screen Name

        // ������ւ�̓R���V���[�}�[�L�[�U���@�\�̂��߂ł��B�����ăe�[�u���͕����Ă��܂���B
        public String a_consumer_key;       // consumer_key
        public String a_consumer_secret;    // consumer_secret

        public String a_access_token;       // access token
        public String a_access_token_secret; // access token secret
    }

    private static final String DB_CREATE          = "CREATE TABLE "
                                                           + DB_TABLE
                                                           + " ( c_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                           + "  a_type TEXT"
                                                           + "  a_id INTEGER,"
                                                           + "  a_screen_name TEXT,"
                                                           + "  a_consumer_key TEXT,"
                                                           + "  a_consumer_secret TEXT,"
                                                           + "  a_access_token TEXT,"
                                                           + "  a_access_token_secret TEXT"
                                                           + " );";

    private static final String DB_DROP            = "DROP TABLE IF EXISTS "
                                                           + DB_TABLE;

    private static final String DB_COUNT_ACCOUNT_D = "SELECT"
                                                           + " COUNT(DISTINCT "
                                                           + COLUM_ID
                                                           + ") FROM "
                                                           + DB_TABLE + ";";

    private static final String DB_COUNT_ACCOUNT   = "SELECT" + " COUNT( "
                                                           + COLUM_ID
                                                           + ") FROM "
                                                           + DB_TABLE + ";";

    public AccountDbHelper(Context context){
        helper = new DbOpenHelper(context, DB_NAME, DB_VERSION);
        helper.setDbListner(getDbListner());
        this.db = helper.getWritableDatabase();
    }

    public DbListner getDbListner(){
        return new DbListner(){

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion,
                    int newVersion){
                // TODO �e�[�u���̃o�[�W�����ύX���ɓǂݏo�����B�����_�ł͖����H
                Log.d(TAG, "�ύX�Ȃ�I�V: " + String.valueOf(newVersion) + " ��: "
                        + String.valueOf(oldVersion));
                db.execSQL(DB_DROP);
                db.execSQL(DB_CREATE);
            }

            @Override
            public void onCreate(SQLiteDatabase db){
                try{
                    db.execSQL(DB_CREATE);
                } catch(SQLException e){
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                    Log.e(TAG, "�e�[�u���̍쐬�Ɏ��s���܂���");
                    return;
                }
                Log.d(TAG, "�e�[�u����V�K�쐬���܂����B");
            }
        };
    }

    public void close(){
        db.close();
    }

    public List<AccountTable> getAccountList(){
        List<AccountTable> list = new ArrayList<AccountDbHelper.AccountTable>();
        Cursor cursor = db.query(DB_TABLE, COLUMNS, null, null, null, null,
                COLUM_C_ID);
        while(cursor.moveToNext()){
            AccountTable entry = new AccountTable();

            entry.c_id = cursor.getLong(0);
            entry.a_type = cursor.getInt(1);
            entry.a_id = cursor.getLong(2);
            entry.a_screen_name = cursor.getString(3);
            entry.a_consumer_key = cursor.getString(4);
            entry.a_consumer_secret = cursor.getString(5);
            entry.a_access_token = cursor.getString(6);
            entry.a_access_token_secret = cursor.getString(7);

            list.add(entry);
        }
        return list;
    }

    public int countAccount(Boolean distinct){
        Cursor query = null;
        if(distinct){
            query = db.rawQuery(DB_COUNT_ACCOUNT_D, null);
        } else{
            query = db.rawQuery(DB_COUNT_ACCOUNT, null);
        }
        query.moveToFirst();
        int count = query.getInt(0);
        Log.i(TAG, "�A�J�E���g��: " + String.valueOf(count));
        return count;
    }

    public int countAccount(){
        return countAccount(false);
    }

    public void dropAllAccount(){
        db.execSQL(DB_DROP);
        db.execSQL(DB_CREATE);
    }

    public void addAccount(BaseDataObject dataObject)
            throws NullPointerException{
        ContentValues values = new ContentValues();
        values.put(COLUM_ID, dataObject.user_id);
        values.put(COLUM_ACCOUNT_TYPE, dataObject.account_type);
        values.put(COLUM_SCREEN_NAME, dataObject.screen_name);
        values.put(COLUM_CONSUMER_KEY, dataObject.consumerKey);
        values.put(COLUM_CONSUMER_SECRET, dataObject.consumerKeySecret);
        values.put(COLUM_ACCESS_TOKEN, dataObject.accessToken);
        values.put(COLUM_ACCESS_TOKEN_SECRET, dataObject.accessTokenSecret);
        db.insert(DB_TABLE, null, values);
    }
}
