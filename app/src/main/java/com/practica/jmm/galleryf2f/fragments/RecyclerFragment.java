package com.practica.jmm.galleryf2f.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practica.jmm.galleryf2f.FiltroImage;
import com.practica.jmm.galleryf2f.R;
import com.practica.jmm.galleryf2f.VariablesGlobales;
import com.practica.jmm.galleryf2f.adapter.GallAdapter;
import com.practica.jmm.galleryf2f.adapter.MarginDecoration;
import com.practica.jmm.galleryf2f.pojo.Foto;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.

 */
public class RecyclerFragment extends android.app.Fragment implements IRecycler{

    private RecyclerView galleryFoto;
    GallAdapter gallAdapter;

    public RecyclerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_recycler, container, false);
        galleryFoto = (RecyclerView) v.findViewById(R.id.rvGall);
        galleryFoto.addItemDecoration(new MarginDecoration(getActivity()));
        galleryFoto.setHasFixedSize(true);
       // generaGridLayaut();
        inicializaeAdaptador(crearAdaptador(cargaDirectorio()));

        return v;
    }

    @Override
    public void generaGridLayaut() {
        GridLayoutManager glm = new GridLayoutManager(getActivity(),3);
        glm.setOrientation(LinearLayoutManager.VERTICAL);
        galleryFoto.setLayoutManager(glm);
    }

    @Override
    public GallAdapter crearAdaptador(ArrayList<Foto> fotos) {
         gallAdapter = new GallAdapter(fotos,getActivity());
        return gallAdapter;
    }

    @Override
    public void inicializaeAdaptador(GallAdapter gAdaptador) {
        galleryFoto.setAdapter(gAdaptador);
    }

    public ArrayList<Foto> cargaDirectorio(){

        if (VariablesGlobales.PATH_GALL.isEmpty()) VariablesGlobales.PATH_GALL = VariablesGlobales.PATH_INTERNAL_GALL;

        String aaa = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        Log.i("Jorge", "PATH CARPETA FOTOS: " + aaa);

        ArrayList<Foto> fotos = new ArrayList<>();
        File dir = new File(VariablesGlobales.PATH_GALL);

        boolean existe = dir.exists();

        if (existe) {
            for(File f:dir.listFiles(new FiltroImage())) {
                Foto foto = new Foto();
                foto.setImagen(f.getAbsolutePath());
                foto.setNombre(f.getName());
                fotos.add(foto);
            }
        }
        return fotos;
    }

    @Override
    public void onStart() {
        super.onStart();
        gallAdapter.updateData(cargaDirectorio());
    }
}


