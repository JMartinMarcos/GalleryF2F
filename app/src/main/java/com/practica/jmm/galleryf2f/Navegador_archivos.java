package com.practica.jmm.galleryf2f;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.practica.jmm.galleryf2f.adapter.ArchivosAdapter;
import com.practica.jmm.galleryf2f.adapter.GallAdapter;
import com.practica.jmm.galleryf2f.db.BaseDatos;
import com.practica.jmm.galleryf2f.db.ConstantesBaseDatos;
import com.practica.jmm.galleryf2f.fragments.IRecycler;
import com.practica.jmm.galleryf2f.pojo.Carpetas;
import com.practica.jmm.galleryf2f.pojo.ConstructorCarpetas;
import com.practica.jmm.galleryf2f.pojo.Foto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class Navegador_archivos extends BaseActivity{
    @Bind(R.id.toolbar2)
    Toolbar toolbar;
    @Bind(R.id.lst_navegador)
    RecyclerView listaArch;
    @Bind(R.id.btnGuardarGall)
    Button guardaCarpeta;
    @Bind(R.id.imv_ico_toolbar2)
    ImageView imvToolbar2;
    @Bind(R.id.txv_toolbar2)
    TextView txvToolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   toolbar.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        generaLinearLayaut();

        ArchivosAdapter adaptador;
        final Bundle bundle = getIntent().getExtras();

        if (bundle == null){
            ArrayList<Carpetas> carpetas = new ArrayList<>();

            /*
            List<StorageDivs.StorageInfo> listaDiv;
            StorageDivs divs = new StorageDivs();

            listaDiv = divs.getStorageList();
            Log.d("LOQUESEA:", "ESO: " + listaDiv );
            for(int i =0;i<listaDiv.size();i++){
                Carpetas carp = new Carpetas();
                carp.setPath(listaDiv.get(i).path);
                carp.setNombre(listaDiv.get(i).getDisplayName());
                carp.setIcono(R.drawable.hdd_usb_256);
                carpetas.add(carp);
            }
*/

            GetAllSds sds = new GetAllSds();
            carpetas = sds.listaSds();

            adaptador = crearAdaptador(carpetas);

            guardaCarpeta.setVisibility(View.INVISIBLE);
        }else{
            adaptador = crearAdaptador(cargaDirectorio());
            guardaCarpeta.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(R.drawable.open_folder_40)
                    .into(imvToolbar2);
            txvToolbar2.setText(bundle.getString("padre"));
        }
        inicializaeAdaptador(adaptador);

        guardaCarpeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstructorCarpetas carpet = new ConstructorCarpetas(getApplicationContext());
                BaseDatos db = new BaseDatos(getApplicationContext());
                ContentValues values = new ContentValues();
                File fil = new File(bundle.getString("padre"));
                values.put(ConstantesBaseDatos.TABLE_CARPETA_NOMBRE, fil.getName());
                values.put(ConstantesBaseDatos.TABLE_CARPETA_PATH, fil.getAbsolutePath());
                values.put(ConstantesBaseDatos.TABLE_CARPETA_FOTO, R.drawable.open_folder_40);
                carpet.insertarCarpeta(db,values);

                 Snackbar.make(view, "Carpeta guardada en menu de navegacion", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(Navegador_archivos.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.putExtras(intent);
                startActivity(intent);
                finish();
            }
        });
    }

    private ArrayList<Carpetas> cargaDirectorio() {

        ArrayList<Carpetas> carpetas;
        Bundle bundle = getIntent().getExtras();
        carpetas = bundle.getParcelableArrayList("carpetas");

        return  carpetas;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_navegador_archivos;
    }

    public void generaLinearLayaut() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaArch.setLayoutManager(llm);
    }

    public ArchivosAdapter crearAdaptador(ArrayList<Carpetas> carpetas) {
        ArchivosAdapter adaptador = new ArchivosAdapter(carpetas,this);
        return adaptador;
    }

    public void inicializaeAdaptador(ArchivosAdapter gAdaptador) {
        listaArch.setAdapter(gAdaptador);
    }
}
