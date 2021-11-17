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

public class CaloriesCalActivity extends AppCompatActivity {
    private Button beef_button;
    private Button pork_button;
    private Button chicken_button;
    private Button fish_button;
    private Button vege_button;
    private Button carbs_button;
    private EditText beef_amount;
    private EditText pork_amount;
    private EditText chicken_amount;
    private EditText fish_amount;
    private EditText vege_amount;
    private EditText carbs_amount;
    private double beef_kcal;
    private double pork_kcal;
    private double chicken_kcal;
    private double fish_kcal;
    private double vege_kcal;
    private double carbs_kcal;
    private TextView beef_kcal_text;
    private TextView pork_kcal_text;
    private TextView chicken_kcal_text;
    private TextView fish_kcal_text;
    private TextView vege_kcal_text;
    private TextView carbs_kcal_text;
    private double total_kcal;
    private TextView total_kcal_text;
    private Button calores_cal_done_button;

    private String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calories_cal);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
          time=extras.getString("time");
        }

        beef_button = (Button) findViewById(R.id.beef_button);
        pork_button = (Button)findViewById(R.id.pork_button);
        chicken_button = (Button)findViewById(R.id.chicken_button);
        fish_button = (Button)findViewById(R.id.fish_button);
        vege_button = (Button)findViewById(R.id.vege_button);
        carbs_button = (Button)findViewById(R.id.carbs_button);
        beef_amount = (EditText) findViewById(R.id.beef_amount);
        pork_amount = (EditText) findViewById(R.id.pork_amount);
        chicken_amount = (EditText) findViewById(R.id.chicken_amount);
        fish_amount = (EditText) findViewById(R.id.fish_amount);
        vege_amount = (EditText) findViewById(R.id.vege_amount);
        carbs_amount = (EditText) findViewById(R.id.carbs_amount);
        beef_amount.setVisibility(View.GONE);
        pork_amount.setVisibility(View.GONE);
        chicken_amount.setVisibility(View.GONE);
        fish_amount.setVisibility(View.GONE);
        vege_amount.setVisibility(View.GONE);
        carbs_amount.setVisibility(View.GONE);
        beef_kcal_text=(TextView) findViewById(R.id.beef_kcal);
        pork_kcal_text=(TextView) findViewById(R.id.pork_kcal);
        chicken_kcal_text=(TextView) findViewById(R.id.chicken_kcal);
        fish_kcal_text=(TextView) findViewById(R.id.fish_kcal);
        vege_kcal_text=(TextView) findViewById(R.id.vege_kcal);
        carbs_kcal_text=(TextView) findViewById(R.id.carbs_kcal);
        beef_kcal_text.setVisibility(View.GONE);
        pork_kcal_text.setVisibility(View.GONE);
        chicken_kcal_text.setVisibility(View.GONE);
        fish_kcal_text.setVisibility(View.GONE);
        vege_kcal_text.setVisibility(View.GONE);
        carbs_kcal_text.setVisibility(View.GONE);
        total_kcal_text = (TextView) findViewById(R.id.total_kcal);
        calores_cal_done_button = (Button) findViewById(R.id.calories_cal_done_button);
        beef_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beef_kcal_text.setVisibility(View.VISIBLE);
                beef_amount.setVisibility(View.VISIBLE);
            }
        });
        pork_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pork_kcal_text.setVisibility(View.VISIBLE);
                pork_amount.setVisibility(View.VISIBLE);
            }
        });
        chicken_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chicken_kcal_text.setVisibility(View.VISIBLE);
                chicken_amount.setVisibility(View.VISIBLE);
            }
        });

        fish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fish_kcal_text.setVisibility(View.VISIBLE);
                fish_amount.setVisibility(View.VISIBLE);
            }
        });
        vege_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vege_kcal_text.setVisibility(View.VISIBLE);
                vege_amount.setVisibility(View.VISIBLE);
            }
        });
        carbs_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carbs_kcal_text.setVisibility(View.VISIBLE);
                carbs_amount.setVisibility(View.VISIBLE);
            }
        });
        beef_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!beef_amount.getText().toString().matches("")) {
                    beef_kcal = Double.parseDouble(beef_amount.getText().toString()) * 2.5;

                    beef_kcal_text.setText("kcal: " + String.format("%.2f",beef_kcal));
                }
                else{
                    beef_kcal=0;
                    beef_kcal_text.setText("kcal: " + String.format("%.2f",beef_kcal));
                }
                total_kcal=beef_kcal+pork_kcal+chicken_kcal+fish_kcal+vege_kcal+carbs_kcal;
                total_kcal_text.setText(String.format("%.2f",total_kcal)+" kcal");
            }
        });
        pork_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!pork_amount.getText().toString().matches("")) {
                    pork_kcal = Double.parseDouble(pork_amount.getText().toString()) * 2.42;
                    pork_kcal_text.setText("kcal: " + String.format("%.2f",pork_kcal));
                }
                else{
                    pork_kcal=0;
                    pork_kcal_text.setText("kcal: " + String.format("%.2f",pork_kcal));
                }
                total_kcal=beef_kcal+pork_kcal+chicken_kcal+fish_kcal+vege_kcal+carbs_kcal;
                total_kcal_text.setText(String.format("%.2f",total_kcal)+" kcal");
            }
        });
        chicken_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!chicken_amount.getText().toString().matches("")) {
                    chicken_kcal = Double.parseDouble(chicken_amount.getText().toString()) * 2.39;
                    chicken_kcal_text.setText("kcal: " + String.format("%.2f",chicken_kcal));
                }
                else{
                    chicken_kcal=0;
                    chicken_kcal_text.setText("kcal: " + String.format("%.2f",chicken_kcal));
                }
                total_kcal=beef_kcal+pork_kcal+chicken_kcal+fish_kcal+vege_kcal+carbs_kcal;
                total_kcal_text.setText(String.format("%.2f",total_kcal)+" kcal");
            }
        });
        fish_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!fish_amount.getText().toString().matches("")) {
                    fish_kcal = Double.parseDouble(fish_amount.getText().toString()) * 2.06;
                    fish_kcal_text.setText("kcal: " + String.format("%.2f",fish_kcal));
                }
                else{
                    fish_kcal=0;
                    fish_kcal_text.setText("kcal: " + String.format("%.2f",fish_kcal));
                }
                total_kcal=beef_kcal+pork_kcal+chicken_kcal+fish_kcal+vege_kcal+carbs_kcal;
                total_kcal_text.setText(String.format("%.2f",total_kcal)+" kcal");
            }
        });
        vege_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!vege_amount.getText().toString().matches("")) {
                    vege_kcal = Double.parseDouble(vege_amount.getText().toString()) * 0.65;
                    vege_kcal_text.setText("kcal: " + String.format("%.2f",vege_kcal));
                }
                else{
                    vege_kcal=0;
                    vege_kcal_text.setText("kcal: " + String.format("%.2f",vege_kcal));
                }
                total_kcal=beef_kcal+pork_kcal+chicken_kcal+fish_kcal+vege_kcal+carbs_kcal;
                total_kcal_text.setText(String.format("%.2f",total_kcal)+" kcal");
            }
        });
        carbs_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!carbs_amount.getText().toString().matches("")) {
                    carbs_kcal = Double.parseDouble(carbs_amount.getText().toString()) * 4;
                    carbs_kcal_text.setText("kcal: " + String.format("%.2f",carbs_kcal));
                }
                else{
                    carbs_kcal=0;
                    carbs_kcal_text.setText("kcal: " + String.format("%.2f",carbs_kcal));
                }
                total_kcal=beef_kcal+pork_kcal+chicken_kcal+fish_kcal+vege_kcal+carbs_kcal;
                total_kcal_text.setText(String.format("%.2f",total_kcal)+" kcal");
            }
        });
        calores_cal_done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CaloriesCalActivity.this,DailyCaloriesActivity.class);
                SharedPreferences mydata=getSharedPreferences("mydata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mydata.edit();
                editor.putLong(time,Double.doubleToLongBits(total_kcal));
                editor.commit();
//                i.putExtra("time","breakfast");
//                i.putExtra("calories",total_kcal);
                startActivity(i);
            }
        });
    }
}