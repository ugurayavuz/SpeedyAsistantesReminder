package com.taylan.speedyasistantes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Task_Informations";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER primary key, reminderName TEXT, reminderTime TEXT, reminderDate TEXT, categoryName TEXT)";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean notificationInsert(String x, String z, String y, String w) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("reminderName", x);
        contentValues.put("reminderTime", z);
        contentValues.put("reminderDate", y);
        contentValues.put("categoryName", w);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor fetchItem(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor fItem = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null);
        return fItem;
    }
    public Cursor fetchItem2(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor fItem = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE ID = '" + ID + "'", null);
        return fItem;
    }
    public Cursor fetchListItems(String remName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ID FROM " + TABLE_NAME +
                " WHERE reminderName = '" + remName + "'";
        Cursor fListItem = db.rawQuery(query, null);
        return fListItem;
    }

    public Cursor getID(String remName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ID FROM " + TABLE_NAME +
                        " WHERE reminderName = '" + remName + "'";
        Cursor number1 = db.rawQuery(query, null);
        return number1;
    }
    public void editTaskDetails(int ID, String newReminderName, String newReminderTime, String newReminderDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET reminderTime = '" + newReminderTime +
                "', reminderDate = '" + newReminderDate + "' WHERE ID = '" + ID + "'" + " AND reminderName = '" + newReminderName + "'";
        db.execSQL(query);
    }
    public void deleteTask(int ID, String TaskName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID = '" + ID + "'" +
                " AND reminderName = '" + TaskName + "'";
        db.execSQL(query);
    }
    public void deleteMainTask(String TaskName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE reminderName = '" + TaskName + "'";
        db.execSQL(query);
    }


}
