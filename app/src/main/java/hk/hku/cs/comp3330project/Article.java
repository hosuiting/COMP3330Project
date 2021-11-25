package hk.hku.cs.comp3330project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Article extends AppCompatActivity {
    boolean liked = false;
    String title, date;

    public Article() {

    }
    public Article(String title, String date) {
        this.title = title;
        this.date = date;
    }

    private ImageView button_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = this.getIntent();
        String titles = intent.getStringExtra("title");
        String dates = intent.getStringExtra("date");
        String content = intent.getStringExtra("content");
        System.out.println(content);
        ((TextView) findViewById(R.id.textView_articleTitle)).setText(titles);
        ((TextView) findViewById(R.id.article_details)).setText(dates);
        ((TextView) findViewById(R.id.article_content)).setText(content);
        button_like = findViewById(R.id.button_like);


        button_like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                liked = !liked;
                if (liked) {
                    button_like.setBackgroundResource(R.drawable.heart_after_like);
                } else {
                    button_like.setBackgroundResource(R.drawable.heart_before_like);
                }
            }
        });

        ((BottomNavigationView) findViewById(R.id.menu)).setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(Article.this,HomeActivity.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(Article.this,RegisterActivity.class));
                        break;
                    case R.id.chatbot:
                        startActivity(new Intent(Article.this,ChatbotActivity.class));
                        break;
                }

                return false;
            }
        });

    }
}
