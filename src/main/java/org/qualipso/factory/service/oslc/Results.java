package org.qualipso.factory.service.oslc;

import java.io.Serializable;

/**
 * 
 * @author Huihui Yang
 * 
 *
 */
public class Results implements Serializable{

    private static final long serialVersionUID = 1L;
    private int srcCount;
    private int licenseFileCount;
   // private int uncertainLicensesFileCount;
   // private int unLicensedFileCount;
    private int distinctLicensesCount;
    private int confRefCount; 
    private int confGblCount;
    private int allCount;
    private int copyRightHolders;
    private int copyRightedFiles;
    
   
//    public int getUncertainLicensesFileCount() {
//		return uncertainLicensesFileCount;
//	}
//	public void setUncertainLicensesFileCount(int uncertainLicensesFileCount) {
//		this.uncertainLicensesFileCount = uncertainLicensesFileCount;
//	}
//	public int getUnLicensedFileCount() {
//		return unLicensedFileCount;
//	}
//	public void setUnLicensedFileCount(int unLicensedFileCount) {
//		this.unLicensedFileCount = unLicensedFileCount;
//	}
	//private String allFilesArray;
    private String conflictingFiles;
   // private String LicensedFiles;
    private String UncertainLicensesFiles;
    private String MissingLicenseFiles;
	public int getSrcCount() {
		return srcCount;
	}
	public void setSrcCount(int srcCount) {
		this.srcCount = srcCount;
	}
	public int getLicenseFileCount() {
		return licenseFileCount;
	}
	public void setLicenseFileCount(int licensesCount) {
		this.licenseFileCount = licensesCount;
	}
//	public int getUnknownCount() {
//		return unknownCount;
//	}
//	public void setUnknownCount(int unknownCount) {
//		this.unknownCount = unknownCount;
//	}
	public int getDisLicensesCount() {
		return distinctLicensesCount;
	}
	public void setDisLicensesCount(int disLicensesCount) {
		this.distinctLicensesCount = disLicensesCount;
	}
	public int getConfRefCount() {
		return confRefCount;
	}
	public void setConfRefCount(int confRefCount) {
		this.confRefCount = confRefCount;
	}
	public int getConfGblCount() {
		return confGblCount;
	}
	public void setConfGblCount(int confGblCount) {
		this.confGblCount = confGblCount;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getCopyRightHolders() {
		return copyRightHolders;
	}
	public void setCopyRightHolders(int copyRightHolders) {
		this.copyRightHolders = copyRightHolders;
	}
	public int getCopyRightedFiles() {
		return copyRightedFiles;
	}
	public void setCopyRightedFiles(int copyRightedFiles) {
		this.copyRightedFiles = copyRightedFiles;
	}
	public String getConflictingFiles() {
		return conflictingFiles;
	}
	public void setConflictingFiles(String conflictingFiles) {
		this.conflictingFiles = conflictingFiles;
	}
	public String getUncertainLicensesFiles() {
		return UncertainLicensesFiles;
	}
	public void setUncertainLicensesFiles(String uncertainLicensesFiles) {
		UncertainLicensesFiles = uncertainLicensesFiles;
	}
	public String getMissingLicenseFiles() {
		return MissingLicenseFiles;
	}
	public void setMissingLicenseFiles(String missingLicenseFiles) {
		MissingLicenseFiles = missingLicenseFiles;
	}
    

    
}
