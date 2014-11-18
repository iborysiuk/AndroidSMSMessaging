package com.yborisjuk.labmessaging;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<ContactsBook> {
	private Context context;
	private List<ContactsBook> contactlist;
	private String[] array_name;
	private String[] array_phone;
	private DBAdapter db;
	private LayoutInflater inflater;
	
	public ContactsAdapter(Context context, int resourceId, List<ContactsBook> contactlist,
			String[] array_name, String[] array_phone,
			DBAdapter db) {
		super(context, resourceId, contactlist);
		this.context = context;
		this.contactlist = contactlist;
		this.array_name = array_name;
		this.array_phone = array_phone;
		this.db = db;
		inflater = LayoutInflater.from(context);
	}

	private class ViewHolder {
		TextView contactName;
		TextView contactPhoneNumber;
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.contacts_list, null);
			holder.contactName = (TextView) view.findViewById(R.id.txt_list_name);
			holder.contactPhoneNumber = (TextView) view.findViewById(R.id.txt_list_phonenumber);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.contactName.setText(contactlist.get(position).getName());
		holder.contactPhoneNumber.setText("tel: " + contactlist.get(position).getPhoneNumber());
		return view;
	}
}
