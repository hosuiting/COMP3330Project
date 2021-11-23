package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ExerciseSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_select);
    }

    public void Imagebuttonclicked(View view) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();
    }
}