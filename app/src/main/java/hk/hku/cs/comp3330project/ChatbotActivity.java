package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private EditText userMsgEdit;
    private FloatingActionButton sendMsgFAB;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModel> chatsModelArrayList;
    private ChatRVAdapter chatRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        chatsRV = findViewById(R.id.idRVChats);
        userMsgEdit = findViewById(R.id.idEditMessage);
        sendMsgFAB = findViewById(R.id.idFABSend);
        chatsModelArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModelArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (userMsgEdit.getText().toString().isEmpty()){
                    Toast.makeText(ChatbotActivity.this, "Please enter your message!!", Toast.LENGTH_LONG).show();
                    return;
                }
                getResponse(userMsgEdit.getText().toString());
                userMsgEdit.setText("");
            }
        });
    }
    public void getBotMessage(final String message) {
        final String url = "http://api.brainshop.ai/get?bid=161460&key=Q99aIs2bkEhEx5Rn&uid=[uid]&msg=" + message;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        System.out.println(message);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                final String jsonString = getJsonString(url);
                System.out.println("Waiting");
                System.out.println(jsonString);
                if (jsonString.equals("Fail to connect"))
                    success = false;
                if(success) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            parse_JSON_String(jsonString);
                        }
                    });
                }
            }
        });
    }
    public void parse_JSON_String(String JSONString){
        try {
            JSONObject rootJSONObj = new JSONObject(JSONString);
            String responseMessage = rootJSONObj.getString("cnt");
            chatsModelArrayList.add(new ChatsModel(responseMessage,BOT_KEY));
            chatRVAdapter.notifyDataSetChanged();
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getJsonString(String url) {
        System.out.println(url);
        HttpURLConnection conn_object = null;
        final int HTML_BUFFER_SIZE = 2*1024*1024;
        char htmlBuffer[] = new char[HTML_BUFFER_SIZE];
        try {
            URL url_object = new URL(url);
            conn_object = (HttpURLConnection) url_object.openConnection();
            conn_object.setInstanceFollowRedirects(true);
            BufferedReader reader_list = new BufferedReader(new InputStreamReader(conn_object.getInputStream()));
            String HTMLSource = ReadBufferedHTML(reader_list, htmlBuffer,
                    HTML_BUFFER_SIZE);
            reader_list.close();
            return HTMLSource;
        } catch (Exception e) {
            return "Fail to connect!";
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            if (conn_object != null) {
                conn_object.disconnect();
            }
        }
    }
    public String ReadBufferedHTML(BufferedReader reader, char [] htmlBuffer, int bufSz) throws java.io.IOException {
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


    private void getResponse (String message){
        chatsModelArrayList.add(new ChatsModel(message,USER_KEY));
        //getBotMessage(message);
        //chatsModelArrayList.add(new ChatsModel("Please revert your question",BOT_KEY));
        //chatRVAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=161460&key=Q99aIs2bkEhEx5Rn&uid=1&msg=" + message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModel> call = retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()){
                    MsgModel model = response.body();
                    chatsModelArrayList.add(new ChatsModel(model.getCnt(),BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                chatsModelArrayList.add(new ChatsModel("The connection is failed",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });
    }
}