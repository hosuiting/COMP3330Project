package hk.hku.cs.comp3330project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.lang.Math;

public class BodyStatSQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "TravelSpots";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Body_history";
    private static final String COL_id = "id";
    private static final String COL_height = "height";
    private static final String COL_weight = "weight";
    private static final String COL_bmi = "bmi";
    private static final String COL_body_fat = "body_fat";
    private static final String COL_record_datetime = "record_datetime";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COL_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_height + " DOUBLE NOT NULL, " +
                    COL_weight + " DOUBLE, " +
                    COL_bmi + " DOUBLE, " +
                    COL_body_fat + " DOUBLE, " +
                    COL_record_datetime + " DATETIME DEFAULT (datetime('now','localtime')) ); ";
//    public BodyStatSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
    public BodyStatSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    /**
     * Get latest row in the database
     */
    public String[] getLatestStatistics() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Body_history ORDER BY record_datetime DESC LIMIT 1", null);

        String[] record = new String[5];
        while (cursor.moveToNext()) {
            for (int i = 1; i < 6; ++i) {
                record[i-1] = cursor.getString(i);
            }
        }
        cursor.close();
        return record;
    }

    /**
     * Get max 5 latest row in the database
     */
    public ArrayList<String> get5LatestStatistics() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT weight FROM Body_history ORDER BY record_datetime DESC LIMIT 5", null);
        ArrayList<String> records = new ArrayList<>();
        while (cursor.moveToNext()) {
//            String[] record = new String[5];
//            for (int i = 1; i < 6; ++i) {
//                record[i-1] = cursor.getString(i);
//            }
//            records.add(record);
            records.add(0, cursor.getString(0));
        }
        cursor.close();
        return records;
    }

    public ArrayList<String> getRecordFromDateRange(String fromDate, String toDate) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT weight FROM Body_history WHERE date(record_datetime) <= ? AND date(record_datetime) >= ? ORDER BY record_datetime ", new String[]{toDate, fromDate});
        ArrayList<String> records = new ArrayList<>();
        while (cursor.moveToNext()) {
//            String[] record = new String[5];
//            for (int i = 1; i < 6; ++i) {
//                record[i-1] = cursor.getString(i);
//            }
//            records.add(record);
            records.add(cursor.getString(0));
        }
        cursor.close();
        return records;
    }


    /**
     * Use this function to add statistics to database
     * @param userInput [height, weight, body_fat]
     * @return -1 when fail
     */
    public long addStatistics(String[] userInput) {
        Double weight = Double.parseDouble(userInput[0]) / 100.0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_height, userInput[0]);
        values.put(COL_weight, userInput[1]);
        values.put(COL_bmi, Math.round(Double.parseDouble(userInput[1])/(weight*weight)* 100.0) / 100.0);
        values.put(COL_body_fat, userInput[2]);
        return db.insert(TABLE_NAME, null, values);
    }
    public long addStatfromRegister(String height, String weight, String bodyfat){
        Double height1 = Double.parseDouble(height);
        Double weight1 = Double.parseDouble(weight);
        Double bodyfat1 = Double.parseDouble(bodyfat);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_height, height1);
        values.put(COL_weight, weight1);
        values.put(COL_bmi, Math.round(weight1*100/(height1*height1))/100);
        values.put(COL_body_fat,bodyfat1);
        System.out.println("TEST");
        return db.insert(TABLE_NAME,null,values);

    }
}
