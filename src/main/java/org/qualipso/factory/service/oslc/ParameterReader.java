package org.qualipso.factory.service.oslc;

/**
 * 
 * @author Huihui Yang
 *
 */
public class ParameterReader {
    
    String localPath = "";
    String licenseFilePath = "";
    
    public ParameterReader(){
    }   
    
    /**
     * 
     * @return localPath
     */
    public  String getLocalPath(){
        //System.out.println(System.getProperty("%JBOSS_HOME"));
        String   envPath   =   System.getProperty("user.dir");  
        String localPath = envPath + "/../tmp/";
        
        return localPath;
    }
    
    /**
     * 
     * @return licenseFilePath
     */
    public  String getLicenseFilePath(){
        
        String   envPath   =   System.getProperty("user.dir");  
       // System.out.println(envPath);   
        licenseFilePath = envPath + "/../tmp/.OSLClicenses";
        return licenseFilePath;  
    }
    
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public void setLicenseFilePath(String licenseFilePath) {
        this.licenseFilePath = licenseFilePath;
    }
}