package com.practica.jmm.galleryf2f;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.practica.jmm.galleryf2f.db.BaseDatos;
import com.practica.jmm.galleryf2f.db.ConstantesBaseDatos;
import com.practica.jmm.galleryf2f.fragments.TreeFiles;
import com.practica.jmm.galleryf2f.pojo.ConstructorCarpetas;

import java.io.File;

import butterknife.Bind;

import static com.practica.jmm.galleryf2f.VariablesGlobales.PATH_ARCH;

public class Navegador_archivos extends BaseActivity{
    @Bind(R.id.toolbar2)
    Toolbar toolbar;
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
        guardaCarpeta.setVisibility(View.INVISIBLE);

        setFragmentArch();

        guardaCarpeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstructorCarpetas carpet = new ConstructorCarpetas(getApplicationContext());
                BaseDatos db = new BaseDatos(getApplicationContext());
                ContentValues values = new ContentValues();
                File fil = new File(PATH_ARCH);
                values.put(ConstantesBaseDatos.TABLE_CARPETA_NOMBRE, fil.getName());
                values.put(ConstantesBaseDatos.TABLE_CARPETA_PATH, fil.getAbsolutePath());
                values.put(ConstantesBaseDatos.TABLE_CARPETA_FOTO, R.drawable.folder_picture);
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setFragmentArch() {

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        TreeFiles tfiles = TreeFiles.newInstance("","");
//        fragmentTransaction.replace(R.id.content_arch,tfiles);
        fragmentTransaction.add(R.id.content_arch,tfiles);

        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_navegador_archivos;
    }


}
