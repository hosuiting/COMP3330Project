package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        SharedPreferences mydata = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        breakfast_kcal_value=Double.longBitsToDouble(mydata.getLong("breakfast",0));
        lunch_kcal_value=Double.longBitsToDouble(mydata.getLong("lunch",0));
        dinner_kcal_value=Double.longBitsToDouble(mydata.getLong("dinner",0));
        additional_kcal_value=Double.longBitsToDouble(mydata.getLong("additional",0));

        breakfast_button=(Button) findViewById(R.id.breakfast_button);
        lunch_button = (Button) findViewById(R.id.lunch_button);
        dinner_button = (Button) findViewById(R.id.dinner_button);
        breakfast_kcal = (TextView) findViewById(R.id.breakfast_kcal);
        lunch_kcal = (TextView) findViewById(R.id.lunch_kcal);
        dinner_kcal = (TextView) findViewById(R.id.dinner_kcal);
        additional_kcal = (EditText)  findViewById(R.id.additional_kcal);
        total_kcal = (TextView)  findViewById(R.id.total_kcal);

        breakfast_kcal.setText("Breakfast : "+String.format("%.2f",breakfast_kcal_value)+"kcal");
        lunch_kcal.setText("Lunch  : "+String.format("%.2f",lunch_kcal_value)+"kcal");
        dinner_kcal.setText("Dinner : "+String.format("%.2f",dinner_kcal_value)+"kcal");
        if(additional_kcal_value !=0) {
            additional_kcal.setText(String.format("%.2f", additional_kcal_value));
        }
        total_kcal_value=breakfast_kcal_value+lunch_kcal_value+dinner_kcal_value+additional_kcal_value;
        total_kcal.setText(String.format("%.2f",total_kcal_value)+"kcal");



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
                editor.putLong("additional",Double.doubleToLongBits(additional_kcal_value));
                editor.commit();
                total_kcal_value=breakfast_kcal_value+lunch_kcal_value+dinner_kcal_value+additional_kcal_value;
                total_kcal.setText(String.format("%.2f",total_kcal_value)+"kcal");
            }
        });
    }
}