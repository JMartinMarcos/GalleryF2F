package com.practica.jmm.galleryf2f.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.practica.jmm.galleryf2f.pojo.Carpetas;

import java.util.ArrayList;

/**
 * Created by sath on 3/01/17.
 */

public class BaseDatos extends SQLiteOpenHelper{

    private Context context;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCtreateTabMascotas = "CREATE TABLE " + ConstantesBaseDatos.TABLE_CARPETA + "( " + ConstantesBaseDatos.TABLE_CARPETA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                           ConstantesBaseDatos.TABLE_CARPETA_NOMBRE + " TEXT, "+ ConstantesBaseDatos.TABLE_CARPETA_PATH + " TEXT, " + ConstantesBaseDatos.TABLE_CARPETA_FOTO + " INTEGER)";
        db.execSQL(queryCtreateTabMascotas);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
           db.execSQL("DROP TABLE " + ConstantesBaseDatos.TABLE_CARPETA);
           onCreate(db);
    }

    public ArrayList<Carpetas> obtenerTodasCarpetas(){


        ArrayList<Carpetas> carpetas = new ArrayList<>();

        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_CARPETA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(query, null);
        while (cur.moveToNext()){
            Carpetas carpeta = new Carpetas();
            carpeta.setId(cur.getString(0));
            carpeta.setNombre(cur.getString(1));
            carpeta.setPath(cur.getString(2));
            carpeta.setIcono(cur.getInt(3));
            carpetas.add(carpeta);
        }

        db.close();

        return carpetas;
    }

    public void InsertarCarpeta(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_CARPETA,null,contentValues);
        db.close();
    }

    public boolean hayCarpetas(){

        boolean rep = true;

        String query = "SELECT COUNT(*) FROM " + ConstantesBaseDatos.TABLE_CARPETA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(query, null);
        while (cur.moveToNext()){
            int i = cur.getInt(0);
            if(i>0){
                rep = false;
            }
        }
        return rep;
    }

    public void borrarCarpeta(int id){

        String query = "DELETE FROM " + ConstantesBaseDatos.TABLE_CARPETA + " WHERE " + ConstantesBaseDatos.TABLE_CARPETA_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

}
