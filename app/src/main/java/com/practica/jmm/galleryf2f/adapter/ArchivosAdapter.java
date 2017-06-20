package com.practica.jmm.galleryf2f.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.practica.jmm.galleryf2f.FiltroArch;
import com.practica.jmm.galleryf2f.FiltroImage;
import com.practica.jmm.galleryf2f.FullscreenActivity;
import com.practica.jmm.galleryf2f.GetAllSds;
import com.practica.jmm.galleryf2f.MainActivity;
import com.practica.jmm.galleryf2f.Navegador_archivos;
import com.practica.jmm.galleryf2f.R;
import com.practica.jmm.galleryf2f.VariablesGlobales;
import com.practica.jmm.galleryf2f.fragments.TreeFiles;
import com.practica.jmm.galleryf2f.pojo.Carpetas;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.graphics.Color.parseColor;
import static com.practica.jmm.galleryf2f.VariablesGlobales.PATH_ARCH;
import static java.security.AccessController.getContext;

/**
 * Created by sath on 18/04/17.
 */

public class ArchivosAdapter extends RecyclerView.Adapter<ArchivosAdapter.ArchivoViewHolder> {

    ArrayList<Carpetas> gestorArchivos;
    Activity activity;
    private  Context context;
  //  private FragmentManager fragmentManager;

    //GetAllSds sds = new GetAllSds();


    public ArchivosAdapter(ArrayList<Carpetas> gestorArchivos, Activity activity, Context context) {
        this.gestorArchivos = gestorArchivos;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public ArchivosAdapter.ArchivoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_archivos,parent,false);
        return new ArchivoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ArchivosAdapter.ArchivoViewHolder holder, final int position) {
        final Carpetas carpeta = gestorArchivos.get(position);

        Glide.with(activity)
                .load(carpeta.getIcono())
                .into(holder.imgImagen);

        holder.ruta.setText(carpeta.getNombre());

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Carpetas> carpetas = new ArrayList<>();

                File archivo = new File(carpeta.getPath());

                final Button guardaCarpeta = (Button) activity.findViewById(R.id.btnGuardarGall);

                if (archivo.isFile()) {
                    Toast.makeText(activity,
                            "Has seleccionado el archivo: " + archivo.getName(),
                            Toast.LENGTH_LONG).show();
                } else {
                    // Si no es un directorio mostramos todos los archivos que contiene
                    if ((VariablesGlobales.PATH_RAIZ_INTERNAL.indexOf(carpeta.getPath().toString()) !=-1 || VariablesGlobales.PATH_RAIZ_EXTERNAL_SD.indexOf(carpeta.getPath().toString())!=-1)&&carpeta.getNombre().equals("../")
                            && !VariablesGlobales.PATH_RAIZ_INTERNAL.equals(carpeta.getPath()) && !VariablesGlobales.PATH_RAIZ_EXTERNAL_SD.equals(carpeta.getPath())) {

                        guardaCarpeta.setVisibility(View.INVISIBLE);
                        PATH_ARCH = "";
                    } else{
                        PATH_ARCH = carpeta.getPath();
                        guardaCarpeta.setVisibility(View.VISIBLE);
                    }
              //      ((AppCompatActivity)activity).getSupportActionBar().setTitle(PATH_ARCH);
                    setFragmentArch(PATH_ARCH,carpeta.getNombre());
                }
            }
        });
    }

    public void setFragmentArch(String arch,String nombre) {


        FragmentTransaction fragmentTransaction;
        fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        TreeFiles tfiles = TreeFiles.newInstance(arch,"");

        if(!nombre.equals(context.getString(R.string.back_arch))){
            fragmentTransaction.replace(R.id.content_arch,tfiles);
            //     if(!nombre.equals(context.getString(R.string.back_arch))){
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else{
            //activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            removePreviousFragment();
        }
    }

    private void removePreviousFragment() {
        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count > 0) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_in_left);
            //ft.hide(mCurrentFragment);
            fm.popBackStack();
            fm.executePendingTransactions();
            /*
            if (count > 1) {
                String name = fm.getBackStackEntryAt(count-2).getName();
                TreeFiles mCurrentFragment = (TreeFiles) fm.findFragmentByTag(name);
            } else {
                TreeFiles mCurrentFragment = null;
            }
            */
        }
    }

    @Override
    public int getItemCount() {
        return gestorArchivos.size();
    }


    public static class ArchivoViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgImagen;
        private TextView ruta;
        private LinearLayout linear;

        public ArchivoViewHolder(View itemView) {
            super(itemView);
            imgImagen = (ImageView) itemView.findViewById(R.id.Item_nav_archivo);
            ruta = (TextView) itemView.findViewById(R.id.text_nav_archivo);
            linear = (LinearLayout) itemView.findViewById(R.id.linear_archivo);
        }
    }


}
