package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private Button toCaloriesCal;
    private Button toChatroom;
    private Button toBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toCaloriesCal=(Button) findViewById(R.id.toCaloriesCal);
        toCaloriesCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date_string = dateFormat.format(date);

                Intent i=new Intent(HomeActivity.this,DailyCaloriesActivity.class);
                i.putExtra("date",date_string);
                startActivity(i);
            }
        });
        toChatroom=(Button) findViewById(R.id.goChatroom);
        toChatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ChatroomActivity.class));
            }
        });
        toBody=(Button) findViewById(R.id.toBody);
        toBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(HomeActivity.this,BodyActivity.class));
            }
        });
    }
}