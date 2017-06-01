package com.practica.jmm.galleryf2f;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by sath on 30/05/17.
 */

public class FiltroArch implements FilenameFilter {
    @Override
    public boolean accept(File file, String s) {

        String namedir = file.getAbsolutePath();
        File arch = new File(namedir + File.separator + s);

        boolean fname = true;
        fname = arch.isFile() &&
                (s.endsWith(".JPG") ||
                s.endsWith(".jpg") ||
                s.endsWith(".jpeg") ||
                s.endsWith(".JPEG") ||
                s.endsWith(".PNG") ||
                s.endsWith(".png") ||
                s.endsWith(".GIF") ||
                s.endsWith(".gif") ||
                s.endsWith(".TIF") ||
                s.endsWith(".tif") ||
                s.endsWith(".TIFF") ||
                s.endsWith(".tiff"));

        boolean hiden = arch.isDirectory() && !s.startsWith(".",0);
        boolean result = false;

        if (hiden || fname){
            result = true;
        }

        return result;
    }
}
