package com.ryantroxler.shottystash;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ryantroxler on 7/4/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ShotgunViewHolder> {

    List<ObjectShotgun> shotguns;

    RVAdapter(List<ObjectShotgun> shotguns) {
        this.shotguns = shotguns;
    }

    @Override
    public int getItemCount() {
        return shotguns.size();
    }

    public void addItem(ObjectShotgun shotgun) {
        shotguns.add(shotgun);
        notifyItemInserted(shotguns.size() - 1);
    }

    public void update(List<ObjectShotgun> shotguns) {
        this.shotguns = shotguns;
        notifyDataSetChanged();
    }

    @Override
    public ShotgunViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item, viewGroup, false);
        return new ShotgunViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShotgunViewHolder viewHolder, int i) {
        viewHolder.shotgunName.setText(shotguns.get(i).name);
        viewHolder.shotgunPrice.setText("$" + Double.toString(shotguns.get(i).price));

        if (!shotguns.get(i).image_url.isEmpty()) {
            Context context = viewHolder.shotgunImage.getContext();
            Picasso picasso = Picasso.with(context);
            picasso.load(shotguns.get(i).image_url)
                    .into(viewHolder.shotgunImage);
        }
        viewHolder.cv.setTag(Integer.toString(shotguns.get(i).id));
        viewHolder.cv.setOnLongClickListener(new OnLongClickListenerShotgunRecord());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ShotgunViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView shotgunName;
        TextView shotgunPrice;
        ImageView shotgunImage;

        ShotgunViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            shotgunName = (TextView) itemView.findViewById(R.id.shotgun_name);
            shotgunPrice = (TextView) itemView.findViewById(R.id.shotgun_price);
            shotgunImage = (ImageView) itemView.findViewById(R.id.shotgun_image);
        }
    }
}
