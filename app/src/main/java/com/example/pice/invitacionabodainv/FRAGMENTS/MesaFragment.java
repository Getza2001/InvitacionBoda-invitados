package com.example.pice.invitacionabodainv.FRAGMENTS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pice.invitacionabodainv.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class MesaFragment extends Fragment {

    View view;
    LinearLayout llNUmMesa;
    CardView cvLeyenda;
    ImageButton btnQR;
    private static final String PREFS_KEY = "mispreferencias";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    TextView tvMesa;
    String correo, password;
    LinearLayout contenedor;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mesa, container, false);

        llNUmMesa = view.findViewById(R.id.llNumMesa);
        cvLeyenda = view.findViewById(R.id.cvLeyenda);
        btnQR = view.findViewById(R.id.btnQR);
        tvMesa = view.findViewById(R.id.tvMesa);


        settings = Objects.requireNonNull(getContext()).getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        boolean asistioBoda = settings.getBoolean("asistiraBoda", false);
        Toast.makeText(getContext(), "Valor: " + asistioBoda, Toast.LENGTH_SHORT).show();
        int mesa = settings.getInt("mesa", 0);
        correo = settings.getString("correo", "");
        password = settings.getString("password", "");

        if (asistioBoda){
            llNUmMesa.setVisibility(View.VISIBLE);
            cvLeyenda.setVisibility(View.GONE);
            tvMesa.setText("#" + mesa);
            btnQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cargarMesa();
                }
            });
        }else{
            llNUmMesa.setVisibility(View.GONE);
            cvLeyenda.setVisibility(View.VISIBLE);
        }

        return view;
    }


    public void cargarMesa(){

        final RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        String url = "http://invitacionaboda.com/WebService/loginInvitadoExt.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onResponse(String response) {

                Toast.makeText(getContext(), "Entro", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject objResponse = new JSONObject(response);
                    int code = Integer.parseInt(objResponse.getString("code"));
                    String message = String.valueOf(objResponse.getString("message"));

                    if (code == 700){
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }else if (code == 505){
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }else if (code == 404){
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }else if (code == 201){

                        String datosInvitados = String.valueOf(objResponse.getString("invitado"));
                        JSONObject objInvitados = new JSONObject(datosInvitados);

                        int mesa;

                        String mesaString = String.valueOf(objInvitados.getString("mesa"));
                        if (mesaString.equals("")){
                            mesa = 0;
                        }else{
                            mesa = Integer.parseInt(objInvitados.getString("mesa"));
                        }
                        boolean asistioBoda;
                        if (objInvitados.getString("asistioBoda").equals("1")){
                            asistioBoda = true;
                        }else{
                            asistioBoda = false;
                        }

                        /*Init save preferences*/
                        settings = Objects.requireNonNull(getContext()).getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
                        editor = settings.edit();
                        editor.putInt("mesa", mesa);
                        editor.putBoolean("asistiraBoda", asistioBoda);
                        editor.apply();
                        /*End save preferences*/

                        //assert getFragmentManager() != null;
                        //getFragmentManager().beginTransaction().remove(Objects.requireNonNull(getFragmentManager().findFragmentById(R.id.contenedor))).commit();

                    }

                } catch (Throwable json) {
                    Log.e("Error Json: ", "Could not parse malformed JSON: \"" + json + "\"");
                    Toast.makeText(getContext(), "Hemos tenido un probelma interno.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error: "+error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<>();
                parametros.put("correo", correo);
                parametros.put("password", password);
                return parametros;
            }
        };

        requestQueue.add(stringRequest);

    }


}
