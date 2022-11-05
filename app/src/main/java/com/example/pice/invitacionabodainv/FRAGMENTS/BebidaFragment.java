package com.example.pice.invitacionabodainv.FRAGMENTS;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pice.invitacionabodainv.R;

import java.util.Objects;

public class BebidaFragment extends Fragment {

    View view;
    String[] listItems;
    CardView cvSinAlcohol, cvConAlcohol;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bebida, container, false);

        cvSinAlcohol = view.findViewById(R.id.cvSinAlcohol);
        cvConAlcohol = view.findViewById(R.id.cvConAlcohol);

        cvSinAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems = new String[]{"Agua", "Jugo", "Refresco", "Agua fresca", "Limonada"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder.setTitle("Bebidas sin alcohol");
                builder.setIcon(R.drawable.ic_menu_bebida);

                boolean[] checkedItems = new boolean[]{false, false, false, false, false}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(getContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + isChecked, Toast.LENGTH_LONG).show();
                    }
                });

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        cvConAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems = new String[]{"Cerveza", "Tequila", "Vino", "Agua loca", "Vino blanco"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder.setTitle("Bebidas con alcohol");
                builder.setIcon(R.drawable.ic_menu_bebida);

                boolean[] checkedItems = new boolean[]{false, false, false, false, false}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(getContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + isChecked, Toast.LENGTH_LONG).show();
                    }
                });

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        return view;
    }

}
