package com.madremedusa.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madremedusa.R;
import com.madremedusa.items.ItemPost;
import com.madremedusa.utils.DrawableManager;

import java.util.ArrayList;

/**
 * Created by Andrés Mariño on 4/5/16.
 */
public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.ViewHolder> {
    private ArrayList<ItemPost> posts;
    private Context context;
    private DrawableManager DM;

    public PostViewAdapter(Context context, ArrayList<ItemPost> posts) {
        this.posts = posts;
        this.context = context;
        this.DM = new DrawableManager(context, ResourcesCompat.getDrawable(context.getResources(), R.mipmap.ic_launcher, null));
    }


    @Override
    public PostViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewAdapter.ViewHolder holder, int position) {
        ItemPost post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.author.setText(post.getAuthor());
        DM.DisplayImage(post.getThumbnailUrl(), holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView thumbnail;
        public TextView title;
        public TextView author;
        public Intent i;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            ItemMagazine m = magazines.get(getAdapterPosition());
            //          i= new Intent(context, MagazineViewer.class);
            //        i.putExtra("MagazineIssue", m.getImageUrl());
            //      context.startActivity(i);
        }
    }
}
