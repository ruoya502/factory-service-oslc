package org.qualipso.factory.service.oslc;

import java.util.ArrayList;
import java.util.Iterator;

import checker.FileID;
import checker.LicenseChecker;
import checker.Pair;
import checker.Reference;
import checker.gui.filter.Criteria;
import checker.gui.filter.LicenseCriterion;
import checker.gui.filter.MatchCriterion;
import checker.gui.tree.FileLicense;
import checker.gui.tree.FileReference;
import checker.gui.tree.FileSource;
import checker.gui.tree.FileUnknown;
import checker.license.License;

/**
 * 
 * @author Huihui Yang
 *Analyzer will analyse the LicenseChecker to store the numbers of conflicting
 *or files in object Results.
 */
public class Analyzer {
    private LicenseChecker lc;

    public Analyzer(LicenseChecker lc){
        this.lc = lc;
    }
    
    /**
     * 
     * @return checkResult  it including all the license information in source code
     */
    public Results getCheckResultValues() {
        Results checkResult = new Results();
        int srcCount = lc.getSourceFiles().size();
		int licCount = lc.getLicenseFiles().size();
		int unkCount = lc.getUnknownFiles().size();
		int disLicCount = lc.getLicenseCounts().size();
		int confRefCount = lc.getNumReferenceConflicts(); 
		int confGblCount = lc.getGlobalLicenseConflicts().size() / 2;

		int allCount = srcCount + licCount + unkCount;
		
        checkResult.setSrcCount(srcCount);
        checkResult.setLicenseFileCount(licCount);
        //checkResult.setUnLicensedFileCount(unkCount);
        checkResult.setDisLicensesCount(disLicCount);
        checkResult.setConfRefCount(confRefCount); 
        checkResult.setConfGblCount(confGblCount);
        checkResult.setAllCount(allCount);
        checkResult.setCopyRightHolders((lc.getCopyrightHolders().size()));
        checkResult.setCopyRightedFiles(lc.getCopyrightFiles().size());
        
       
        checkResult.setMissingLicenseFiles(getMissingLicesesArray());  
       // checkResult.setAllFilesArray(getAllFilesArray());
        checkResult.setConflictingFiles(getConflictingFilesArray());
        //checkResult.setLicensedFiles(getLicensedFilesArray(""));
        checkResult.setUncertainLicensesFiles(getuncertainLicesesArray(1));
            
       
        return checkResult;
    }
    
    /**
     * 
     * @return all Files in source code in String
     */
    public String getAllFilesArray()  {
        
        ArrayList<FileID> filesID = lc.getFiles();
        String allFilesArray = "";
        
        for(int i = 0;i < filesID.size();i ++){
            allFilesArray += filesID.get(i).path + "/" + filesID.get(i).name + ",";
        }
        if(allFilesArray.length()!=0)
            allFilesArray = allFilesArray.substring(0,allFilesArray.length()-1);
        return allFilesArray;
    }
   
    /**
     * unKnownFiles and LicensFiles don't have conflicting License.So, dealing with the source License is OK.
     */
    public String getConflictingFilesArray()  {
        ArrayList<FileID> backupFiles = new ArrayList<FileID>();
        ArrayList<FileSource> sources = getFileSources(lc);
        int i = 0;
        for(FileSource file: sources){
            if(file.hasConflict()){
                backupFiles.add(i, file.getFileID());
                i ++;
            }
                
        }
        String conflictingFiles = "";
        for(int j = 0; j < backupFiles.size();j ++){
            conflictingFiles += backupFiles.get(j).path + "/" + backupFiles.get(j).name + ",";
        }
        if(conflictingFiles.length() != 0)
           conflictingFiles = conflictingFiles.substring(0,conflictingFiles.length()-1);
       return conflictingFiles;
    }

    /**
     * 
     * @param id
     * @return the licensed Files with a criteria, the String id in null in common.
     */
    public String getLicensedFilesArray(String id) {
       
        License license = new License();
        Criteria criteria = Criteria.ALL;
        
        Iterator<License> iter = lc.getLicenses().iterator();     
        while (iter.hasNext()) {
            license = iter.next();
          if(license.getId().equals(id)){
              break;
          }
          license = null;
        }
        
        if(license != null){      
             LicenseCriterion licenseCriterion = new LicenseCriterion(license);
             criteria = new Criteria(licenseCriterion);
        }
       
        ArrayList<FileID> backupFiles = new ArrayList<FileID>();
        ArrayList<FileSource> sources = getFileSources(lc);
        ArrayList<FileLicense> licenses = getFileLicense(lc);
        int i = 0;
        for(FileSource file: sources){
            if (criteria != Criteria.ALL) {
                if (file.hasLicense() && file.meetsCriteria(criteria)) {
                    backupFiles.add(i, file.getFileID());
                    i++;
                }
            } else {
                if (file.hasLicense()) {
                    backupFiles.add(i, file.getFileID());
                    i++;
                }
            }   
        }
        for(FileLicense file: licenses){
            if (criteria != Criteria.ALL) {
                if (file.hasLicense() && file.meetsCriteria(criteria)) {
                    backupFiles.add(i, file.getFileID());
                    i++;
                }
            } else {
                if (file.hasLicense()) {
                    backupFiles.add(i, file.getFileID());
                    i++;
                }
            } 
        }
        String hasLicenseFiles = "";
        for(int j = 0; j < backupFiles.size();j ++){
            hasLicenseFiles += backupFiles.get(j).path + "/" + backupFiles.get(j).name + ",";
        }
        if(hasLicenseFiles.length() != 0)
            hasLicenseFiles = hasLicenseFiles.substring(0,hasLicenseFiles.length()-1);
        return hasLicenseFiles;
    }
    
    /**
     * 
     * @param m
     * @return the uncertain Licensed files in String, with criteria m, the float m is 1 in common.
     */
    public String getuncertainLicesesArray(float m)  {
        
        MatchCriterion matchCriterion = new MatchCriterion(m);
        Criteria criteria = new Criteria(matchCriterion);
        
        ArrayList<FileID> backupFiles = new ArrayList<FileID>();
        ArrayList<FileSource> sources = getFileSources(lc);
        ArrayList<FileLicense> licenses = getFileLicense(lc);
        int i = 0;
        for(FileSource file: sources){
            if (criteria != Criteria.ALL) {
              if (file.hasLicense() && file.meetsCriteria(criteria)) {
                  backupFiles.add(i, file.getFileID());
                  i++;
              }
          } else {
              if (file.hasLicense()) {
                  backupFiles.add(i, file.getFileID());
                  i++;
              }
          } 
            
        }
        for(FileLicense file: licenses){
            if (criteria != Criteria.ALL) {
              if (file.hasLicense() && file.meetsCriteria(criteria)) {
                  backupFiles.add(i, file.getFileID());
                  i++;
              }
          } else {
              if (file.hasLicense()) {
                  backupFiles.add(i, file.getFileID());
                  i++;
              }
          } 
        }
        String hasUncertainLicenses = "";
        for(int j = 0; j < backupFiles.size();j ++){
            hasUncertainLicenses += backupFiles.get(j).path + "/" + backupFiles.get(j).name + ",";
        }
        if(hasUncertainLicenses.length() != 0)
            hasUncertainLicenses = hasUncertainLicenses.substring(0,hasUncertainLicenses.length()-1);
        return hasUncertainLicenses;
    }

   /**
    * 
    * @return the missing licenses files in String
    */
    public String getMissingLicesesArray()  {
        ArrayList<FileID> backupFiles = new ArrayList<FileID>();
        ArrayList<FileSource> sources = getFileSources(lc);
        ArrayList<FileUnknown> unknowns = getFileUnKnowns(lc);
        ArrayList<FileLicense> licenses = getFileLicense(lc);
        int i = 0;
        for(FileSource file: sources){
            if(file.missLicense()){
                backupFiles.add(i, file.getFileID());
                i ++;
            }
               
        }
        for(FileUnknown file: unknowns){
            if(file.missLicense()){
                backupFiles.add(i, file.getFileID());
                i ++;
            }
        }
        for(FileLicense file: licenses){
            if(file.missLicense()){
                backupFiles.add(i, file.getFileID());
                i ++;
            }
        }
        String missingLicenseFiles = "";
        for(int j = 0; j < backupFiles.size();j ++){
            missingLicenseFiles += backupFiles.get(j).path + "/" + backupFiles.get(j).name + ",";
        }
        if(missingLicenseFiles.length() != 0)
            missingLicenseFiles = missingLicenseFiles.substring(0,missingLicenseFiles.length()-1);
        return missingLicenseFiles;
    }
    
    /**
     * 
     * @param lc
     * @return the FilesSource in ArrayList
     * After process the source code, the result will be stored in LicenseChecker,
     * Also, in LicenseChecker, which include ArrayList<FileID> sourceFiles.By read 
     * the sourceFiles and add the information to ArrayList<FileSource> sources.
     */
    public ArrayList<FileSource> getFileSources(LicenseChecker lc){
        ArrayList<FileID> sourceFiles = lc.getSourceFiles();
        ArrayList<FileSource> sources = new ArrayList<FileSource>();
        int i = 0;
        for (FileID file : sourceFiles) {
                sources.add(i, new FileSource(file, lc.getLicenseMatches(file)));
                /* Add references and associate conflicts with them */
                ArrayList<Reference> refs = lc.getReferences(file);
                if (refs != null)
                    for (Reference ref : refs) {
                        ArrayList<Pair<License, License>> conflicts = lc.getLicenseConflicts(ref);
                        sources.get(i).addReference(new FileReference(ref, conflicts));
                    }
                /* Add a possible self-reference ie. file conflicts with itself. */
                ArrayList<Pair<License, License>> selfconflicts = lc.getInternalLicenseConflicts(file);
                if (selfconflicts != null) {  
                    Reference self = new Reference(file, file);
                    self.referenceType = Reference.ReferenceType.IMPORT;
                   // self.declaration = loc.lc("(self)");
                    sources.get(i).addReference(new FileReference(self, selfconflicts)); 
                }
                i ++;
            }
        return sources;
    }
    
    /**
     * 
     * @param lc
     * @return unKnowns
     *  By read the arrayList<FileUnknown> in LicenseChecker, add information to
     *  ArrayList<FileID> .
     */
    public ArrayList<FileUnknown> getFileUnKnowns(LicenseChecker lc){
        ArrayList<FileUnknown> unKnowns = new  ArrayList<FileUnknown>();
        ArrayList<FileID> unKnownFiles = lc.getUnknownFiles();
        int i = 0;
        for (FileID file : unKnownFiles){
            unKnowns.add(i, new FileUnknown(file));
            i ++;
        }
        return unKnowns;
    }
    
    /**
     * 
     * @param lc
     * @return licenseFiles
     * By read the arrayList<FileLicense> in LicenseChecker, add information to
     *  ArrayList<FileID> .
     */
    public ArrayList<FileLicense> getFileLicense(LicenseChecker lc){
        ArrayList<FileLicense> licenses = new  ArrayList<FileLicense>();
        ArrayList<FileID> licenseFiles = lc.getLicenseFiles();
        int i = 0;
        for (FileID file : licenseFiles){
            licenses.add(i, new FileLicense(file, lc.getLicenseMatches(file)));
            i ++;
        }
        return licenses;
    }
    
    
    public LicenseChecker getLc() {
        return lc;
    }

    public void setLc(LicenseChecker lc) {
        this.lc = lc;
    }
    
}
