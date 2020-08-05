package com.ht.commons.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class UploadImageUtil {


    public String getUploadFileName() {
        SimpleDateFormat sf = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
        Random random = new Random();
        int ran = random.nextInt( 999999 );
        String time = sf.format( new Date() ) + ran;
        return time;
    }

    public String getExtension(File f) {
        return (f != null) ? getExtension( f.getName() ) : "";
    }

    public String getExtension(String filename) {
        return getExtension( filename, "" );
    }

    public String getExtension(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf( '.' );

            if ((i > -1) && (i < (filename.length() - 1))) {
                return filename.substring( i + 1 );
            }
        }
        return defExt;
    }

    public String trimExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf( '.' );
            if ((i > -1) && (i < (filename.length()))) {
                return filename.substring( 0, i );
            }
        }
        return filename;
    }
}
