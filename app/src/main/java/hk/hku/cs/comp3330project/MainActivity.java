package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startBtn);
        SharedPreferences mydata = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        if(mydata.contains("username")){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mydata.contains("username")){
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }
                else {

                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                }

            }
        });
    }
}