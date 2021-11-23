package hk.hku.cs.comp3330project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private Button toCaloriesCal;
    private Button toExerciseSelect;
    private Button toChatroom;
    private Button toBody;
    private TextView Greet;
    private Button toRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                        break;
                    case R.id.profile:
                        startActivity(new Intent(HomeActivity.this,RegisterActivity.class));
                        break;
                    case R.id.chatbot:
                        startActivity(new Intent(HomeActivity.this,ChatbotActivity.class));
                        break;
                }

                return false;
            }
        });

        Greet = (TextView)findViewById(R.id.Greet);
        SharedPreferences mydata = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        Greet.setText("Hi "+mydata.getString("username",""));
        toRegister=(Button)findViewById(R.id.toRegister);
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,RegisterActivity.class));
            }
        });
        toCaloriesCal=(Button) findViewById(R.id.toCaloriesCal);
        toCaloriesCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Date date = Calendar.getInstance().getTime();
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String date_string = dateFormat.format(date);
//
//                Intent i=new Intent(HomeActivity.this,DailyCaloriesActivity.class);
//                i.putExtra("date",date_string);
//                startActivity(i);
                startActivity(new Intent(HomeActivity.this,CalendarActivity.class));
            }
        });
        toChatroom=(Button) findViewById(R.id.goChatroom);
        toChatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ChatbotActivity.class));
            }
        });
        toBody=(Button) findViewById(R.id.toBody);
        toBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,BodyActivity.class));
            }
        });

        findViewById(R.id.toEcercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ExerciseActivity.class));
            }
        });
        toExerciseSelect = findViewById(R.id.toExerciseSelect);
        toExerciseSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ExerciseSelectActivity.class));
            }
        });
    }
}