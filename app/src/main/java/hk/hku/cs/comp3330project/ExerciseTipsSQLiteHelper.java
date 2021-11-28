package hk.hku.cs.comp3330project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class ExerciseTipsSQLiteHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "Exercise";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Exercise_tips";
    private static final String COL_exId = "exId";
    private static final String COL_tipNo = "tipNo";
    private static final String COL_tips = "tips";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COL_exId + " INTEGER NOT NULL," +
                    COL_tipNo + " INTEGER NOT NULL," +
                    COL_tips + " TEXT NOT NULL," +
                    "PRIMARY KEY (" + COL_exId + ", " + COL_tipNo +")" +
                    " ); ";

    public ExerciseTipsSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        String[][] tips = {{"Sit on the floor with your feet in front of you and hands behind you. Your fingers can be pointed towards the side or behind you. With your feet on the floor, lift your hips skyward. Pause when your body is parallel with the floor.", "Focusing on contracting the core first, bring your right hand up while simultaneously lifting your left leg up. Touch your right hand to your left toes.", "Slowly return to the elevated position and switch sides. Bring your left hand to your right toes. Keep alternating back and forth."}, {"Stand with feet hip-width apart"}};
        int exerciseId = 0;
        for (String[] tip: tips) {
            int tipNumber = 0;
            for (String sentence: tip) {
                ContentValues values = new ContentValues();
                values.put(COL_exId, exerciseId);
                values.put(COL_tipNo, tipNumber);
                values.put(COL_tips, sentence);

                db.insert(TABLE_NAME, null, values);
                tipNumber++;
            }
            exerciseId++;
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<String> getAllTip(int exerciseId) {
        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT tips FROM Exercise_tips WHERE date(record_datetime) <= ? AND date(record_datetime) >= ? ORDER BY record_datetime ", new String[]{toDate, fromDate});
        Cursor cursor = db.rawQuery("SELECT tips FROM Exercise_tips WHERE exId = ? ORDER BY tipNo ", new String[] {Integer.toString(exerciseId)});
//        Cursor cursor = db.rawQuery("SELECT tips FROM Exercise_tips ORDER BY tipNo ", null);
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

}
