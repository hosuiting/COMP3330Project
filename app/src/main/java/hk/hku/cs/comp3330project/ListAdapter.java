package hk.hku.cs.comp3330project;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Article>{

    private Context context;

    public ListAdapter(Context context, ArrayList<Article> articleArraylist) {
        super(context, R.layout.list_item, articleArraylist);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Article article = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView title = convertView.findViewById(R.id.articletitle);
        TextView date = convertView.findViewById(R.id.date);

        title.setText(article.title);
        date.setText(article.date);
        String image = "@drawable/" + article.images;

        int imageResource = context.getResources().getIdentifier(image, null, context.getPackageName());
        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        Drawable res = context.getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);

        return super.getView(position,convertView, parent);
    }
}
