package com.yborisjuk.labmessaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	// Users Tables
	public static final String KEY_ROWID = "id";
	public static final int COL_ROWID = 0;

	public static final String KEY_NAME = "name";
	public static final int COL_NAME = 1;

	public static final String KEY_LAST_NAME = "last_name";
	public static final int COL_LASTN_AME = 2;

	public static final String KEY_AGE = "age";
	public static final int COL_AGE = 3;

	public static final String KEY_PHONE_NUMBER = "phone_number";
	public static final int COL_PHONE_NUMBER = 4;
	
	
	public static final int COL_SMS_PHONE_NUMBER = 1;
	
	public static final String KEY_SMS_BODY = "sms_body";
	public static final int COL_SMS_BODY = 2;

	private static final String KEY_DATE = "createdAt";
	public static final int COL_DATE = 3;

	// Database name
	public static final String DATABASE_NAME = "contacts";

	// Table name
	public static final String TABLE_USERS = "users";
	public static final String TABLE_MESSAGES = "messages";

	// Database version
	public static final int DATABASE_VERSION = 1;

	// query tag
	public static final String CREATE_USERS_TABLE = "CREATE TABLE "
			+ TABLE_USERS + "(" + KEY_ROWID
			+ " integer primary key autoincrement, " + KEY_NAME
			+ " text not null, " + KEY_LAST_NAME + " text not null, " + KEY_AGE
			+ " text not null, " + KEY_PHONE_NUMBER + " text not null" + ");";
	
	public static final String CREATE_SMS_TABLE = "CREATE TABLE "
			+ TABLE_MESSAGES + "(" + KEY_ROWID
			+ " integer primary key autoincrement, " + KEY_PHONE_NUMBER
			+ " text not null, " + KEY_SMS_BODY + " text not null, " + KEY_DATE
			+ " text not null" + ");";

	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase sqlDB;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(ctx);

	}

	public DBAdapter open() {
		sqlDB = myDBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		myDBHelper.close();
	}

	/**
	 * ALL CRUD (Create, Read, Update, Delete) Operations
	 * 
	 * @author Yura
	 */

	/**
	 * Adding new user
	 */
	public long addUser(String name, String lastName, String age,
			String phoneNumber) {

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_LAST_NAME, lastName);
		values.put(KEY_AGE, age);
		values.put(KEY_PHONE_NUMBER, phoneNumber);
		return sqlDB.insert(TABLE_USERS, null, values);

	}

	// get user from database
	public Cursor getAllUsers() {

		Cursor cursor = sqlDB.query(TABLE_USERS, new String[] {KEY_ROWID,
				KEY_NAME, KEY_LAST_NAME, KEY_AGE, KEY_PHONE_NUMBER}, null,
				null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public long storeMessage(String phoneNumber, String smsBody) {
		
		ContentValues values = new ContentValues();
		values.put(KEY_PHONE_NUMBER, phoneNumber);
		values.put(KEY_SMS_BODY, smsBody);
		values.put(KEY_DATE, getDateTime());
		return sqlDB.insert(TABLE_MESSAGES, null, values);
		
	}
	
	public Cursor getSMSHistory() {

		Cursor cursor = sqlDB.query(TABLE_MESSAGES, new String[] {KEY_ROWID,
				KEY_PHONE_NUMBER, KEY_SMS_BODY, KEY_DATE}, null,
				null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_USERS_TABLE);
			db.execSQL(CREATE_SMS_TABLE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Destroy old database:
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);

			// Recreate new database:
			onCreate(db);

		}

	}
}
