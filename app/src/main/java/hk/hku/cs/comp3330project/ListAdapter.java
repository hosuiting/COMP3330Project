package hk.hku.cs.comp3330project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Map;

public class ListAdapter extends ArrayAdapter<Article> {
    private Context mContext;
    private int mResource;

    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Article article = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView title = convertView.findViewById(R.id.articletitle);
        TextView date = convertView.findViewById(R.id.date);

        title.setText(article.title);
        date.setText(article.date);
        imageView.setImageResource(R.drawable.applogo);

        return super.getView(position,convertView, parent);
    }


}
