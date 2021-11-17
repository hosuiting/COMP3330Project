package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calories);

        breakfast_button=(Button) findViewById(R.id.breakfast_button);
        lunch_button = (Button) findViewById(R.id.lunch_button);
        dinner_button = (Button) findViewById(R.id.dinner_button);
        breakfast_kcal = (TextView) findViewById(R.id.breakfast_kcal);
        lunch_kcal = (TextView) findViewById(R.id.lunch_kcal);
        dinner_kcal = (TextView) findViewById(R.id.dinner_kcal);
        additional_kcal = (EditText)  findViewById(R.id.additional_kcal);
        total_kcal = (TextView)  findViewById(R.id.total_kcal);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String time=extras.getString("time");

            if (time.equals("breakfast")){

                breakfast_kcal_value= extras.getDouble("calories");

                breakfast_kcal.setText(String.format("%.2f",breakfast_kcal_value)+"kcal");
            }
            else if(time == "lunch"){
                lunch_kcal_value= extras.getDouble("calories");
                lunch_kcal.setText(String.format("%.2f",lunch_kcal_value)+"kcal");
            }
            else if(time == "dinner"){
                dinner_kcal_value= extras.getDouble("calories");
                dinner_kcal.setText(String.format("%.2f",dinner_kcal_value)+"kcal");
            }
            total_kcal_value=breakfast_kcal_value+lunch_kcal_value+dinner_kcal_value+additional_kcal_value;

        }
        breakfast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DailyCaloriesActivity.this,CaloriesCalActivity.class);
                i.putExtra("time","breakfast");
                startActivity(i);
            }
        });
        lunch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DailyCaloriesActivity.this,CaloriesCalActivity.class);
                i.putExtra("time","lunch");
                startActivity(i);
            }
        });
        dinner_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DailyCaloriesActivity.this,CaloriesCalActivity.class);
                i.putExtra("time","dinner");
                startActivity(i);
            }
        });
    }
}