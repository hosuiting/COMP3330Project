package hk.hku.cs.comp3330project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Article> {

    public ListAdapter(Context context, ArrayList<Article> articleArraylist) {
        super(context, R.layout.list_item, articleArraylist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Article article = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        //ImageView imageView =
        TextView title = convertView.findViewById(R.id.articletitle);
        TextView date = convertView.findViewById(R.id.date);

        title.setText(article.title);
        date.setText(article.date);

        return super.getView(position,convertView, parent);
    }
}
