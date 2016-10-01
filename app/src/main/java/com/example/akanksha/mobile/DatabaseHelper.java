package com.example.akanksha.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by akanksha on 9/27/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "student.db";
    public static final String T_NAME = "student";
    public static final String COL_1 = "roll_no";
    public static final String COL_2 = "name";
    public static final String COL_3 = "marks";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table student ( "+"roll_no INTEGER," + "name TEXT," + "marks INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS"+T_NAME);
        onCreate(db);

    }

    public boolean insertData(String roll, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, roll);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, marks);
        long result = db.insert(T_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor viewData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from student",null);
        return res;
    }

    public int deleteData(String roll)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(T_NAME,"roll_no = ?",new String[] {roll});
    }

    public boolean upData(String roll, String name, String marks)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, roll);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, marks);
        db.update(T_NAME,contentValues,"roll_no = ?",new String[] {roll});
        return true;
    }


}
