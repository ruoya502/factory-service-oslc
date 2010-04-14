
package org.qualipso.factory.service.oslc;

import java.io.File;

/**
 * 
 *  
 * @author Huihui Yang
 * 
 */
public class Separator  {

    // The file separator used on this platform
    public static String fileSeparator;
    
   /**
    * Define the separator
    */
    public Separator() {
    	
        /* Fix file separator for regexps in Windows */
        if ("\\".equals(File.separator)) 
            Separator.fileSeparator = "\\\\";
        else
        	Separator.fileSeparator = File.separator;
    }
}
