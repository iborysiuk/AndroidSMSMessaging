package com.yborisjuk.labmessaging;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSActivity extends Activity {
	private EditText getPhoneNumber, inputMsg;
	private Button btn_send;
	private String number;
	private String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_sms);
		
		getPhoneNumber = (EditText) findViewById(R.id.edtxt_sms_phone);
		inputMsg = (EditText) findViewById(R.id.edtxt_sms_body);
		btn_send = (Button) findViewById(R.id.btn_sms_send);
		
		number = getIntent().getStringExtra("phone");	
		getPhoneNumber.setText(number);		
		
		btn_send.setOnClickListener(sendClick);
	}
		OnClickListener sendClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
						
				message = inputMsg.getText().toString();
				
				try {
			           SmsManager smsManager = SmsManager.getDefault();
			           smsManager.sendTextMessage(number, null, message, null, null);
			           Toast.makeText(getApplicationContext(), "SMS sent." + number + " : " + message,
			           Toast.LENGTH_LONG).show();
			        } catch (Exception e) {
			           Toast.makeText(getApplicationContext(),
			           "SMS faild, please try again.",
			           Toast.LENGTH_LONG).show();
			           e.printStackTrace();
			        } 
			}
		};
	
}
