package hk.hku.cs.comp3330project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        ;
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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SearchBtn) {
            String name = search_bar.getText().toString();
            connect(name);
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

    public void parse_JSON_String_and_Switch_Activity(String JSONString) {
        ArrayList<String> title = new ArrayList<String>();
//                ArrayList<String> thumbnail = new ArrayList<String>();
        ArrayList<String> date = new ArrayList<String>();

        try {
            JSONObject rootJSONObj = new JSONObject(JSONString);
            String article = rootJSONObj.getString("article0");
            title.add("title");
            date.add("date");

            //                   thumbnail.add();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getBaseContext(), ArticleSearchResultsActivity.class);
        intent.putStringArrayListExtra("title", title);
        intent.putStringArrayListExtra("date", date);
        startActivity(intent);
    }

    public void connect(final String name){
        final ProgressDialog pdialog = new ProgressDialog(this);

        pdialog.setCancelable(false);
        pdialog.setMessage("Connecting ...");
        pdialog.show();

        final String url = "https://i.cs.hku.hk/~stho/comp3330/articleSearch.php?action=search&query=b" + (name.isEmpty() ? "" : "?action=insert&name=" + android.net.Uri.encode(name, "UTF-8"));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                pdialog.setMessage("Before ...");
                pdialog.show();
                final String jsonString = getJsonPage(url);
                if (jsonString.equals("Fail to login"))
                    success = false;
                final boolean finalSuccess = success;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (finalSuccess) {
                            parse_JSON_String_and_Switch_Activity(jsonString);
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