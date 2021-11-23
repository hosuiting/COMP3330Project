package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.http.Body;

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText age;
    private EditText height;
    private EditText weight;
    private EditText bodyfat;
    private Button confirm_button;
    private Button cancel_button;
    private RadioButton Male_button,Female_button;
    private TextView register_message, username_text,age_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.register_username);
        age = (EditText) findViewById(R.id.register_age);
        height = (EditText) findViewById(R.id.register_height);
        weight = (EditText) findViewById(R.id.register_weight);
        bodyfat = (EditText) findViewById(R.id.register_bodyfat);
        confirm_button = (Button) findViewById(R.id.Confirm_button);
        cancel_button = (Button) findViewById(R.id.Cancel_button);
        Male_button = (RadioButton)findViewById(R.id.Male_button);
        Female_button = (RadioButton)findViewById(R.id.Female_button);
        register_message = (TextView)findViewById(R.id.register_message);
        username_text = (TextView)findViewById(R.id.username_text);
        age_text = (TextView)findViewById(R.id.age_text);
        username_text.setVisibility(View.GONE);
        age_text.setVisibility(View.GONE);
        SharedPreferences mydata = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        if(mydata.contains("username")){
            register_message.setText("Edit your profile");
            username.setText(mydata.getString("username",""));
            age.setText(mydata.getString("age",""));
            height.setVisibility(View.GONE);
            weight.setVisibility(View.GONE);
            bodyfat.setVisibility(View.GONE);
            username_text.setVisibility(View.VISIBLE);
            age_text.setVisibility(View.VISIBLE);
            System.out.println(mydata.getString("sex",""));
            if(mydata.getString("sex","").equals("male")){
                Male_button.setChecked(true);
                Female_button.setChecked(false);

            }
            else if(mydata.getString("sex","").equals("female")){
                Male_button.setChecked(false);
                Female_button.setChecked(true);
            }
        }

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(age.getText())){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please input in all the fields!",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
                else if(mydata.contains("username")){
                    SharedPreferences.Editor editor = mydata.edit();
                    editor.putString("username",username.getText().toString());
                    editor.putString("age",age.getText().toString());
                    if(Male_button.isChecked()){
                        editor.putString("sex","male");

                    }
                    else if(Female_button.isChecked()){
                        editor.putString("sex","female");

                    }
                    editor.commit();
                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                }



                else{
                        if(TextUtils.isEmpty(height.getText()) || TextUtils.isEmpty(weight.getText()) ||TextUtils.isEmpty(bodyfat.getText()) ){
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Please input in all the fields!",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        }
                        else{
                            SharedPreferences.Editor editor = mydata.edit();
                            editor.putString("username",username.getText().toString());
                            editor.putString("age",age.getText().toString());
                            if(Male_button.isChecked()){
                                editor.putString("sex","male");
                            }
                            else if(Female_button.isChecked()){
                                editor.putString("sex","female");
                            }
                            editor.commit();
                            BodyStatSQLiteHelper putdb = new BodyStatSQLiteHelper(RegisterActivity.this);
                            putdb.addStatfromRegister(height.getText().toString(),weight.getText().toString(),bodyfat.getText().toString());

                            startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                        }




                }
            }
        });
    }
}