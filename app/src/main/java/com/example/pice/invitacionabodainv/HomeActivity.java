package com.example.pice.invitacionabodainv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pice.invitacionabodainv.CLASES.PageAdapter;
import com.example.pice.invitacionabodainv.INFO.InvitacionabodaAcercade;
import com.example.pice.invitacionabodainv.INFO.InvitacionabodaInfo;
import com.example.pice.invitacionabodainv.INICIO.LoginActivity;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabInicio, tabMesa, tabFotos, tabBoleto;

    private static final String PREFS_KEY = "mispreferencias";
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        settings = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tablayout);

        tabInicio = findViewById(R.id.tabInicio);
        tabBoleto = findViewById(R.id.tabBoleto);
        tabMesa = findViewById(R.id.tabMesa);
        //tabMusica = findViewById(R.id.tabMusica);
        tabFotos = findViewById(R.id.tabFotos);
        //tabBebida = findViewById(R.id.tabBebida);

        viewPager = findViewById(R.id.viewPager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {

                    //Toast.makeText(HomeActivity.this, "Tab 1", Toast.LENGTH_SHORT).show();

                } else if (tab.getPosition() == 1) {

                    //Toast.makeText(HomeActivity.this, "Tab 2", Toast.LENGTH_SHORT).show();

                } else if (tab.getPosition() == 2) {

                    //Toast.makeText(HomeActivity.this, "Tab 3", Toast.LENGTH_SHORT).show();

                } else if (tab.getPosition() == 3) {

                    //Toast.makeText(HomeActivity.this, "Tab 4", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_salir) {

            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Salir");
            dialogo.setMessage("¿Seguro que quieres cerrar la sesión actual?");
            dialogo.setCancelable(false);
            dialogo.setPositiveButton("Si, cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {

                    SharedPreferences settings = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
                    settings.edit().clear().apply();

                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
            dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    dialogo.dismiss();
                }
            });
            dialogo.show();

        } else if (id == R.id.action_configuraciones) {
            Toast.makeText(this, "Aún no está habilitada esta parte.", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.action_info) {
            FragmentManager fragmentManager = Objects.requireNonNull(getSupportFragmentManager());
            InvitacionabodaInfo dialogo = new InvitacionabodaInfo();
            dialogo.show(fragmentManager, "tagAlerta");
        }else if (id == R.id.action_acercade) {
            FragmentManager fragmentManager_2 = Objects.requireNonNull(getSupportFragmentManager());
            InvitacionabodaAcercade dialogo_acercade = new InvitacionabodaAcercade();
            dialogo_acercade.show(fragmentManager_2, "tagAlerta_2");
        }
        return super.onOptionsItemSelected(item);
    }

}