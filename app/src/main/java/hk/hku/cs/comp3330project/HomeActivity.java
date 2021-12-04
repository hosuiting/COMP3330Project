package hk.hku.cs.comp3330project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout toCaloriesCal;
    private LinearLayout toExerciseSelect;
    private LinearLayout toChatroom;
    private LinearLayout toBody;
    private LinearLayout farticle1;
    private TextView Greet;
    private Button SearchBtn;
    private EditText search_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.menu);
        SearchBtn = (Button) findViewById(R.id.SearchBtn);
        search_bar = (EditText) findViewById(R.id.search_bar);
        SearchBtn.setOnClickListener(this);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:

                        break;
                    case R.id.profile:
                        startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                        break;
                    case R.id.chatbot:
                        startActivity(new Intent(HomeActivity.this, ChatbotActivity.class));
                        break;
                }
                return false;
            }
        });

        Greet = (TextView) findViewById(R.id.Greet);
        SharedPreferences mydata = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        Greet.setText("Hi " + mydata.getString("username", ""));

        toCaloriesCal = (LinearLayout) findViewById(R.id.tcal);
        toCaloriesCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Date date = Calendar.getInstance().getTime();
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String date_string = dateFormat.format(date);
//
//                Intent i=new Intent(HomeActivity.this,DailyCaloriesActivity.class);
//                i.putExtra("date",date_string);
//                startActivity(i);
                startActivity(new Intent(HomeActivity.this, CalendarActivity.class));
            }
        });

        toChatroom = (LinearLayout) findViewById(R.id.tchatbot);
        toChatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ChatbotActivity.class));
            }
        });
        toBody = (LinearLayout) findViewById(R.id.tbody);
        toBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, BodyActivity.class));
            }
        });

        findViewById(R.id.texercise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ExerciseActivity.class));
            }
        });

        connect("featured_art", "of");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SearchBtn) {
            String name = search_bar.getText().toString();
            if (name == "") {
                return;
            }
            connect("search", name);
        }
    }

    public String ReadBufferedHTML(BufferedReader reader,
                                   char [] htmlBuffer, int bufSz) throws java.io.IOException {
        htmlBuffer[0] = '\0';
        int offset = 0;
        do {
            int cnt = reader.read(htmlBuffer, offset, bufSz - offset); if (cnt > 0) {
                offset += cnt;
            } else {
                break; }
        } while (true);
        return new String(htmlBuffer);
    }

    public String getJsonPage(String url) {
        HttpURLConnection conn_object = null;
        final int HTML_BUFFER_SIZE = 2 * 1024 * 1024;
        char htmlBuffer[] = new char[HTML_BUFFER_SIZE];
        try {
            URL url_object = new URL(url);
            conn_object = (HttpURLConnection) url_object.openConnection();
            conn_object.setInstanceFollowRedirects(true);
            BufferedReader reader_list = new BufferedReader
                    (new InputStreamReader(conn_object.getInputStream()));
            String HTMLSource = ReadBufferedHTML(reader_list, htmlBuffer, HTML_BUFFER_SIZE);
            reader_list.close();
            return HTMLSource;
        } catch (Exception e) {
            return "Fail to login";
        } finally {
            if (conn_object != null) {
                conn_object.disconnect();
            }
        }
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

    public void parse_JSON_String_and_Switch_Activity(String JSONString, String function) {
        ArrayList<String> title = new ArrayList<String>();
//                ArrayList<String> thumbnail = new ArrayList<String>();
        ArrayList<String> date = new ArrayList<String>();
        ArrayList<String> content = new ArrayList<String>();
        ArrayList<String> images = new ArrayList<String>();
        ArrayList<String> tags = new ArrayList<String>();
        ArrayList<Integer> likes = new ArrayList<Integer>();

        try {
            JSONObject rootJSONObj = new JSONObject(JSONString);
            JSONArray article = rootJSONObj.getJSONArray("article");
            for (int i = 0; i < article.length(); ++i) {
                JSONObject object = article.getJSONObject(i);
                title.add(object.getString("title"));
                date.add(object.getString("date"));
                content.add(object.getString("content"));
                images.add(object.getString("image"));
                tags.add(object.getString("tag"));
                likes.add(object.getInt("likes"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (function == "search") {
            Intent intent = new Intent(getBaseContext(), ArticleSearchResultsActivity.class);
            intent.putStringArrayListExtra("title", title);
            intent.putStringArrayListExtra("date", date);
            intent.putStringArrayListExtra("content", content);
            intent.putStringArrayListExtra("images", images);
            intent.putStringArrayListExtra("tags",tags);
            intent.putExtra("likes",likes);
            startActivity(intent);
        }

        if (function == "featured_art") {
            Log.d(TAG, "initRecyclerView: init recyclerview");
            System.out.println("Featured article section");

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(layoutManager);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, title, date, content, images, tags, likes);
            recyclerView.setAdapter(adapter);
        }
    }

    public void connect(String function, final String name){
        final ProgressDialog pdialog = new ProgressDialog(this);
        String url = null;

        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();

        url = "https://i.cs.hku.hk/~stho/comp3330/articleSearch.php?action=search&query=" + android.net.Uri.encode(name, "UTF-8");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        String finalUrl = url;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                pdialog.setMessage("Before ...");
                pdialog.show();
                final String jsonString = getJsonPage(finalUrl);
                System.out.println(jsonString);
                if (jsonString.equals("Fail to login"))
                    success = false;
                final boolean finalSuccess = success;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (finalSuccess) {
                            parse_JSON_String_and_Switch_Activity(jsonString, function);
                        } else {
                            alert( "Error", "Fail to connect" );
                        }
                        pdialog.hide();
                    }
                });
            }
        });



    }




//        toExerciseSelect = findViewById(R.id.toExerciseSelect);
//        toExerciseSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this,ExerciseSelectActivity.class));
//            }
//        });
}