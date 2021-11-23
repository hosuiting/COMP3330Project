package hk.hku.cs.comp3330project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ExerciseSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_select);
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(ExerciseSelectActivity.this,HomeActivity.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(ExerciseSelectActivity.this,RegisterActivity.class));
                        break;
                    case R.id.chatbot:
                        startActivity(new Intent(ExerciseSelectActivity.this,ChatbotActivity.class));
                        break;
                }

                return false;
            }
        });
    }

    public void Imagebuttonclicked(View view) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();
    }
}