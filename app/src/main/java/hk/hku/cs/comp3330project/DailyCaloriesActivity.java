package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DailyCaloriesActivity extends AppCompatActivity {
    private Button breakfast_button;
    private Button lunch_button;
    private Button dinner_button;
    private TextView breakfast_kcal;
    private TextView lunch_kcal;
    private TextView dinner_kcal;
    private EditText additional_kcal;
    private TextView total_kcal;
    private double breakfast_kcal_value;
    private double lunch_kcal_value;
    private double dinner_kcal_value;
    private double total_kcal_value;
    private double additional_kcal_value;
    private String date;
    private TextView date_display;
    private Button yesterday;
    private Button tomorrow;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date today;
    private Button toCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calories);
        tomorrow = (Button)findViewById(R.id.tomorrow);
        SharedPreferences mydata = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        date_display=(TextView) findViewById(R.id.date_display) ;
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            date=extras.getString("date");
            date_display.setText(date);
            Date check_today=Calendar.getInstance().getTime();
            String check_today_string=dateFormat.format(check_today);
//            if(date.equals(check_today_string)){
//                tomorrow.setVisibility(View.GONE);
//            }
        }
        breakfast_kcal_value=Double.longBitsToDouble(mydata.getLong(date+"breakfast",0));
        lunch_kcal_value=Double.longBitsToDouble(mydata.getLong(date+"lunch",0));
        dinner_kcal_value=Double.longBitsToDouble(mydata.getLong(date+"dinner",0));
        additional_kcal_value=Double.longBitsToDouble(mydata.getLong(date+"additional",0));

        breakfast_button=(Button) findViewById(R.id.breakfast_button);
        lunch_button = (Button) findViewById(R.id.lunch_button);
        dinner_button = (Button) findViewById(R.id.dinner_button);
        breakfast_kcal = (TextView) findViewById(R.id.breakfast_kcal);
        lunch_kcal = (TextView) findViewById(R.id.lunch_kcal);
        dinner_kcal = (TextView) findViewById(R.id.dinner_kcal);
        additional_kcal = (EditText)  findViewById(R.id.additional_kcal);
        total_kcal = (TextView)  findViewById(R.id.total_kcal);
        yesterday = (Button)findViewById(R.id.yesterday);
        toCalendar = (Button)findViewById(R.id.toCalendar);

        breakfast_kcal.setText("Breakfast : "+String.format("%.2f",breakfast_kcal_value)+"kcal");
        lunch_kcal.setText("Lunch  : "+String.format("%.2f",lunch_kcal_value)+"kcal");
        dinner_kcal.setText("Dinner : "+String.format("%.2f",dinner_kcal_value)+"kcal");
        if(additional_kcal_value !=0) {
            additional_kcal.setText(String.format("%.2f", additional_kcal_value));
        }
        total_kcal_value=breakfast_kcal_value+lunch_kcal_value+dinner_kcal_value+additional_kcal_value;
        total_kcal.setText(String.format("%.2f",total_kcal_value)+"kcal");

        toCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DailyCaloriesActivity.this,CalendarActivity.class));
            }
        });

        breakfast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DailyCaloriesActivity.this,CaloriesCalActivity.class);
                i.putExtra("time",date+"breakfast");
                i.putExtra("date",date);
                startActivity(i);
            }
        });
        lunch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DailyCaloriesActivity.this,CaloriesCalActivity.class);
                i.putExtra("time",date+"lunch");
                i.putExtra("date",date);
                startActivity(i);
            }
        });
        dinner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DailyCaloriesActivity.this,CaloriesCalActivity.class);
                i.putExtra("time",date+"dinner");
                i.putExtra("date",date);
                startActivity(i);
            }
        });
        additional_kcal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!additional_kcal.getText().toString().matches("")) {
                    additional_kcal_value = Double.parseDouble(additional_kcal.getText().toString());

                }
                else{
                    additional_kcal_value=0;
                }
                SharedPreferences mydata=getSharedPreferences("mydata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mydata.edit();
                editor.putLong(date+"additional",Double.doubleToLongBits(additional_kcal_value));
                editor.commit();
                total_kcal_value=breakfast_kcal_value+lunch_kcal_value+dinner_kcal_value+additional_kcal_value;
                total_kcal.setText(String.format("%.2f",total_kcal_value)+"kcal");
            }

        });
        yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    today=dateFormat.parse(date);
                    Calendar c = Calendar.getInstance();
                    c.setTime(today);
                    c.add(Calendar.DATE,-1);
                    String newday=dateFormat.format(c.getTime());
                    Intent i=new Intent(DailyCaloriesActivity.this,DailyCaloriesActivity.class);
                    i.putExtra("date",newday);
                    startActivity(i);


                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
        });
        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    today=dateFormat.parse(date);
                    Calendar c = Calendar.getInstance();
                    c.setTime(today);
                    c.add(Calendar.DATE,1);
                    String newday=dateFormat.format(c.getTime());
                    Intent i=new Intent(DailyCaloriesActivity.this,DailyCaloriesActivity.class);
                    i.putExtra("date",newday);
                    startActivity(i);


                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
        });

    }
}