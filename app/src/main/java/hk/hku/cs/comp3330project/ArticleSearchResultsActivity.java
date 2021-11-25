package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hk.hku.cs.comp3330project.databinding.ActivityMainBinding;

public class ArticleSearchResultsActivity extends ListActivity {

    ArrayList< Map<String, Object> > list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        ArrayList<String> titles = intent.getStringArrayListExtra("title");
        ArrayList<String> dates = intent.getStringArrayListExtra("date");

        if (titles != null) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Entered loop");
            for (int i = 0; i < titles.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("title", titles.get(i));
                map.put("date", dates.get(i));
                list.add(map);
            }
        }
        else{
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Didn't enter loop");
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.list_item, new String[]{"title", "date"}, new int[]{R.id.articletitle, R.id.date});
        setListAdapter(adapter);
    }

}