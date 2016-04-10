package com.madremedusa.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madremedusa.R;
import com.madremedusa.activities.PostActivity;
import com.madremedusa.items.ItemPost;
import com.madremedusa.utils.DrawableManager;

import java.util.ArrayList;

/**
 * Created by Andrés Mariño on 4/5/16.
 */
public class PostViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROG = 0;
    private boolean loading;
    private ArrayList<ItemPost> posts;
    private Context context;
    private DrawableManager DM;
    public OnLoadMoreListener onLoadMoreListener;

    public PostViewAdapter(Context context, ArrayList<ItemPost> posts) {
        this.posts = posts;
        this.context = context;
        this.DM = new DrawableManager(context, ResourcesCompat.getDrawable(context.getResources(), R.mipmap.ic_launcher, null));
    }

    @Override
    public int getItemViewType(int position) {
        return posts.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_ITEM) {
            View layoutView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
            viewHolder = new ViewHolder(layoutView);
        } else {
            View layoutView = LayoutInflater.from(context).inflate(R.layout.loading_layout, parent, false);
            viewHolder = new ProgressViewHolder(layoutView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ItemPost post = posts.get(position);
            ((ViewHolder) holder).title.setText(post.getTitle());
            ((ViewHolder) holder).author.setText(post.getAuthor());
            DM.DisplayImage(post.getThumbnailUrl(), ((ViewHolder) holder).thumbnail);
        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded(boolean aux) {
        loading = aux;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
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
            ItemPost aux = posts.get(getAdapterPosition());
            i = new Intent(context, PostActivity.class);
            i.putExtra("thumbnail", aux.getThumbnailUrl());
            i.putExtra("title", aux.getTitle());
            i.putExtra("author", aux.getAuthor());
            i.putExtra("content", aux.getContent());
            context.startActivity(i);
            ((Activity)context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }
}
