package ws.temp.openshootingstar;

import ws.temp.openshootingstar.util.Setting;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	public static final String TAG = "Dispatch";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getSharedPreferences("pref", MODE_PRIVATE)
                .getBoolean("firstStartUp", Setting.firstStartUp)){
            Log.d(TAG, "first start up.");
            startAgree();
        } else if(getSharedPreferences("pref", MODE_PRIVATE)
                .getLong("accountLength", 0) == 0){
            Log.d(TAG, "account nothing.");
            startAddAuth(this);
        } else{
            Log.d(TAG, "default start.");
            startDefault();
        }
    }

    private void startAgree(){
        startActivityForResult(new Intent(this, Agree.class), 0);
    }

    private void startAddAuth(final Context context){
        new Builder(this)
                .setTitle("認証するアカウントの種類を選択")
                .setItems(Setting.AuthItems, new OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Log.i(TAG, "Item " + which + " clicked.");
                        startActivityForResult(new Intent(context,
                                AddAuth.class), which);
                    }
                })
                .create()
                .show();
    }

    private void startDefault(){
        // TODO Start Timeline.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){
            case -1:
                // 同意画面にて同意していない
                finish();
                break;
            case 2:
                // 同意画面にて同意
                getSharedPreferences("pref", MODE_PRIVATE)
                        .edit()
                        .putBoolean("firstStartUp", !Setting.firstStartUp)
                        .commit();
                startAddAuth(this);
                break;
            default:
                break;
        }
    }

}
