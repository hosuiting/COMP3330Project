package hk.hku.cs.comp3330project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
        setContentView(R.layout.activity_article_search_results);
        ListView lv = (ListView) findViewById(android.R.id.list);

        Intent intent = this.getIntent();
        ArrayList<String> titles = intent.getStringArrayListExtra("title");
        ArrayList<String> dates = intent.getStringArrayListExtra("date");
        ArrayList<String> content = intent.getStringArrayListExtra("content");
        ArrayList<String> images = intent.getStringArrayListExtra("images");
        ArrayList<String> tags = intent.getStringArrayListExtra("tags");
        ArrayList<Integer> likes = intent.getIntegerArrayListExtra("likes");

        if (titles != null) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Entered loop");
            if (titles.size() == 0) {
                Toast.makeText(this, "No articles matching that keyword", Toast.LENGTH_LONG).show();
                Intent returnintent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(returnintent);
            }
            for (int i = 0; i < titles.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("title", titles.get(i));
                map.put("date", dates.get(i));
                String image = "@drawable/thumbnail_" + images.get(i);
                int imageResource = getResources().getIdentifier(image, null, getPackageName());
                map.put("image",imageResource);

                list.add(map);
            }
        }
        else{
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Didn't enter loop");
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.list_item, new String[]{"title", "date", "image"}, new int[]{R.id.articletitle, R.id.date, R.id.profile_pic});
        lv.setAdapter(adapter);

        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getBaseContext(), Article.class);
                intent.putExtra("title", titles.get(position));
                intent.putExtra("date", dates.get(position));
                intent.putExtra("content", content.get(position));
                intent.putExtra("image",images.get(position));
                intent.putExtra("tag",tags.get(position));
                intent.putExtra("likes", likes.get(position));
                startActivity(intent);

                // When clicked, show a toast with the TextView text
//                Toast.makeText(ArticleSearchResultsActivity.this, ((TextView) view).getText(),
//                        Toast.LENGTH_SHORT).show();
                System.out.println("======================="+Integer.toString(position));
            }
        });
    }

}