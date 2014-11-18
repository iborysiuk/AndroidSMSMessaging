package com.yborisjuk.labmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailHistoryActivity extends Activity {
	private TextView getPhone, getMsgBody;
	private Button btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_history);
		
		getPhone = (TextView) findViewById(R.id.txt_sender_phone);
		getMsgBody = (TextView) findViewById(R.id.txt_sms_body);
		btn_back = (Button) findViewById(R.id.btn_back);
		
		String setPhone = getIntent().getStringExtra("phone");
		String setSmsBody = getIntent().getStringExtra("msg_body");
		
		getPhone.setText("tel: " + setPhone);
		getMsgBody.setText(setSmsBody);
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), SMSHistoryActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				
			}
		});
	}
}
