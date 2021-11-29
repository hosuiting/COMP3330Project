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
        String[][] tips = {
                {"Sit on the floor with your feet in front of you and hands behind you. Your fingers can be pointed towards the side or behind you. With your feet on the floor, lift your hips skyward. Pause when your body is parallel with the floor.", "Focusing on contracting the core first, bring your right hand up while simultaneously lifting your left leg up. Touch your right hand to your left toes.", "Slowly return to the elevated position and switch sides. Bring your left hand to your right toes. Keep alternating back and forth."},
                {"Brace your upper back and abdominals and take a good breath in","Exhale as you hinge at the hips, sending the hips backward and upper body forward. Stop once your upper body is nearly parallel with the floor.","Inhale as you return to the starting position."},
                {"Stand with your feet hip-width apart. Lift up your left knee to your chest.","Switch to lift your right knee to your chest. Continue the movement, alternating legs and moving at a sprinting or running pace."},
                {"Begin standing at the back edge of the mat.","Hinge your body forward and walk your hands out in front of you along the floor, keeping your feet in place.","Continue walking your arms out past the plank position. Your arms should be above your head and your feet should still be at the back of the mat.","Start walking your feet toward your hands in small steps.","Continue inching your body up until your feet meet your palms, then repeat the movement."},
                {"Extend one leg from the hip so that your foot lifts off the mat a few inches. Your foot can point softly as it is released from the mat. As you extend your leg, your hip will lift slightly, but the challenge is to keep the rest of your body stable in plank position. This requires extra work from your abdominals, shoulders, and back.","Return your foot to the mat and extend the other leg.","Repeat the lift five to seven times on each side."},
                {"Step forward with your right foot, lowering your body into a basic lunge position. Don't let your right knee extend past the toes.","Twist your upper body to the right from your midsection. Keep your core engaged, squeeze your glutes, and be careful to not rotate your knee.","Bring your arms back to the center in a slow, controlled movement.","Step the right foot back and return to the starting position."},
                {"Root into your sit bones as you lift your feet from the floor, keeping your knees bent.","Elongate and straighten your spine at a 45-degree angle from the floor, creating a V shape with your torso and thighs.","Reach your arms straight out in front, interlacing your fingers or clasping your hands together.","Use your abdominals to twist to the right, then back to center, and then to the left."},
                {"Bend your left knee into a half-squat. Keep your right leg straight and flex your foot so that your toes leave the floor so you are rooting into the right heel.","Press hips back while keeping the spine lifted. Root into your feet so that your body is lifted, instead of sinking.","There are a lot of options for arm variations. Keep your hands on the floor if you need them for balance (on a block if that is helpful). Otherwise, try bending your elbows and bring your hands into anjali mudra \u200B(palms together) with the left elbow inside the left knee in a kind of half Garland Pose (Malasana). Or, extend arms out wide.","Drop your hands to the floor for support and shift to the other side."},
                {"Fold your hands and rest on your left cheek.","Bend your right knee and bring it in line with your hip, then bring your ankle in line with your knee","One of my favourite relaxing poses, feel your groin releasing and enjoy this sweet surrender pose."},
                {"Begin lowering your body by bending your elbows, keeping your core tight and your back flat, until your chest grazes the floor.","Keep your elbows tucked in toward your body. Extend your elbows and push your body back up, using your triceps and chest."}
        };
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
