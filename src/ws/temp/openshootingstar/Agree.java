package ws.temp.openshootingstar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class Agree extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_agree);

		findViewById(R.id.agree_agree).setOnClickListener(this);
		findViewById(R.id.agree_not_agree).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.agree_agree:
			// “¯ˆÓ
			goodEnd();
			break;
		case R.id.agree_not_agree:
			// “¯ˆÓ‚µ‚È‚¢
			badEnd();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			badEnd();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void badEnd() {
		setResult(-1, new Intent());
		finish();
	}

	private void goodEnd() {
		setResult(2, new Intent());
		finish();
	}
}
