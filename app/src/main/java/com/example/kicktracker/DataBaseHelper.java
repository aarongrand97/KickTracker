package com.example.kicktracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String SESSIONS_TABLE = "SESSIONS_TABLE";
    public static final String COLUMN_SESSION_NAME = "SESSION_NAME";
    public static final String COLUMN_SESSION_ARRAY = "SESSION_ARRAY";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(Context context) {
        super(context, "kickSessions.db", null, 1);
    }

    //this is called the first time the database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SESSIONS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SESSION_NAME + " TEXT, " + COLUMN_SESSION_ARRAY + " TEXT)";

        db.execSQL(createTableStatement);
    }

    // used for when the version of the application is updated
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    // Add a session to the database
    public boolean addOne(String sessionName, String sessionArray){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SESSION_NAME, sessionName);
        cv.put(COLUMN_SESSION_ARRAY, sessionArray);

        long insert = db.insert(SESSIONS_TABLE, null, cv);
        if(insert == -1)
            return false;
        else
            return true;
    }

    // Retrieve all names of sessions
    public List<String> getSessionNames(){
        List<String> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + SESSIONS_TABLE;

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            // loop through the results, store for the returnList
            do{
                returnList.add(cursor.getString(1));
            } while(cursor.moveToNext());
        }
        else{
            // empty database dont worry about it
        }
        cursor.close();
        db.close();

        return returnList;
    }

    // Gets a specific session array by its name
    public int[][][] getSessionArray(String sessionName){
        String returnString = "";

        String queryString = "SELECT " + COLUMN_SESSION_ARRAY + " FROM " + SESSIONS_TABLE + " WHERE " + COLUMN_SESSION_NAME + " = " + "'" + sessionName + "'";

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            // loop through the results, store for the returnList
                returnString = cursor.getString(0);
            }
        else{
            // empty database dont worry about it
        }

        cursor.close();
        db.close();

        return convertSessionStringToArray(returnString);
    }

    // Creates a combined array of all sessions
    public int[][][] getOverallStats(){
        int[][][] returnArray = new int[7][10][2];

        String queryString = "SELECT * FROM " + SESSIONS_TABLE;

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            // loop through the results, store for the returnList
            do{
                addArrayString(cursor.getString(2), returnArray);
            } while(cursor.moveToNext());
        }
        else{
            // empty database dont worry about it
        }
        cursor.close();
        db.close();

        return returnArray;
    }

    // Adds an array in its stored string form to an actual array
    private void addArrayString(String arrayString, int[][][] returnArray){
        int[][][] sessionArray = convertSessionStringToArray(arrayString);
        for(int row = 0; row != 7; row++){
            for(int col = 0; col != 10; col++){
                for(int dep = 0; dep != 2; dep++){
                    returnArray[row][col][dep] = returnArray[row][col][dep] + sessionArray[row][col][dep];
                }
            }

        }
    }

    // Converts the stored form of the array to a usable form
    private int[][][] convertSessionStringToArray(String sessionString){
        int[][][] returnArray = new int[7][10][2];

        for(int i = 0; i != sessionString.length(); i++){
            int row = (int)Math.floor(i/20);
            int col = (int)Math.floor((i%20)/2);
            if(i%2 == 0){
                returnArray[row][col][0] = Character.getNumericValue(sessionString.charAt(i));
            }
            else{
                returnArray[row][col][1] = Character.getNumericValue(sessionString.charAt(i));
            }
        }

        return returnArray;

    }

    private String sessionArrayToString(int [][][] sessionArray){
        String arrayString = "";
        for(int row = 0; row != 7; row++){
            for(int col = 0; col != 10; col++) {
                arrayString = arrayString + sessionArray[row][col][0];
                arrayString = arrayString + sessionArray[row][col][1];
            }
        }
        return arrayString;
    }
}
