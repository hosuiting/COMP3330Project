package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
        ArrayList<String> content = intent.getStringArrayListExtra("content");

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

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getBaseContext(), Article.class);
                intent.putStringArrayListExtra("title", titles);
                intent.putStringArrayListExtra("date", dates);
                intent.putStringArrayListExtra("content", content);
                startActivity(intent);

                // When clicked, show a toast with the TextView text
//                Toast.makeText(ArticleSearchResultsActivity.this, ((TextView) view).getText(),
//                        Toast.LENGTH_SHORT).show();
                System.out.println("======================="+Integer.toString(position));
            }
        });
    }

}