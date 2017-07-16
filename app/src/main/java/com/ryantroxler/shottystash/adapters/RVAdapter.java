package com.ryantroxler.shottystash.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ryantroxler.shottystash.R;
import com.ryantroxler.shottystash.listeners.OnLongClickListenerShotgunRecord;
import com.ryantroxler.shottystash.listeners.OnClickListenerShotgunRecord;
import com.ryantroxler.shottystash.models.Shotgun;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ryantroxler on 7/4/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ShotgunViewHolder> {

    private List<Shotgun> shotguns;
    private Double currentBalance;

    public RVAdapter(List<Shotgun> shotguns, Double balance) {
        this.shotguns = shotguns;
        this.currentBalance = balance;
    }

    @Override
    public int getItemCount() {
        return shotguns.size();
    }

    public void addItem(Shotgun shotgun) {
        shotguns.add(shotgun);
        notifyItemInserted(shotguns.size() - 1);
    }

    public void update(List<Shotgun> shotguns) {
        this.shotguns = shotguns;
        notifyDataSetChanged();
    }

    public void update(List<Shotgun> shotguns, Double balance) {
        this.shotguns = shotguns;
        this.currentBalance = balance;
        notifyDataSetChanged();
    }

    @Override
    public ShotgunViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item, viewGroup, false);
        return new ShotgunViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShotgunViewHolder viewHolder, int i) {
        Double price = shotguns.get(i).getPrice();
        viewHolder.shotgunPrice.setText("$" + Double.toString(price));
        viewHolder.shotgunName.setText(shotguns.get(i).getName());
        Double progress = (currentBalance / price) * 100;
        viewHolder.shotgunProgress.setProgress(progress.intValue());

        Context context = viewHolder.shotgunImage.getContext();
        if (!shotguns.get(i).getImageURL().isEmpty()) {
            Picasso picasso = Picasso.with(context);
            picasso.load(shotguns.get(i).getImageURL())
                    .into(viewHolder.shotgunImage);
        } else {
            Picasso.with(context)
                   .load(R.drawable.shotgun_silhouette)
                   .into(viewHolder.shotgunImage);
        }
        viewHolder.cv.setTag(Integer.toString(shotguns.get(i).getId()));
        viewHolder.cv.setOnLongClickListener(new OnLongClickListenerShotgunRecord());
        viewHolder.cv.setOnClickListener(new OnClickListenerShotgunRecord());
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
        ProgressBar shotgunProgress;

        ShotgunViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            shotgunName = itemView.findViewById(R.id.shotgun_name);
            shotgunPrice = itemView.findViewById(R.id.shotgun_price);
            shotgunImage = itemView.findViewById(R.id.shotgun_image);
            shotgunProgress = itemView.findViewById(R.id.shotgun_progress);
        }
    }
}
