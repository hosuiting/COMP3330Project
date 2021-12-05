package hk.hku.cs.comp3330project;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private OnNoteListener mOnNoteListener;

    //vars
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> content = new ArrayList<String>();
    ArrayList<String> images = new ArrayList<String>();
    ArrayList<String> tags = new ArrayList<String>();
    ArrayList<Integer> likes = new ArrayList<Integer>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> title, ArrayList<String> date, ArrayList<String> content, ArrayList<String> images, ArrayList<String> tags, ArrayList<Integer> likes, OnNoteListener onNoteListener) {
        mContext = context;
        this.mOnNoteListener = onNoteListener;
        this.title = title;
        this.date = date;
        this.content = content;
        this.images = images;
        this.tags = tags;
        this.likes = likes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_listitem, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.name.setText(title.get(position));
        String image = "@drawable/thumbnail_" + images.get(position);
        System.out.println(image);
        int imageResource = mContext.getResources().getIdentifier(image, null, mContext.getPackageName());
        holder.image.setImageResource(imageResource);


    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView image;
        TextView name;
        OnNoteListener onNoteListener;

        public ViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.name);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getBindingAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}