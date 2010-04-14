package org.qualipso.factory.service.oslc;

import java.io.File;

public class DeleteFilesUtil {
      /**   
          * Delete Files, including file or directory  
          * @param   fileName     
          * @return if delete successfully to return true, else return false  
          */    
         public static boolean delete(String fileName){     
             File file = new File(fileName);     
             if(!file.exists()){     
                 System.out.println("Fail to delete files："+fileName+"doesn't exist");     
                 return false;     
             }else{     
                if(file.isFile()){           
                     return deleteFile(fileName);     
                 }else{     
                     return deleteDirectory(fileName);     
                 }     
             }     
         }     
              
         /**   
          * delete a file  
          * @param   fileName    the file name to be deleted  
          * @return   if delete successfully to return true, else return false  
          */    
         public static boolean deleteFile(String fileName){     
             File file = new File(fileName);     
             if(file.isFile() && file.exists()){     
                 file.delete();               
                 return true;     
             }else{      
                 return false;     
             }     
         }     
              
         /**   
          * delete directory and the files in this directory 
          * @param   dir   the directory to be deleted  
          * @return  if delete successfully to return true, else return false    
          */    
         public static boolean deleteDirectory(String dir){     
             //add the separator in the end  
             if(!dir.endsWith(File.separator)){     
                 dir = dir+File.separator;     
             }     
             File dirFile = new File(dir);     
             //exit if the directory doesn't exist or not be a directory
             if(!dirFile.exists() || !dirFile.isDirectory()){        
                 return false;     
             }     
             boolean flag = true;     
            //delete all the files in the directory including subdirecotry     
             File[] files = dirFile.listFiles();     
            for(int i=0;i<files.length;i++){     
                 //delete files     
                 if(files[i].isFile()){     
                     flag = deleteFile(files[i].getAbsolutePath());     
                     if(!flag){     
                         break;     
                     }     
                 }     
                 //delete subdirectory    
                 else{     
                     flag = deleteDirectory(files[i].getAbsolutePath());     
                     if(!flag){     
                         break;     
                     }     
                 }     
             }     
                  
             if(!flag){           
                 return false;     
             }     
                  
             //delete current directory     
             if(dirFile.delete()){      
                 return true;     
             }else{     
                 return false;     
            }     
         }     
}