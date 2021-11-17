package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                startActivity(new Intent(HomeActivity.this,DailyCaloriesActivity.class));
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