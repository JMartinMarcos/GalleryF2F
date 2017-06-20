package com.practica.jmm.galleryf2f.fragments;

import com.practica.jmm.galleryf2f.adapter.ArchivosAdapter;
import com.practica.jmm.galleryf2f.pojo.Carpetas;

import java.util.ArrayList;

/**
 * Created by sath on 19/06/17.
 */

public interface ITreeFilesRecycler {

    public void generaLinearLayaut();
    public ArchivosAdapter crearAdaptador(ArrayList<Carpetas> carpetas);
    public void inicializaeAdaptador(ArchivosAdapter gAdaptador);
}
