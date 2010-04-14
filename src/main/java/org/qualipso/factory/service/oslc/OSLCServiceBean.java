/*
 * Qualipso Funky Factory
 * Copyright (C) 2006-2010 INRIA
 * http://www.inria.fr - molli@loria.fr
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation. See the GNU
 * Lesser General Public License in LGPL.txt for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 * Initial authors :
 *
 * Jérôme Blanchard / INRIA
 * Christophe Bouthier / INRIA
 * Pascal Molli / Nancy Université
 * Gérald Oster / Nancy Université
 */
package org.qualipso.factory.service.oslc;

import java.io.File;


import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ws.annotation.EndpointConfig;
import org.jboss.wsf.spi.annotation.WebContext;
import org.qualipso.factory.FactoryException;
import org.qualipso.factory.FactoryNamingConvention;
import org.qualipso.factory.FactoryResource;
import org.qualipso.factory.core.CoreServiceException;
import checker.LicenseChecker;
import checker.Log;
import checker.LogEntry;


/**
 * Implementation of the ClockService. Provides a time service for the factory.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 27 July 2009 
 * 
 * mappedName define the service jndi name, later client will use this name to lookup
 */
@Stateless(name = OSLCService.SERVICE_NAME, mappedName = FactoryNamingConvention.SERVICE_PREFIX+OSLCService.SERVICE_NAME)
@WebService(endpointInterface = "org.qualipso.factory.service.oslc.OSLCService", targetNamespace = FactoryNamingConvention.SERVICE_NAMESPACE + OSLCService.SERVICE_NAME, serviceName = OSLCService.SERVICE_NAME)
@WebContext(contextRoot = FactoryNamingConvention.WEB_SERVICE_ROOT_MODULE_CONTEXT + "-" + OSLCService.SERVICE_NAME, urlPattern = FactoryNamingConvention.WEB_SERVICE_URL_PATTERN_PREFIX + OSLCService.SERVICE_NAME)
@SOAPBinding(style = Style.RPC)
@SecurityDomain(value = "JBossWSDigest")
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
public class OSLCServiceBean implements OSLCService {

	/**
	 * Return all ressources types supported by this service.
	 * Here, return an empty array, no ressources types are supported.
	 * 
	 * @return	empty array
	 * @see org.qualipso.factory.FactoryService#getResourceTypeList()
	 */
    
	public String[] getResourceTypeList() {
		return OSLCService.RESOURCE_TYPE_LIST;
	}

	/**
	 * Return the name of the service.
	 * 
	 * @return    service name
	 * @see org.qualipso.factory.FactoryService#getServiceName()
	 */
	public String getServiceName() {
		return OSLCService.SERVICE_NAME;
	}

    /**
     * Returns an exception as this service doesn't manage resources.
     * 
     * @see org.qualipso.factory.FactoryService#findResource(java.lang.String)
     */
    public FactoryResource findResource(String arg0) throws FactoryException {
        throw new CoreServiceException("No Resource are managed by OSLC Service");
    }

    public Results getLicensesResult(String svnPath,String userName,String password) throws OSLCServiceException{
        
        Results checkResult = new Results();
        DownLoader checkToLocal = new DownLoader(new ParameterReader().getLocalPath());
        String localFileName = checkToLocal.CheckSVNToLocal(svnPath, userName, password);   
        LicenseChecker lc = localFilesAnalysis(localFileName);
        
        Analyzer analyseResults = new Analyzer(lc);
        checkResult = analyseResults.getCheckResultValues();
        
        //delete the downloaded files
        if(!DeleteFilesUtil.delete(localFileName)){
            Log.log(LogEntry.VERBOSE, "Failed to delete " + localFileName);   
        }
        return checkResult;
    }
    
    /**
     * 
     * @param localFileName
     * @return LicenseChecker
     * @throws OSLCServiceException
     * 
     */
    public static LicenseChecker localFilesAnalysis(String localFileName) throws OSLCServiceException{
        LicenseChecker lc = new LicenseChecker();
        
        Log.log(LogEntry.VERBOSE, "Loading file " + localFileName);               
        File infile = new File(localFileName);
        if(!infile.exists()) {
            Log.log(LogEntry.VERBOSE,"Failed to load file \"" + localFileName + "\", file not found");
             System.exit(1);
         }
         try {
                lc.openPackage(infile);
         } catch (Exception e) {
             Log.log(LogEntry.VERBOSE,"Failed to load file \"" + localFileName + "\"");
             System.exit(1);
         }
         LicenseChecker.doExcludePaths(lc);
         
         // Deal with the package,get xml result
        Log.log(LogEntry.DEBUG, "Processing...");
        lc.processPackage(new ParameterReader().getLicenseFilePath());
        
        return lc;
    }
}
