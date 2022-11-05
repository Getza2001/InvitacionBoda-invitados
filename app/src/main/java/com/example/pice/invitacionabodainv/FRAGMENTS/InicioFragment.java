package com.example.pice.invitacionabodainv.FRAGMENTS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pice.invitacionabodainv.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class InicioFragment extends Fragment {

    Button btnScannearBoleto;
    CardView cvCeremonia, cvMisa;
    View view;
    SimpleDateFormat dateFormat;
    TextView tv_day, tv_hour, tv_minute;
    String finalfinal, finalfinal1, finalfinal2;

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        btnScannearBoleto = view.findViewById(R.id.btnScannearBoleto);
        cvCeremonia = view.findViewById(R.id.cvCeremonia);
        cvMisa = view.findViewById(R.id.cvMisa);
        tv_day = view.findViewById(R.id.tv_day);
        tv_hour = view.findViewById(R.id.tv_hour);
        tv_minute = view.findViewById(R.id.tv_minute);

        cvCeremonia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialogo.setTitle("Info.");
                dialogo.setMessage("Esta opción le lleva a GoogleMaps para ver el lugar de la Ceremonia");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Si, ir al mapa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {

                        Uri uri = Uri.parse("https://www.google.com.mx/maps/place/Catedral+De+Guadalajara/@20.6895339,-103.3837518,17z/data=!3m1!4b1!4m5!3m4!1s0x8428ae3f7edd61ab:0x8415bcd5455e2af!8m2!3d20.6895289!4d-103.3815631");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });
                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        dialogo.dismiss();
                    }
                });
                dialogo.show();

            }
        });

        cvMisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialogo.setTitle("Info.");
                dialogo.setMessage("Esta opción le lleva a GoogleMaps para ver el lugar de la Misa");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Si, ir al mapa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {

                        Uri uri = Uri.parse("https://www.google.com.mx/maps/place/Joanca+Hacienda+y+Salon/@20.5431228,-103.4922414,17z/data=!3m1!4b1!4m5!3m4!1s0x842f54dc1a7f6da1:0xac05660287af5017!8m2!3d20.5431178!4d-103.4900527");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });
                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        dialogo.dismiss();
                    }
                });
                dialogo.show();

            }
        });

        btnScannearBoleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Desliza para ir a la sección de Boleto.", Toast.LENGTH_SHORT).show();

            }
        });

        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date futureDate = dateFormat.parse("2020-03-15");
            String fechaFinaltoString = (String) getCountdownText(getContext(), futureDate);
            String[] arrayfecha = fechaFinaltoString.split(",");

//            int dayValidar = Integer.parseInt(arrayfecha[0]);
            int dayValidar = 5;
            if (dayValidar >= 1 && dayValidar <= 9){
                finalfinal = "0" + arrayfecha[0];
            }else{
                finalfinal = arrayfecha[0];
            }
//            int hourValidar = Integer.parseInt(arrayfecha[1]);
            int hourValidar = 5;
            if (hourValidar >= 1 && hourValidar <= 9){
//                finalfinal1 = "0" + arrayfecha[1].toString();
                finalfinal = "0";
            }else{
                finalfinal1 = arrayfecha[1];
            }
//            int minuteValidar = Integer.parseInt(arrayfecha[2]);
            int minuteValidar = 5;
            if (minuteValidar >= 1 && minuteValidar <= 9){
//                finalfinal2 = "0" + arrayfecha[2];
                finalfinal2 = "0";
            }else{
                finalfinal2 = arrayfecha[2];
            }

            tv_day.setText(finalfinal);
            tv_hour.setText(finalfinal1);
            tv_minute.setText(finalfinal2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        return view;
    }

    public static CharSequence getCountdownText(Context context, Date futureDate) {
        StringBuilder countdownText = new StringBuilder();

        // Calculate the time between now and the future date.
        long timeRemaining = futureDate.getTime() - new Date().getTime();

        // If there is no time between (ie. the date is now or in the past), do nothing
        if (timeRemaining > 0) {
            Resources resources = context.getResources();

            // Calculate the days/hours/minutes/seconds within the time difference.
            //
            // It's important to subtract the value from the total time remaining after each is calculated.
            // For example, if we didn't do this and the time was 25 hours from now,
            // we would get `1 day, 25 hours`.
            int days = (int) TimeUnit.MILLISECONDS.toDays(timeRemaining);
            timeRemaining -= TimeUnit.DAYS.toMillis(days);
            int hours = (int) TimeUnit.MILLISECONDS.toHours(timeRemaining);
            timeRemaining -= TimeUnit.HOURS.toMillis(hours);
            int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(timeRemaining);
            timeRemaining -= TimeUnit.MINUTES.toMillis(minutes);
            int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(timeRemaining);

            // For each time unit, add the quantity string to the output, with a space.
            if (days > 0) {
                countdownText.append(resources.getQuantityString(R.plurals.days, days, days));
                countdownText.append(",");
            }
            if (days > 0 || hours > 0) {
                countdownText.append(resources.getQuantityString(R.plurals.hours, hours, hours));
                countdownText.append(",");
            }
            if (days > 0 || hours > 0 || minutes > 0) {
                countdownText.append(resources.getQuantityString(R.plurals.minutes, minutes, minutes));
                countdownText.append(",");
            }
            if (days > 0 || hours > 0 || minutes > 0 || seconds > 0) {
                countdownText.append(resources.getQuantityString(R.plurals.seconds, seconds, seconds));
                countdownText.append(",");
            }
        }

        return countdownText.toString();
    }
}
