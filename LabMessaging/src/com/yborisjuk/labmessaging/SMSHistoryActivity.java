package com.yborisjuk.labmessaging;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SMSHistoryActivity extends Activity {

	private ListView lv_history;
	private ContactsAdapter contactsAdapter;
	private String array_body[];
	private String array_phone[];
	private DBAdapter db;
	private ContactsBook objAdapter;
	private List<ContactsBook> contactsBooks = new ArrayList<ContactsBook>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smshistory);
		
		lv_history = (ListView) findViewById(R.id.ls_history);
		db = new DBAdapter(getApplicationContext());
		
		db.open();
		Cursor cursor = db.getSMSHistory();
		int k = cursor.getCount();
		Log.d("Cursor", "cursor" + k);
		
		array_body = new String[cursor.getCount()];
		array_phone = new String[cursor.getCount()];
		int sms = 0;
		int phone = 0;
			if (cursor.moveToFirst()) {
				do {
					
					array_phone[phone++] = cursor.getString(DBAdapter.COL_SMS_PHONE_NUMBER);
					array_body[sms++] = cursor.getString(DBAdapter.COL_SMS_BODY);
					
				} while (cursor.moveToNext());
			}			
		cursor.close();
		db.close();
		
		//contanctslist.clear();
		
		for (int i = 0; i < array_body.length; i++) {
			objAdapter = new ContactsBook(array_body[i],array_phone[i]);
			contactsBooks.add(objAdapter);
		}
		
		contactsAdapter =  new ContactsAdapter(getApplicationContext(), R.layout.contacts_list, contactsBooks, array_phone, array_body, db);
		lv_history.setAdapter(contactsAdapter);
		
		lv_history.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long index) {
				String phone = contactsBooks.get(position).getPhoneNumber();
				String body = contactsBooks.get(position).getName();
				Intent sms = new Intent(getApplicationContext(), DetailHistoryActivity.class);
				sms.putExtra("phone", phone);
				sms.putExtra("msg_body", body);
				sms.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(sms);
				startActivityForResult(sms, 1);
				finish();
				
			}
		});
	}
}