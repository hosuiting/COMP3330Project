package hk.hku.cs.comp3330project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ArticleActivity extends AppCompatActivity {
    private ImageView button_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        button_like = findViewById(R.id.button_like);

        button_like.setOnClickListener(new View.OnClickListener() {
            boolean liked = false;
            @Override
            public void onClick(View view) {
                liked = !liked;
                if(liked) {
                    button_like.setBackgroundResource(R.drawable.heart_before_like);
                } else if (!(liked)){
                    button_like.setBackgroundResource(R.drawable.heart_before_like);
                }
            }
        });

        ((BottomNavigationView) findViewById(R.id.menu)).setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(ArticleActivity.this,HomeActivity.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(ArticleActivity.this,RegisterActivity.class));
                        break;
                    case R.id.chatbot:
                        startActivity(new Intent(ArticleActivity.this,ChatbotActivity.class));
                        break;
                }

                return false;
            }
        });

    }
}