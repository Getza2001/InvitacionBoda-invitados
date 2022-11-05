package com.example.pice.invitacionabodainv.INFO;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.pice.invitacionabodainv.R;

import java.util.Objects;

public class InvitacionabodaAcercade extends DialogFragment {

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder_2 = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater_2 = getActivity().getLayoutInflater();

        builder_2.setView(inflater_2.inflate(R.layout.fragment_acercade, null))
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder_2.create();
    }
}
