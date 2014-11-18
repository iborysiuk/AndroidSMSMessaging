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

public class ContactChooserActivity extends Activity {
	private ListView lv_contacts;
	private ContactsAdapter contactsAdapter;
	private String array_name[];
	private String array_phone[];
	private DBAdapter db;
	private ContactsBook objAdapter;
	private List<ContactsBook> contactsBooks = new ArrayList<ContactsBook>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_chooser);
		
		lv_contacts = (ListView) findViewById(R.id.lv_contact);
		db = new DBAdapter(getApplicationContext());
		
		db.open();
		Cursor cursor = db.getAllUsers();
		
		array_name = new String[cursor.getCount()];
		array_phone = new String[cursor.getCount()];
		int name = 0;
		int phone = 0;
			if (cursor.moveToFirst()) {
				do {
					
					array_name[name++] = cursor.getString(DBAdapter.COL_NAME);
					array_phone[phone++] = cursor.getString(DBAdapter.COL_PHONE_NUMBER);
					
				} while (cursor.moveToNext());
			}			
		cursor.close();
		db.close();
		
		//contanctslist.clear();
		
		for (int i = 0; i < array_name.length; i++) {
			objAdapter = new ContactsBook(array_name[i],array_phone[i]);
			contactsBooks.add(objAdapter);
		}
		
		contactsAdapter =  new ContactsAdapter(getApplicationContext(), R.layout.contacts_list, contactsBooks, array_name, array_phone, db);
		lv_contacts.setAdapter(contactsAdapter);
		
		lv_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long index) {
				
				String phone = contactsBooks.get(position).getPhoneNumber();
				Intent sms = new Intent(getApplicationContext(), SendSMSActivity.class);
				sms.putExtra("phone", phone);
				sms.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(sms);
				startActivityForResult(sms, 1);
				finish();
				
			}
		});
	}
}
