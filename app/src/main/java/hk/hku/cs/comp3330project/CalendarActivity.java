package hk.hku.cs.comp3330project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DecimalFormat;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    private TextView limit_text;
    private Button limit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        limit_text = (TextView) findViewById(R.id.limit_text);
        limit_button = (Button) findViewById(R.id.limit_button);
        calendarView = (CalendarView)findViewById(R.id.calendarView2);
        SharedPreferences mydata = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        limit_text.setText("Your daily calories limit: "+mydata.getString("limit","2200"));
        limit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
                builder.setTitle("Edit calories limit ");
                final EditText input = new EditText(CalendarActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences mydata=getSharedPreferences("mydata", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mydata.edit();
                        editor.putString("limit",input.getText().toString());
                        editor.commit();
                        limit_text.setText("My daily calories limit : "+input.getText().toString()+" kcal");

                    }
                });
                builder.setNegativeButton("Cancel   ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
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