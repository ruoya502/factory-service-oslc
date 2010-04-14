package org.qualipso.factory.service.oslc;

import java.util.regex.Pattern;
import org.tmatesoft.svn.core.SVNException;
import checker.gui.svn.AuthenticationProvider;
import checker.repository.SVNRepositoryPackage;

public class DownLoader {
    
    String localPath;
    
    public DownLoader(String localPath) {
        this.localPath = localPath;
    }
       
    /**
     * check svn repository files to local path
     * @param svnPath
     * @param localPath
     * @param userName
     * @param password
     */
    public String CheckSVNToLocal(String svnPath,String userName,String password) throws OSLCServiceException{
        

            //  Open the SVN repository
            SVNRepositoryPackage svnRepositoryPackage = new SVNRepositoryPackage();
            
            try {
                svnRepositoryPackage.openRepository(svnPath, userName, password, null,new AuthenticationProvider(userName, password));
           
            } catch (Exception e) {  
                throw(new OSLCServiceException("Can not open the SVN Repository!",e));
            }
            
            //Get the packageName being checked
            String packageName = GetPackageName(svnPath);
            
            //Check out from remote repository
            try {
                svnRepositoryPackage.checkoutFromRepository(packageName, svnPath);       
            } catch (SVNException e) {
                throw(new OSLCServiceException("Fail to Check Out the Files in SVN Repository!",e));
            }    
           return packageName;
    }

    private String GetPackageName(String svnPath) {
        Pattern p = Pattern.compile("/");
        String[] items = p.split(svnPath);
        String folderName = items[items.length - 1];
        String packageName = this.getLocalPath() + folderName;
        return packageName;
    }
    
    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
