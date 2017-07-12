package com.ryantroxler.shottystash.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ryantroxler.shottystash.DAO.ShotgunDAO;
import com.ryantroxler.shottystash.activities.MainActivity;
import com.ryantroxler.shottystash.R;
import com.ryantroxler.shottystash.models.Shotgun;

/**
 * Created by ryantroxler on 7/3/17.
 */

public class OnLongClickListenerShotgunRecord implements View.OnLongClickListener {
    Context context;
    String id;

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Edit", "Delete"};

        new AlertDialog.Builder(context).setTitle("Shotgun")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        } else if (item == 1) {
                            boolean deleteSuccessful = new ShotgunDAO(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Record was deleted.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Unable to delete record", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).updateRecyclerView();
                        }
                        dialog.dismiss();
                    }
                }).show();

        return false;
    }

    public void editRecord(final int shotgunId) {
        final ShotgunDAO shotgunDAO = new ShotgunDAO(context);
        Shotgun shotgun = shotgunDAO.readSingleRecord(shotgunId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.shotgun_input_form, null, false);

        final EditText editTextShotgunName =  formElementsView.findViewById(R.id.editTextShotgunName);
        final EditText editTextShotgunPrice =  formElementsView.findViewById(R.id.editTextShotgunPrice);
        final EditText editTextShotgunImageUrl =  formElementsView.findViewById(R.id.editTextShotgunImageUrl);

        editTextShotgunName.setText(shotgun.getName());
        editTextShotgunImageUrl.setText(shotgun.getImageURL());
        editTextShotgunPrice.setText(Double.toString(shotgun.getPrice()));

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String newName = editTextShotgunName.getText().toString();
                                String newImageUrl = editTextShotgunImageUrl.getText().toString();
                                Double newPrice = Double.parseDouble(editTextShotgunPrice.getText().toString());
                                Shotgun shotgun = new Shotgun(shotgunId, newName, newImageUrl, newPrice);

                                boolean updateSuccessful = shotgunDAO.update(shotgun);
                                if (updateSuccessful) {
                                    Toast.makeText(context, "Record was updated.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Update failed.", Toast.LENGTH_SHORT).show();
                                }

                                ((MainActivity) context).updateRecyclerView();
                                dialog.cancel();
                            }
                        }).show();

    }
}

