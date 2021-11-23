package hk.hku.cs.comp3330project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DecimalFormat;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = (CalendarView)findViewById(R.id.calendarView2);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                DecimalFormat mFormat= new DecimalFormat("00");
                String date = i+"-"+mFormat.format(Integer.valueOf(i1+1))+"-"+mFormat.format(Integer.valueOf(i2));
//                System.out.println(date);
                Intent intent=new Intent(CalendarActivity.this,DailyCaloriesActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(CalendarActivity.this,HomeActivity.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(CalendarActivity.this,RegisterActivity.class));
                        break;
                    case R.id.chatbot:
                        startActivity(new Intent(CalendarActivity.this,ChatbotActivity.class));
                        break;
                }

                return false;
            }
        });
    }
}