package com.madremedusa.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madremedusa.R;
import com.madremedusa.utils.AppConstant;
import com.madremedusa.utils.DrawableManager;

/**
 * Created by Andrés Mariño on 4/3/16.
 */
public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.ViewHolder> {
    private Context context;
    private DrawableManager DM;

    public Integer[] titles = {
            R.string.collaborator_1,
            R.string.collaborator_2,
            R.string.collaborator_3,
            R.string.collaborator_4,
            R.string.collaborator_5,
            R.string.collaborator_6,
            R.string.collaborator_7,
            R.string.collaborator_8,
            R.string.collaborator_9,
            R.string.collaborator_10,
            R.string.collaborator_11,
            R.string.collaborator_12,
            R.string.collaborator_13,
            R.string.collaborator_14,
            R.string.collaborator_15,
            R.string.collaborator_16,
            R.string.collaborator_17,
            R.string.collaborator_18
    };

    public CollaboratorAdapter(Context context) {
        this.context = context;
        this.DM = new DrawableManager(context, ResourcesCompat.getDrawable(context.getResources(), R.mipmap.ic_launcher, null));
    }

    @Override
    public CollaboratorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.collaborator_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CollaboratorAdapter.ViewHolder holder, int position) {
        holder.text.setText(titles[position]);
        DM.DisplayImage(AppConstant.collaboratorsUrl[position], holder.image);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView text;


        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
