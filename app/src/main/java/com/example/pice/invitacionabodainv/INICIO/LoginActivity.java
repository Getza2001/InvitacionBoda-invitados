package com.example.pice.invitacionabodainv.INICIO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pice.invitacionabodainv.CLASES.Globals;
import com.example.pice.invitacionabodainv.HomeActivity;
import com.example.pice.invitacionabodainv.R;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText et_usuario, et_password;
    Button btn_login;
    ProgressDialog progress;
    boolean ok = false;

    private static final String PREFS_KEY = "mispreferencias";
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        boolean validate = settings.getBoolean("session", false);

        if (validate){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        btn_login = (Button) findViewById(R.id.btn_iniciar);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password = (EditText) findViewById(R.id.et_password);

        et_usuario.setText("cjmc12@hotmail.com");
        et_password.setText("Ceci2215");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();
                login.execute();

            }
        });
    }

    public class Login extends AsyncTask<String, String, String> {

        String mensaje = "";
        String usuario_s = et_usuario.getText().toString();
        String contrasena_s = et_usuario.getText().toString();

        @Override
        protected String doInBackground(String... params) {

            if (usuario_s.trim().equals("")||contrasena_s.trim().equals("")) {
                mensaje = "Completa todos los campos";
            }
            return mensaje;
        }

        @Override
        protected void onPostExecute(String r) {
            if(!r.equals("")) {
                Toast.makeText(LoginActivity.this, r, Toast.LENGTH_SHORT).show();
            }
            else {
                progress = new ProgressDialog(LoginActivity.this);
                progress.setTitle("Cargando");
                progress.setMessage("Espere por favor...");
                progress.setCancelable(false);
                progress.show();
                CargarWebService();
            }
            super.onPostExecute(r);
        }
    }

    public void CargarWebService(){
        final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        String url = "http://invitacionaboda.com/WebService/loginInvitadoExt.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Memije: ", response);

                progress.dismiss();

                try {
                    JSONObject objResponse = new JSONObject(response);
                    int code = Integer.parseInt(objResponse.getString("code"));
                    String message = String.valueOf(objResponse.getString("message"));

                    if (code == 700){
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }else if (code == 505){
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }else if (code == 404){
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }else if (code == 201){

                        String datosInvitados = String.valueOf(objResponse.getString("invitado"));
                        JSONObject objInvitados = new JSONObject(datosInvitados);

                        int idNovios, mesa;

                        int idInvitado = Integer.parseInt(objInvitados.getString("idInvitado"));
                        String apodo = String.valueOf(objInvitados.getString("apodo"));
                        String nombre = String.valueOf(objInvitados.getString("nombre"));
                        String aPaterno = String.valueOf(objInvitados.getString("aPaterno"));
                        String aMaterno = String.valueOf(objInvitados.getString("aMaterno"));
                        String idNoviosString = String.valueOf(objInvitados.getString("idNovios"));
                        if (idNoviosString.equals("")){
                            idNovios = 0;
                        }else{
                            idNovios = Integer.parseInt(objInvitados.getString("idNovios"));
                        }
                        String mesaString = String.valueOf(objInvitados.getString("mesa"));
                        if (mesaString.equals("")){
                            mesa = 0;
                        }else{
                            mesa = Integer.parseInt(objInvitados.getString("mesa"));
                        }

                        boolean asistira = Boolean.parseBoolean(objInvitados.getString("asistira"));

                        boolean asistioBoda;
                        if (objInvitados.getString("asistioBoda").equals("1")){
                            asistioBoda = true;
                        }else{
                            asistioBoda = false;
                        }

                        String QR = String.valueOf(objInvitados.getString("QR"));

                        /*Init save preferences*/
                        settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
                        editor = settings.edit();
                        editor.putBoolean("session", true);
                        editor.putInt("idInvitados", idInvitado);
                        editor.putString("apodo", apodo);
                        editor.putString("nombre", nombre);
                        editor.putString("aPaterno", aPaterno);
                        editor.putString("aMaterno", aMaterno);
                        editor.putInt("idNovios", idNovios);
                        editor.putInt("mesa", mesa);
                        editor.putBoolean("asistira", asistira);
                        editor.putBoolean("asistiraBoda", false);
                        editor.putString("QR", QR);
                        editor.putString("correo", et_usuario.getText().toString().trim());
                        editor.putString("password", et_password.getText().toString().trim());
                        editor.apply();
                        /*End save preferences*/
                        
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    
                } catch (Throwable json) {
                    Log.e("Error Json: ", "Could not parse malformed JSON: \"" + json + "\"");
//                    System.out.println("jeisonnnn"+json);
                    Toast.makeText(LoginActivity.this, "Hemos tenido un probelma interno.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "error: "+error, Toast.LENGTH_SHORT).show();
//                System.out.println("Errroooooooorrrrrr:"+error);
                progress.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String user = et_usuario.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                Map<String, String> parametros = new HashMap<>();
                parametros.put("correo", user);
                parametros.put("password", password);
                return parametros;
            }
        };

        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
