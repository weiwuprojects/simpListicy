package wuweiprojects.com.simplisticy;

/**
 * Created by cat on 3/29/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class StringDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 49;
    private static final String DATABASE_NAME = "StringDB";
    private static final String TABLE_STRING = "String";
    private static final String text = "text";
    private static final String CREATE_TABLE_STRING =
            "CREATE TABLE " + TABLE_STRING + " (" +
                    text + " TEXT PRIMARY KEY );";

    StringDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STRING);
        // create new tables
        onCreate(db);
    }

    public long addString(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(text, s);
        // insert row
        long stringID = db.insert(TABLE_STRING, null, value);
        db.close();
        return stringID;
    }

    public void deleteString(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STRING + " WHERE "+ text + " = '" + s +"'");
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_STRING);
        db.close();
    }

    public ArrayList<String> arrayGenerate(){
        ArrayList<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM String";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try
        {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    //the .getString(int x) method of the cursor returns the column
                    //of the table your query returned
                    String s = cursor.getString(0);
                    result.add(s);
                } while (cursor.moveToNext());
            }
        }
        catch (SQLiteException e)
        {
            Log.d("SQL Error", e.getMessage());
            return null;
        }
        finally
        {
            //release all your resources
            cursor.close();
            db.close();
        }
        return result;
    }
}
