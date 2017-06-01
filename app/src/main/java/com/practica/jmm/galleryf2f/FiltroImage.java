package com.practica.jmm.galleryf2f;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by sath on 27/07/16.
 */
public class FiltroImage implements FilenameFilter {
//Clase que se encarga de filtrar los archivos
//para que solo se muestren lasimagenes soportadas por el terminal, las que tienen la extensión .jpg etc..
    public boolean accept(File dir, String name) {

        boolean fname = false;

        if (
                //dir.isDirectory() ||
                name.endsWith(".JPG") ||
                name.endsWith(".jpg") ||
                name.endsWith(".jpeg") ||
                name.endsWith(".JPEG") ||
                name.endsWith(".PNG") ||
                name.endsWith(".png") ||
                name.endsWith(".GIF") ||
                name.endsWith(".gif") ||
                name.endsWith(".TIF") ||
                name.endsWith(".tif") ||
                name.endsWith(".TIFF") ||
                name.endsWith(".tiff")) {

      //              if (name.indexOf(".") != 0) {
                        fname = true;
       //             }
        }
        return fname;
    }
}
