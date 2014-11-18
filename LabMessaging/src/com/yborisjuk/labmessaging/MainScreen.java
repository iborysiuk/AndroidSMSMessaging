package com.yborisjuk.labmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreen extends Activity {

	private Button btn_register, btn_sendtext, btn_history;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);

		btn_register = (Button) findViewById(R.id.btn_register);
		btn_sendtext = (Button) findViewById(R.id.btn_sendtxt);
		btn_history = (Button) findViewById(R.id.btn_sms_history);

		btn_register.setOnClickListener(clickListener);
		btn_sendtext.setOnClickListener(clickListener);
		btn_history.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_register: {
				intent = new Intent(getApplicationContext(),
						RegisterActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
				break;
			case R.id.btn_sendtxt: {
				intent = new Intent(getApplicationContext(),
						ContactChooserActivity.class);
				startActivity(intent);
			}
				break;
			case R.id.btn_sms_history:
				intent = new Intent(getApplicationContext(),
						SMSHistoryActivity.class);
				startActivity(intent);
			}
		}
	};
}
