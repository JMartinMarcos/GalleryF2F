package com.practica.jmm.galleryf2f.pojo;

import android.content.ContentValues;
import android.content.Context;
import com.practica.jmm.galleryf2f.FiltroArch;
import com.practica.jmm.galleryf2f.GetAllSds;
import com.practica.jmm.galleryf2f.R;
import com.practica.jmm.galleryf2f.db.BaseDatos;
import com.practica.jmm.galleryf2f.db.ConstantesBaseDatos;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by sath on 1/05/17.
 */

public class ConstructorCarpetas {

    private Context contexto;

    public ConstructorCarpetas(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Carpetas> obtenerDatos(){
        BaseDatos db = new BaseDatos(contexto);
        if(db.hayCarpetas()){
            cargaInicialCarpetas(db);
        }
        return db.obtenerTodasCarpetas();
    }

    private void cargaInicialCarpetas(BaseDatos db) {
        GetAllSds sds = new GetAllSds();
        ArrayList<Carpetas> carpetas = sds.listaSds();
        int i=0;
        int icon = R.drawable.hdd_graphite_server_b_256;

        for(Carpetas carp:carpetas){
            File ruta = new File(carp.getNombre());
            String prefijo = null;

            if(ruta.getName().equals("0")||ruta.getName().equals("1")){
                prefijo = "SdCard: ";
            }else{
               // prefijo = ruta.getName() + ": ";
                prefijo = "";
            }

            if (i>0) icon =R.drawable.hdd_usb_256;
            i++;

            findRootPathSD(carp.getPath(),prefijo,icon,db);
        }
    }

    public void insertarCarpeta(BaseDatos db, ContentValues values){
        db.InsertarCarpeta(values);
    }
    public void quitarCarpeta(BaseDatos db, int id){
        db.borrarCarpeta(id);
    }


    public void findRootPathSD(String rutaBase, String prefijo,int icon,BaseDatos db) {

        File ruta = new File(rutaBase);
        boolean cargaRuta = true;

        for (File h:ruta.listFiles(new FiltroArch())) {
            if (h.isDirectory()) {
                findRootPathSD(h.getAbsolutePath(), prefijo, icon, db);
            } else {
                if (cargaRuta) {
                    cargaRutaBD(ruta, prefijo,icon, db);
                    cargaRuta = false;
                }
            }
        }
    }

    public void cargaRutaBD(File ruta, String prefijo,int icon, BaseDatos db){

        ContentValues values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_CARPETA_NOMBRE, prefijo + ruta.getName());
        values.put(ConstantesBaseDatos.TABLE_CARPETA_PATH, ruta.getAbsolutePath());
        values.put(ConstantesBaseDatos.TABLE_CARPETA_FOTO, icon);
        db.InsertarCarpeta(values);
    }
}
