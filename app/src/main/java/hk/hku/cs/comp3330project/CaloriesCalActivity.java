package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_cal);
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



        beef_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beef_amount.setVisibility(View.VISIBLE);
            }
        });
    }
}