package com.yborisjuk.labmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	private EditText inputName, inputLastName, inputAge, inputPhoneNumber;
	private Button btn_new_register;
	private DBAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		inputName = (EditText) findViewById(R.id.edtxt_name);
		inputLastName = (EditText) findViewById(R.id.edtxt_lastname);
		inputAge = (EditText) findViewById(R.id.edtxt_age);
		inputPhoneNumber = (EditText) findViewById(R.id.edtxt_phnum);
		btn_new_register = (Button) findViewById(R.id.btn_signup);

		db = new DBAdapter(getApplicationContext());
		btn_new_register.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			String name = inputName.getText().toString();
			String lastName = inputLastName.getText().toString();
			String age = inputAge.getText().toString();
			String phoneNumber = inputPhoneNumber.getText().toString();
			db.open();
			db.addUser(name, lastName, age, phoneNumber);
			db.close();

			Intent activityIntent = new Intent(getApplicationContext(),
					ContactChooserActivity.class);
			activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(activityIntent);
			finish();
		}
	};
}
