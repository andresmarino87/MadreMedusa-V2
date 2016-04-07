package com.madremedusa.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madremedusa.R;
import com.madremedusa.utils.DrawableManager;
import com.madremedusa.views.TouchImageView;

import java.util.ArrayList;

/**
 * Created by Andrés Mariño on 4/2/16.
 */
public class FullScreenAdapter  extends RecyclerView.Adapter<FullScreenAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> imageUrl;
    private DrawableManager DM;


    public FullScreenAdapter(Context context, ArrayList<String> items) {
        this.imageUrl = items;
        this.context = context;
        this.DM = new DrawableManager(context, ResourcesCompat.getDrawable(context.getResources(), R.mipmap.ic_launcher, null));
    }

    @Override
    public FullScreenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.page_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DM.DisplayImage(imageUrl.get(position), holder.page);
    }

    @Override
    public int getItemCount() {
        return imageUrl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TouchImageView page;

        public ViewHolder(View itemView) {
            super(itemView);
            page = (TouchImageView) itemView.findViewById(R.id.page);
        }
    }
}
