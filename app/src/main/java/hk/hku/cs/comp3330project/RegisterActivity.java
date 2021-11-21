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
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(age.getText()) ||
                    TextUtils.isEmpty(height.getText()) || TextUtils.isEmpty(weight.getText()) || TextUtils.isEmpty(bodyfat.getText())){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please input in all the fields!",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }
                else{
                    SharedPreferences mydata = getSharedPreferences("mydata", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mydata.edit();
                    editor.putString("username",username.getText().toString());
                    editor.putString("age",age.getText().toString());
                    editor.commit();

                    BodyStatSQLiteHelper putdb = new BodyStatSQLiteHelper(RegisterActivity.this);
                    putdb.addStatfromRegister(height.getText().toString(),weight.getText().toString(),bodyfat.getText().toString());

                    startActivity(new Intent(RegisterActivity.this,HomeActivity.class));

                }
            }
        });
    }
}