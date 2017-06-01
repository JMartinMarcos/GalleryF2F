package com.practica.jmm.galleryf2f.fragments;

import com.practica.jmm.galleryf2f.adapter.GallAdapter;
import com.practica.jmm.galleryf2f.pojo.Foto;

import java.util.ArrayList;

/**
 * Created by sath on 17/03/17.
 */

public interface IRecycler {

    public void generaGridLayaut();

    public GallAdapter crearAdaptador(ArrayList<Foto> fotos);

    public void inicializaeAdaptador(GallAdapter gAdaptador);
}
