package com.ryantroxler.shottystash.listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Toast;

import com.ryantroxler.shottystash.DAO.ShotgunDAO;
import com.ryantroxler.shottystash.activities.MainActivity;
import com.ryantroxler.shottystash.models.Shotgun;

/**
 * Created by ryantroxler on 7/16/17.
 */

public class OnClickListenerShotgunRecord implements View.OnClickListener {

    Context context;
    String id;

    @Override
    public void onClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();
        final ShotgunDAO shotgunDAO = new ShotgunDAO(context);
        final Shotgun shotgun = shotgunDAO.readSingleRecord(Integer.parseInt(id));

        new AlertDialog.Builder(context)
                .setTitle("Visit site")
                .setMessage("Go to shotgun webpage?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (shotgun.getWebURL() != null) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shotgun.getWebURL()));
                            context.startActivity(browserIntent);
                        } else {
                            // do nothing
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        }).show();
    }
}
