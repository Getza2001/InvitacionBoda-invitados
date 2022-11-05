package com.example.pice.invitacionabodainv.FRAGMENTS;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class FotosFragment extends Fragment {
    CardView cvCamaraFoto;
    View view;
    LinearLayout ll_content_options;
    ImageView imagen;
    Intent intent;
    final static int cons =0;
    Bitmap bmp;
    LinearLayout ll_content_save_photo;
    String foto_base64 ="";
    ProgressDialog progress;
    Button btnGuardar, btnReintentar, btnSalir, btnTakePicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fotos, container, false);
        checkCameraPermission();
        imagen = view.findViewById(R.id.imagen);
        ll_content_options = view.findViewById(R.id.ll_content_options);
        ll_content_save_photo = view.findViewById(R.id.ll_content_save_photo);
        ll_content_options.setVisibility(View.VISIBLE);
        ll_content_save_photo.setVisibility(View.GONE);
        cvCamaraFoto = (CardView) view.findViewById(R.id.cvCamaraFoto);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnReintentar = view.findViewById(R.id.btnReintentar);
        btnSalir = view.findViewById(R.id.btnSalir);
        btnTakePicture = view.findViewById(R.id.btnTakePicture);

        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, cons);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_content_save_photo.setVisibility(View.GONE);
                ll_content_options.setVisibility(View.VISIBLE);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SubirFoto subirfoto = new SubirFoto();
                subirfoto.execute();

            }
        });

        cvCamaraFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, cons);
            }
        });

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, cons);
            }
        });

        return view;
    }
    public class SubirFoto extends AsyncTask<String, String, String> {

        String mensaje = "";

        @Override
        protected String doInBackground(String... params) {

            if (foto_base64.equals("")) {
                mensaje = "Completa todos los campos";
            }
            return mensaje;
        }

        @Override
        protected void onPostExecute(String r) {
            if(!r.equals("")) {
                Toast.makeText(getContext(), r, Toast.LENGTH_SHORT).show();
            }
            else {
                progress = new ProgressDialog(getContext());
                progress.setTitle("Cargando");
                progress.setMessage("Subiendo foto...");
                progress.setCancelable(false);
                progress.show();
                WSSubirImg();
            }
            super.onPostExecute(r);
        }
    }

    private void WSSubirImg() {
        final RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        String url_subirfoto = "http://supermoteles.com/WebService/subirFoto.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_subirfoto, new Response.Listener<String>() {
            @Override
            public void onResponse(String json) {

                progress.dismiss();

                try {
                    JSONObject obj = new JSONObject(json);
                    int code = Integer.parseInt(obj.getString("code"));
                    String message = String.valueOf(obj.getString("message"));

                    if (code == 201){
                        ll_content_save_photo.setVisibility(View.GONE);
                        ll_content_options.setVisibility(View.VISIBLE);
                    }

                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                } catch (Throwable t) {
                    Log.e("Error Json: ", "Could not parse malformed JSON: \"" + json + "\"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("imagen", foto_base64);
                parametros.put("idInvitado", "3");
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions((Activity) Objects.requireNonNull(getContext()), new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode== Activity.RESULT_OK){

            ll_content_options.setVisibility(View.GONE);
            ll_content_save_photo.setVisibility(View.VISIBLE);
            Bundle ext = data.getExtras();
            assert ext != null;
            bmp = (Bitmap)ext.get("data");
            assert bmp != null;
            foto_base64 = covertirImgString(bmp);
            imagen.setImageBitmap(bmp);
        }
    }

    private String covertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;

    }
}
