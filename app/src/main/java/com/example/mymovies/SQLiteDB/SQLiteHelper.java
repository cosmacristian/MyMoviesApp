package com.example.mymovies.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mymovies.Models.User;

public class SQLiteHelper extends SQLiteOpenHelper {
    //DATABASE NAME
    public static final String DATABASE_NAME = "mymoviesapp";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //COLUMN imageblob
    public static final String KEY_IMAGE = "image";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT,"
            +KEY_IMAGE + " TEXT"
            + " );";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override  //TODO: Add here new tables when needed
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        //Put imageURI in  @values
        values.put(KEY_IMAGE, user.imageURI);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_IMAGE},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {

            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }
}
