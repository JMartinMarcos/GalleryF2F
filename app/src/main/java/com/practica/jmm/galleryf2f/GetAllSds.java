package com.practica.jmm.galleryf2f;

import com.practica.jmm.galleryf2f.pojo.Carpetas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by sath on 20/04/17.
 */

public class GetAllSds {



    private void GetAllSds() {
    }

    public  ArrayList<Carpetas> listaSds() {
        ArrayList<String> paths = new ArrayList<String>(); // Array de todas las rutas
        paths = obtenerRutas();
        return getCarpetasSds(paths);
    }

    public ArrayList<String> obtenerRutas(){

        ArrayList<String> tmp = new ArrayList<String>(); // Array de todas las rutas

        String rutas[] = {"/mnt/", "/storage/"}; // Rutas posibles

        for (int i = 0; i < rutas.length; i++) {
            File file = new File(rutas[i]);// Ruta actual ( listamos de uno a uno )
            if (file.isHidden() || (!file.exists() && file.canRead())) // Si no existe pasamos a la siguiente
                continue;
            System.out.println("Ruta : " + rutas[i]); // Imprimimos para "depurar"
            String f[] = file.list(); // Contenido de ruta actual
            for (int o = 0; o < f.length; o++) { // Recorremos el contenido de la ruta actual
                if (f[o].indexOf("sd") != -1 || f[o].indexOf("BD") != -1) { // Vemos si contiene sd en el nombre si es asi es almacenamiento
                    String ruta = rutas[i] + f[o]; // Creamos una Ruta,esta ruta es la original
                    File fd = new File(ruta); // Creamos un nuevo File para evitar rutas repetidas basadas en symlinks
                    String toAdd;
                    try {
                        System.out.println("Canocial :"
                                + fd.getCanonicalPath().toString()); // Esta seria la ruta original

                        toAdd = fd.getCanonicalPath().toString();
                       // toAdd = fd.getAbsolutePath();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        toAdd = null;
                        continue;
                    }
                    if (!tmp.contains(toAdd)) { // Si se repitiera no lo añadimos
                        System.out.println("A añadir :" + toAdd);
                        tmp.add(toAdd);
                    }
                }
            }
        }
        return tmp;
    }

    public ArrayList<Carpetas> getCarpetasSds(ArrayList<String> tmp) {

        ArrayList<Carpetas> carpetas = new ArrayList<>();

        for(String sd:tmp) {
            File file = new File(sd);
            Carpetas extSD = new Carpetas();
            extSD.setIcono(R.drawable.hdd_usb_256);
            extSD.setPath(sd);
            extSD.setNombre(file.getName());
            carpetas.add(extSD);
        }

        return carpetas; // Regresamos el Array de almacenamientos
    }
}
