package com.practica.jmm.galleryf2f.pojo;

import android.content.ContentValues;
import android.content.Context;

import com.practica.jmm.galleryf2f.R;
import com.practica.jmm.galleryf2f.VariablesGlobales;
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

    public void cargaInicialCarpetas(BaseDatos db){
        ContentValues values = new ContentValues();

        File intSd = new File(VariablesGlobales.PATH_INTERNAL_GALL);
        if(intSd.exists()) {

            values.put(ConstantesBaseDatos.TABLE_CARPETA_NOMBRE, "CAMARA SD INTERNA");
            values.put(ConstantesBaseDatos.TABLE_CARPETA_PATH, VariablesGlobales.PATH_INTERNAL_GALL);
            values.put(ConstantesBaseDatos.TABLE_CARPETA_FOTO, R.drawable.hdd_grey_256);
            db.InsertarCarpeta(values);
        }
        File extSd = new File(VariablesGlobales.PATH_RAIZ_EXTERNAL_SD_GALL);
        if(extSd.exists()) {

            values = new ContentValues();
            values.put(ConstantesBaseDatos.TABLE_CARPETA_NOMBRE, "CAMARA SD EXTERNA");
            values.put(ConstantesBaseDatos.TABLE_CARPETA_PATH, VariablesGlobales.PATH_RAIZ_EXTERNAL_SD_GALL);
            values.put(ConstantesBaseDatos.TABLE_CARPETA_FOTO, R.drawable.micro_sd_48);
            db.InsertarCarpeta(values);
        }

        File wshApp = new File(VariablesGlobales.PATH_WHATSAPP_GALL);
        if(wshApp.exists()) {

            values = new ContentValues();
            values.put(ConstantesBaseDatos.TABLE_CARPETA_NOMBRE, "WHATSAPP");
            values.put(ConstantesBaseDatos.TABLE_CARPETA_PATH, VariablesGlobales.PATH_WHATSAPP_GALL);
            values.put(ConstantesBaseDatos.TABLE_CARPETA_FOTO, R.drawable.whatsapp_50);
            db.InsertarCarpeta(values);
        }
/*
        values = new ContentValues();
        values.put(ConstantesBaseDatos.TABLE_CARPETA_NOMBRE, "INSTAGRAM" );
        values.put(ConstantesBaseDatos.TABLE_CARPETA_PATH, VariablesGlobales.PATH_WHATSAPP_GALL );
        values.put(ConstantesBaseDatos.TABLE_CARPETA_FOTO, R.drawable.instagram_48);
        db.InsertarCarpeta(values);
        */
    }

    public void insertarCarpeta(BaseDatos db, ContentValues values){
        db.InsertarCarpeta(values);
    }
    public void quitarCarpeta(BaseDatos db, int id){
        db.borrarCarpeta(id);
    }
}
