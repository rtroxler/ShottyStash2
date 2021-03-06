package com.ryantroxler.shottystash.listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.ryantroxler.shottystash.DAO.ShotgunDAO;
import com.ryantroxler.shottystash.activities.MainActivity;
import com.ryantroxler.shottystash.R;
import com.ryantroxler.shottystash.models.Shotgun;

/**
 * Created by ryantroxler on 7/3/17.
 */

public class OnClickListenerCreateShotgun implements View.OnClickListener {
    Context context;

    @Override
    public void onClick(View view) {
        context = view.getContext();

        FloatingActionMenu fam = (FloatingActionMenu) ((MainActivity) context).findViewById(R.id.fam);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.shotgun_input_form, null, false);

        final EditText editTextShotgunName = formElementsView.findViewById(R.id.editTextShotgunName);
        final EditText editTextShotgunPrice = formElementsView.findViewById(R.id.editTextShotgunPrice);
        final EditText editTextShotgunImageUrl = formElementsView.findViewById(R.id.editTextShotgunImageUrl);
        final EditText editTextShotgunWebUrl = formElementsView.findViewById(R.id.editTextShotgunWebUrl);

        fam.close(false);
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Create New Shotgun")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)  {
                                String name = editTextShotgunName.getText().toString();
                                Double price = Double.parseDouble(editTextShotgunPrice.getText().toString());
                                String image_url = editTextShotgunImageUrl.getText().toString();
                                String web_url = editTextShotgunWebUrl.getText().toString();

                                Shotgun shotgun = new Shotgun(name, image_url, web_url, price);

                                boolean createSuccessful = new ShotgunDAO(context).create(shotgun);

                                if (createSuccessful) {
                                    Toast.makeText(context, "Information was saved!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Save failed. :(", Toast.LENGTH_SHORT).show();
                                }

                                ((MainActivity) context).updateRecyclerView(shotgun);
                            }
                        }).show();
    }
}

