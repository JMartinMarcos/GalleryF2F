package com.practica.jmm.galleryf2f.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sath on 17/03/17.
 */

public class Foto implements Parcelable{
    public Foto() {
    }

    private String imagen;
    private String nombre;

    public Foto(Parcel in) {
        imagen = in.readString();
        nombre = in.readString();
    }

    public static final Creator<Foto> CREATOR = new Creator<Foto>() {
        @Override
        public Foto createFromParcel(Parcel in) {
            return new Foto(in);
        }

        @Override
        public Foto[] newArray(int size) {
            return new Foto[size];
        }
    };

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imagen);
        parcel.writeString(nombre);
    }
}
