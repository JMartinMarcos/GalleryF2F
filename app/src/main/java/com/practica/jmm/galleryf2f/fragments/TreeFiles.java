package com.practica.jmm.galleryf2f.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.practica.jmm.galleryf2f.FiltroArch;
import com.practica.jmm.galleryf2f.GetAllSds;
import com.practica.jmm.galleryf2f.R;
import com.practica.jmm.galleryf2f.adapter.ArchivosAdapter;
import com.practica.jmm.galleryf2f.adapter.GallAdapter;
import com.practica.jmm.galleryf2f.pojo.Carpetas;
import com.practica.jmm.galleryf2f.pojo.Foto;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TreeFiles extends Fragment implements ITreeFilesRecycler{

    private RecyclerView listaArch;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public TreeFiles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TreeFiles.
     */
    // TODO: Rename and change types and number of parameters
    public static TreeFiles newInstance(String param1, String param2) {
        TreeFiles fragment = new TreeFiles();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tree_files, container, false);

        listaArch = (RecyclerView) v.findViewById(R.id.lst_navegador);

        ArchivosAdapter adaptador;
        if (mParam1.isEmpty()){
            ArrayList<Carpetas> carpetas;

            GetAllSds sds = new GetAllSds();
            carpetas = sds.listaSds();

            adaptador = crearAdaptador(carpetas);

//            guardaCarpeta.setVisibility(View.INVISIBLE);
        }else{
            adaptador = crearAdaptador(verArchivosDirectorio(mParam1));
//            guardaCarpeta.setVisibility(View.VISIBLE);

            ImageView img = (ImageView) getActivity().findViewById(R.id.imv_ico_toolbar2);
            TextView txvToolbar2 = (TextView) getActivity().findViewById(R.id.txv_toolbar2);
            Glide.with(this)
                    .load(R.drawable.open_folder_40)
                    .into(img);
            txvToolbar2.setText(mParam1);

        }
        inicializaeAdaptador(adaptador);
        generaLinearLayaut();
        return v;
    }
    @Override
    public void generaLinearLayaut() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaArch.setLayoutManager(llm);
    }
    @Override
    public ArchivosAdapter crearAdaptador(ArrayList<Carpetas> carpetas) {
        ArchivosAdapter adaptador = new ArchivosAdapter(carpetas,getActivity(),getContext());
        return adaptador;
    }
    @Override
    public void inicializaeAdaptador(ArchivosAdapter gAdaptador) {
        listaArch.setAdapter(gAdaptador);
    }


    private ArrayList<Carpetas> verArchivosDirectorio(String rutaDirectorio) {

        ArrayList<Carpetas> listaNombresArchivos = new ArrayList<>();
        ArrayAdapter<String> adaptador;
        List<String> listaRutasArchivos = new ArrayList<>();
        List<String> listaRutasDir      = new ArrayList<>();

        File directorioActual = new File(rutaDirectorio);
        File[] listaArchivos = directorioActual.listFiles(new FiltroArch());

        Carpetas carpeta = new Carpetas();

        int x = 0;

        carpeta.setPath(directorioActual.getParent());
        carpeta.setIcono(R.drawable.arrow_return_right_up_48);
        carpeta.setNombre(getString(R.string.back_arch));
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
            // Recorredos la lista de archivos ordenada para crear la lista de los nombres
            // de los archivos que mostraremos en el listView
            for (int i = x; i < listaRutasArchivos.size(); i++){
                File archivo = new File(listaRutasArchivos.get(i));
                Carpetas ls = new Carpetas();
                if (archivo.isFile()) {
                    ls.setIcono(R.drawable.bmp_file_256);
                } else {
                    ls.setIcono(R.drawable.folder_blue);
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

        return listaNombresArchivos;
    }

}
