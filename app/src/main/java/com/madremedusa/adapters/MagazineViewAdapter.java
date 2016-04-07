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
import com.madremedusa.activities.MagazineViewer;
import com.madremedusa.items.ItemMagazine;
import com.madremedusa.utils.DrawableManager;

import java.util.ArrayList;

/**
 * Created by Andrés Mariño on 4/2/16.
 */
public class MagazineViewAdapter extends RecyclerView.Adapter<MagazineViewAdapter.ViewHolder> {
    private ArrayList<ItemMagazine> magazines;
    private Context context;
    private DrawableManager DM;

    public MagazineViewAdapter(Context context, ArrayList<ItemMagazine> magazines) {
        this.magazines = magazines;
        this.context = context;
        this.DM = new DrawableManager(context, ResourcesCompat.getDrawable(context.getResources(),R.mipmap.ic_launcher,null));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.cover_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemMagazine magazine = magazines.get(position);
        holder.cover_title.setText(magazine.getCoverName());
        DM.DisplayImage(magazine.getCover(), holder.cover_image);
    }

    @Override
    public int getItemCount() {
        return magazines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView cover_title;
        public ImageView cover_image;
        public Intent i;

        public ViewHolder(View itemView) {
            super(itemView);
            cover_title = (TextView) itemView.findViewById(R.id.cover_title);
            cover_image = (ImageView) itemView.findViewById(R.id.cover_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ItemMagazine m = magazines.get(getAdapterPosition());
            i= new Intent(context, MagazineViewer.class);
            i.putExtra("MagazineIssue", m.getImageUrl());
            context.startActivity(i);
        }
    }
}
