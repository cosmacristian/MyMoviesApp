package com.example.mymovies.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mymovies.Models.MovieDetails;
import com.example.mymovies.Models.MovieResults;
import com.example.mymovies.Models.PageResult;
import com.example.mymovies.Models.User;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    //DATABASE NAME
    public static final String DATABASE_NAME = "mymoviesapp";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 7;

    //TABLE NAME
    public static final String TABLE_USERS = "users";
    public static final String TABLE_FAVMOVIES = "favorites";
    public static final String TABLE_USER_FAVMOVIES = "users_favorites";

    //TABLE FAVMOVIES COLUMNS
    public static final String KEY_MOVIEID = "id";

    //COLUMN user name
    public static final String KEY_MOVIETITLE = "title";

    //COLUMN email
    public static final String KEY_MOVIEDATE = "date";

    //COLUMN email
    public static final String KEY_MOVIEIMAGE = "imageurl";
    //////

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //COLUMN imageurl
    public static final String KEY_IMAGE = "image";

    public static final String KEY_USERID = "userid";
    public static final String KEY_FAVMOVIEID = "movieid";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT,"
            +KEY_IMAGE + " TEXT"
            + " );";

    public static final String SQL_TABLE_FAVMOVIES = " CREATE TABLE " + TABLE_FAVMOVIES
            + " ( "
            + KEY_MOVIEID + " INTEGER PRIMARY KEY, "
            + KEY_MOVIETITLE + " TEXT, "
            + KEY_MOVIEDATE + " TEXT, "
            +KEY_MOVIEIMAGE + " TEXT"
            + " );";

    public static final String SQL_TABLE_USER_FAVMOVIES = " CREATE TABLE " + TABLE_USER_FAVMOVIES
            + " ( "
            + KEY_USERID + " INTEGER, "
            + KEY_FAVMOVIEID + " INTEGER, "
            + "PRIMARY KEY("+KEY_USERID+","+KEY_FAVMOVIEID+")"
            + " );";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override  //TODO: Add here new tables when needed
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_FAVMOVIES);
        sqLiteDatabase.execSQL(SQL_TABLE_USER_FAVMOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_FAVMOVIES);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER_FAVMOVIES);
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_FAVMOVIES);
        sqLiteDatabase.execSQL(SQL_TABLE_USER_FAVMOVIES);
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
        values.put(KEY_IMAGE, "");

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public void addToFavorites(MovieResults movie,User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_MOVIEID, movie.id);

        //Put email in  @values
        values.put(KEY_MOVIETITLE, movie.title);

        //Put password in  @values
        values.put(KEY_MOVIEDATE, movie.release_date);

        //Put imageURI in  @values
        values.put(KEY_MOVIEIMAGE, movie.poster_path);

        // insert row
        long todo_id = db.insert(TABLE_FAVMOVIES, null, values);

        ContentValues values2 = new ContentValues();

        //Put username in  @values
        values2.put(KEY_USERID, user.id);

        //Put email in  @values
        values2.put(KEY_FAVMOVIEID, movie.id);

        // insert row
        long todo_id2 = db.insert(TABLE_USER_FAVMOVIES, null, values2);
    }

    public void addToFavorites(MovieDetails movie,User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_MOVIEID, movie.id);

        //Put email in  @values
        values.put(KEY_MOVIETITLE, movie.title);

        //Put password in  @values
        values.put(KEY_MOVIEDATE, movie.release_date);

        //Put imageURI in  @values
        values.put(KEY_MOVIEIMAGE, movie.poster_path);

        // insert row
        long todo_id = db.insert(TABLE_FAVMOVIES, null, values);

        ContentValues values2 = new ContentValues();

        //Put username in  @values
        values2.put(KEY_USERID, user.id);

        //Put email in  @values
        values2.put(KEY_FAVMOVIEID, movie.id);

        // insert row
        long todo_id2 = db.insert(TABLE_USER_FAVMOVIES, null, values2);
    }

    public boolean deleteFromFavorites(int id,User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVMOVIES, KEY_MOVIEID + "=?", new String[]{Integer.toString(id)});
        return db.delete(TABLE_USER_FAVMOVIES, KEY_MOVIEID + "=? and "+KEY_ID + "=?", new String[]{Integer.toString(id),user.id}) > 0;
    }

    public PageResult getFavorites(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        String QUERY = "SELECT * FROM "+TABLE_USER_FAVMOVIES+" a INNER JOIN "+TABLE_FAVMOVIES+" b ON a."+KEY_FAVMOVIEID+"=b."+KEY_MOVIEID+" WHERE a."+KEY_USERID+"=?";

        Cursor cursor = db.rawQuery(QUERY, new String[]{String.valueOf(user.id)});
        ArrayList<MovieResults> movies = new ArrayList<MovieResults>();
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            MovieResults movie = new MovieResults(cursor.getInt(1), cursor.getString(5), cursor.getString(3), cursor.getString(4));
            movies.add(movie);
            while (cursor.moveToNext()) {
                movie = new MovieResults(cursor.getInt(1), cursor.getString(5), cursor.getString(3), cursor.getString(4));
                movies.add(movie);
            }

        }
        return new PageResult(1,movies.size(),1,movies);
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
