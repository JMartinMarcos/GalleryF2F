package com.practica.jmm.galleryf2f.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.practica.jmm.galleryf2f.pojo.Carpetas;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.graphics.Color.parseColor;

/**
 * Created by sath on 18/04/17.
 */

public class ArchivosAdapter extends RecyclerView.Adapter<ArchivosAdapter.ArchivoViewHolder> {

    ArrayList<Carpetas> gestorArchivos;
    Activity activity;
    GetAllSds sds = new GetAllSds();


    public ArchivosAdapter(ArrayList<Carpetas> gestorArchivos, Activity activity) {
        this.gestorArchivos = gestorArchivos;
        this.activity = activity;
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

                if (archivo.isFile()) {
                    Toast.makeText(activity,
                            "Has seleccionado el archivo: " + archivo.getName(),
                            Toast.LENGTH_LONG).show();
                } else {
                    // Si no es un directorio mostramos todos los archivos que contiene
                    if ((VariablesGlobales.PATH_RAIZ_INTERNAL.indexOf(carpeta.getPath().toString()) !=-1 || VariablesGlobales.PATH_RAIZ_EXTERNAL_SD.indexOf(carpeta.getPath().toString())!=-1)&&carpeta.getNombre().equals("../")
                            && !VariablesGlobales.PATH_RAIZ_INTERNAL.equals(carpeta.getPath()) && !VariablesGlobales.PATH_RAIZ_EXTERNAL_SD.equals(carpeta.getPath())) {

                        carpetas = sds.listaSds();

                    } else{
                        carpetas = verArchivosDirectorio(archivo.getAbsolutePath());
                    }
                    Intent intent = new Intent(activity, Navegador_archivos.class).addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("carpetas", carpetas);
                    bundle.putString("padre", archivo.getAbsolutePath());

                    intent.putExtras(bundle);
                    if(holder.ruta.getText().toString().equals("../"))
                        activity.finish();
                    activity.startActivity(intent);
         //           activity.finish();
                }

            }
        });
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

    private ArrayList<Carpetas> verArchivosDirectorio(String rutaDirectorio) {

        ArrayList<Carpetas> listaNombresArchivos = new ArrayList<>();
        ArrayAdapter<String> adaptador;
        List<String> listaRutasArchivos = new ArrayList<>();
        List<String> listaRutasDir      = new ArrayList<>();


        //carpetaActual.setText("Estas en: " + rutaDirectorio);
        File directorioActual = new File(rutaDirectorio);
        File[] listaArchivos = directorioActual.listFiles(new FiltroArch());
//        File[] listaArchivos = directorioActual.listFiles(new FiltroImage());

        Carpetas carpeta = new Carpetas();

        int x = 0;

        // Si no es nuestro directorio raiz creamos un elemento que nos
        // permita volver al directorio padre del directorio actual

        /*
        if (rutaDirectorio.equals(VariablesGlobales.PATH_RAIZ_INTERNAL) || rutaDirectorio.equals(VariablesGlobales.PATH_RAIZ_EXTERNAL_SD)) {
            carpeta.setPath(directorioActual.getPath());
        }else{
            carpeta.setPath(directorioActual.getParent());
         }
         */

        carpeta.setPath(directorioActual.getParent());
        carpeta.setIcono(R.drawable.arrow_return_right_up_48);
        carpeta.setNombre("../");
        listaNombresArchivos.add(carpeta);
        x = 1;

        // Almacenamos las rutas de todos los archivos y carpetas del directorio
        for (File archivo : listaArchivos) {
            if(archivo.isDirectory())
                listaRutasArchivos.add(archivo.getPath());
            else
            listaRutasDir.add(archivo.getPath());
        }

        // Ordenamos la lista de archivos para que se muestren en orden alfabetico
        Collections.sort(listaRutasArchivos, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(listaRutasDir, String.CASE_INSENSITIVE_ORDER);

        for(String fich:listaRutasDir){
            listaRutasArchivos.add(fich);
        }

        // Si no hay ningun archivo en el directorio lo indicamos
        if (listaArchivos.length < 1) {
            /*
            carpeta.setPath(directorioActual.getParent());
            carpeta.setIcono(R.drawable.arrow_return_right_up_48);
            carpeta.setNombre("../");
            listaNombresArchivos.add(carpeta);
            */
        }else{
            // Recorredos la lista de archivos ordenada para crear la lista de los nombres
            // de los archivos que mostraremos en el listView
            for (int i = x; i < listaRutasArchivos.size(); i++){
                File archivo = new File(listaRutasArchivos.get(i));
                Carpetas ls = new Carpetas();
                if (archivo.isFile()) {
                    ls.setIcono(R.drawable.bmp_file_256);
                } else {
                    ls.setIcono(R.drawable.open_folder_40);
                }
                ls.setPath(archivo.getAbsolutePath());

                String nombre = archivo.getName();
                int longString = nombre.length();
                if (longString>35){
                    nombre = archivo.getName().substring(0,30) + " ....";
                }

                ls.setNombre(nombre);
                listaNombresArchivos.add(ls);
            }
        }
        return listaNombresArchivos;
     }
}
