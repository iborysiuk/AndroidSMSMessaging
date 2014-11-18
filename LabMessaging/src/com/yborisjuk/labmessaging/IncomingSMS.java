package com.yborisjuk.labmessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class IncomingSMS extends BroadcastReceiver {

	// Get the object of SmsManager
	final SmsManager smsManager = SmsManager.getDefault();
	private DBAdapter db;

	@Override
	public void onReceive(Context context, Intent intent) {
		// Retrieves a map of extended data from the intent
		final Bundle bundle = intent.getExtras();
		db = new DBAdapter(context);
		try {
			if (bundle != null) {
				final Object[] objects = (Object[]) bundle.get("pdus");

				for (int i = 0; i < objects.length; i++) {
					SmsMessage message = SmsMessage
							.createFromPdu((byte[]) objects[i]);
					String phoneNumber = message.getDisplayOriginatingAddress();
					String msgBody = message.getDisplayMessageBody();
					/*Toast.makeText(
							context,
							"senderNumber: " + phoneNumber + ", message: "
									+ msgBody, Toast.LENGTH_LONG).show();*/
					db.open();
						db.storeMessage(phoneNumber, msgBody);
					db.close();
					Toast.makeText(context, "sms stored in database",
							Toast.LENGTH_SHORT).show();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
