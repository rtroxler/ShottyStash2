package com.ryantroxler.shottystash;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by ryantroxler on 7/9/17.
 */

public class OnClickListenerDeposit implements View.OnClickListener {
    Context context;

    @Override
    public void onClick(View view) {
        context = view.getContext();

        FloatingActionMenu fam = (FloatingActionMenu) ((MainActivity) context).findViewById(R.id.fam);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.transaction_form, null, false);

        final EditText editTransactionAmount = formElementsView.findViewById(R.id.editTransactionAmount);

        fam.close(false);
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("New Deposit")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Double price = Double.parseDouble(editTransactionAmount.getText().toString());

                                ObjectTransaction transaction = new ObjectTransaction(price);

                                boolean createSuccessful = new TableControllerTransaction(context).create(transaction);

                                if (createSuccessful) {
                                    Toast.makeText(context, "Information was saved!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Save failed. :(", Toast.LENGTH_SHORT).show();
                                }

                                ((MainActivity) context).updateBalance();
                            }
                        }).show();
    }
}
