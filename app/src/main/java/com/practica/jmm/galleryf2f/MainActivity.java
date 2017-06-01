package com.practica.jmm.galleryf2f;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.practica.jmm.galleryf2f.adapter.NavAdapterRV;
import com.practica.jmm.galleryf2f.adapter.SampleDivider;
import com.practica.jmm.galleryf2f.decoration.HeaderDecoration;
import com.practica.jmm.galleryf2f.fragments.RecyclerFragment;
import com.practica.jmm.galleryf2f.pojo.Carpetas;
import com.practica.jmm.galleryf2f.pojo.ConstructorCarpetas;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import butterknife.Bind;

import static android.app.Notification.EXTRA_TITLE;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.rv_navigator)
    RecyclerView listaArch;

    private static final String EXTRA_IMAGE = "com.antonioleiva.materializeyourapp.extraImage";
    private static final String EXTRA_TITLE = "com.antonioleiva.materializeyourapp.extraTitle";
    private CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
/*
        GetAllSds sd = new GetAllSds();

        ArrayList<String> pathSD = sd.obtenerRutas();
         for(String s:pathSD){
            VariablesGlobales.PATH_RAIZ_EXTERNAL_SD = s;
        }
*/
        solicitarPermisos();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        //       toolbar.setVisibility(View.INVISIBLE);

//        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
//        supportPostponeEnterTransition();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String itemTitle = getIntent().getStringExtra(EXTRA_TITLE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        generaLinearLayaut();
        inicializaeAdaptador(crearAdaptador(cargaDirectorio()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Navegador_archivos.class);
                startActivity(intent);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

       // navigationView.setNavigationItemSelectedListener(this);
        setFragment();

    }


    public void solicitarPermisos(){
        final int PERMISO_STORAGE_READ = 1;
        final int MI_PERMISO_STORAGE2 = 2;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        //    SystemClock.sleep(5000);

            if (ContextCompat.checkSelfPermission(getBaseContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISO_STORAGE_READ);
                SystemClock.sleep(5000);
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISO_STORAGE_READ);
                    SystemClock.sleep(5000);
                }
            }
        }

/*

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d("Permiso Storage Read", "Perrmisos");
            } else {
                //PRIMERA VEZ!!!!!!!!!
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MI_PERMISO_STORAGE);
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.d("Permiso Storage Write", "Perrmisos");
            } else {
                //PRIMERA VEZ!!!!!!!!!
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, MI_PERMISO_STORAGE2);
            }
        }
        */
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFragment() {
        android.app.FragmentManager fragmentManager;
        android.app.FragmentTransaction fragmentTransaction;
        fragmentManager = getFragmentManager();
        //getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        RecyclerFragment iRecycler = new RecyclerFragment();

        fragmentTransaction.replace(R.id.content_main, iRecycler);
        fragmentTransaction.commit();
    }

    private ArrayList<Carpetas> cargaDirectorio() {

        ArrayList<Carpetas> carpetas;

        ConstructorCarpetas db = new ConstructorCarpetas(this);
        carpetas=db.obtenerDatos();

        return  carpetas;
    }

    public void generaLinearLayaut() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaArch.setHasFixedSize(true);
        listaArch.setLayoutManager(llm);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        listaArch.addItemDecoration(itemDecoration);
    }

    public NavAdapterRV crearAdaptador(ArrayList<Carpetas> carpetas) {
        NavAdapterRV adaptador = new NavAdapterRV(carpetas,this);
        return adaptador;
    }

    public void inicializaeAdaptador(NavAdapterRV gAdaptador) {
        listaArch.setAdapter(gAdaptador);

        listaArch.addItemDecoration(HeaderDecoration.with(listaArch)
                .inflate(R.layout.nav_header_main)
                .parallax(0.2f)
                .dropShadowDp(4)
                .build());

//        recyclerHeader.attachTo(listaArch);

    }
}