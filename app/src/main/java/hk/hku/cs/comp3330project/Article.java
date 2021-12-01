package hk.hku.cs.comp3330project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Article extends AppCompatActivity implements View.OnClickListener {
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
        button_like.setBackgroundResource(R.drawable.heart_before_like);
        button_like.setOnClickListener(this);

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


    @Override
    public void onClick(View view) {
        String name = title;
        liked = !liked;
        if (liked) {
            button_like.setBackgroundResource(R.drawable.heart_after_like);
            connect(name,liked);

        } else {
            button_like.setBackgroundResource(R.drawable.heart_before_like);
            connect(name,liked);
        }
    }

    public void connect(final String name, boolean liked){
        final ProgressDialog pdialog = new ProgressDialog(this);

        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();
        final String url;

        if (liked) {
            url = "https://i7.cs.hku.hk/~cyjluk/comp3330/like.php?action=like&query=" + android.net.Uri.encode(name, "UTF-8");
        }
        else {
            url = "https://i7.cs.hku.hk/~cyjluk/comp3330/like.php?action=unlike&query=" + android.net.Uri.encode(name, "UTF-8");
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                pdialog.setMessage("Before ...");
                pdialog.show();
                final String jsonString = getJsonPage(url);
                System.out.println(jsonString);
                if (jsonString.equals("Fail to login"))
                    success = false;
                final boolean finalSuccess = success;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (finalSuccess == false) {
                            alert( "Error", "Fail to connect" );
                        }
                        pdialog.hide();
                    }
                });
            }
        });

    }

    public String getJsonPage(String url) {
        HttpURLConnection conn_object = null;
        final int HTML_BUFFER_SIZE = 2*1024*1024;
        char htmlBuffer[] = new char[HTML_BUFFER_SIZE];

        try {
            URL url_object = new URL(url);
            conn_object = (HttpURLConnection) url_object.openConnection();
            conn_object.setInstanceFollowRedirects(true);
            BufferedReader reader_list = new BufferedReader(new InputStreamReader(conn_object.getInputStream()));
            String HTMLSource = ReadBufferedHTML(reader_list, htmlBuffer, HTML_BUFFER_SIZE);
            reader_list.close();
            return HTMLSource;
        } catch (Exception e) {
            System.out.println("Exception caught!");
            return "Fail to login";
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            if (conn_object != null) {
                conn_object.disconnect();
            }
        }
    }

    public String ReadBufferedHTML(BufferedReader reader, char [] htmlBuffer, int bufSz) throws java.io.IOException
    {
        htmlBuffer[0] = '\0';
        int offset = 0;
        do {
            int cnt = reader.read(htmlBuffer, offset, bufSz - offset);
            if (cnt > 0) {
                offset += cnt;
            } else {
                break;
            }
        } while (true);
        return new String(htmlBuffer);
    }

    protected void alert(String title, String mymessage){
        AlertDialog show = new AlertDialog.Builder(this)
                .setMessage(mymessage)
                .setTitle(title)
                .setCancelable(true)
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        }
                )
                .show();
    }

}

