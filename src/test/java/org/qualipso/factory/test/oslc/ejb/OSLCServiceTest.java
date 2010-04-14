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
package org.qualipso.factory.test.oslc.ejb;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.qualipso.factory.service.oslc.OSLCService;
import org.qualipso.factory.service.oslc.OSLCServiceBean;
import org.qualipso.factory.service.oslc.OSLCServiceException;
import org.qualipso.factory.service.oslc.Results;

import com.bm.testsuite.BaseSessionBeanFixture;

/**
 * Direct unit tests for the OSLC service.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 29 July 2009
 *
 */
public class OSLCServiceTest extends BaseSessionBeanFixture<OSLCServiceBean> {
	
	private static Log logger = LogFactory.getLog(OSLCServiceTest.class);
	
	@SuppressWarnings("unchecked")
	private static final Class[] usedBeans = {};
	
	
	/**
	 * Constructor 
	 */
	public OSLCServiceTest() {
		super(OSLCServiceBean.class, usedBeans);
	}
	
	/**
	 * Test the OSLC service.
	 * We can't test directly by comparing to a hard-coded string, as the date is always changing,
	 * so we only test if the format is correct, by matching it to a given regex.
	 */
//	public void testOSLCService() {
//		logger.debug("Testing OSLCService");
//		
//		OSLCService service = getBeanToTest();
//		
//		String result;
//            try {
//                
////                String url = "https://forge.theresis.org/svn/qualipso/QualipsoGadgets/SVN/";//repository url
////                String username = "yang";//repository user name
////                String password = "huyang";//repository password
//                
//                String url = "http://o-engine.com/repos/qualipso/junit-log-extractor/test";      
//                String username = "lpn";//repository user name
//                String password = "lpn";//repository password
//                
//                result = service.getLicensesResult(url,username,password);
//                logger.info("result xml is as following: " + result); 
//                assertTrue(result.startsWith("<?xml"));
//            } catch (OSLCServiceException e) {
//                e.printStackTrace();
//            }
//	}
	
	public void testOSLCService(){
	    logger.debug("Testing OSLCService");   
        OSLCService service = getBeanToTest();
        
	    String svnPath = "http://o-engine.com/repos/qualipso/junit-log-extractor/test";
	    String userName = "lpn";
	    String pwd = "lpn";  
//        String svnPath = "https://forge.theresis.org/svn/qualipso/QualipsoGadgets/SVN/";//repository url
//        String userName = "yang";//repository user name
//        String pwd = "huyang";//repository password
	    float m = 1;  
	    try {     
	        logger.info("==================uncertain Licensed Files===================");
            Results checkResult = service.getLicensesResult(svnPath, userName, pwd); 
            String allFilesArray = checkResult.getUncertainLicensesFiles();        
	       // String path = new ParameterReader().getLicenseFilePath();
              logger.info(allFilesArray);

//     
//            logger.info("==================conflicting Licensed Files===================");
//            String[] conflitingFilesArray = service.getConflictingFilesArray();
//            for(int i = 0;i < conflitingFilesArray.length;i ++){
//                logger.info(conflitingFilesArray[i]);
//            }
//            
//            logger.info("==================uncertain Files======matches is less than " + m + "=============");
//            String[] uncertainFilesArray = service.getuncertainLicesesArray(m);
//            for(int i = 0;i < uncertainFilesArray.length;i ++){
//                logger.info(uncertainFilesArray[i]);
//            }
//            
//            logger.info("==================missing Licenses===================");
//            String[] missingFilesArray = service.getMissingLicesesArray();
//            for(int i = 0;i < missingFilesArray.length;i ++){
//                logger.info(missingFilesArray[i]);
//            }
        } catch (OSLCServiceException e) {
            e.printStackTrace();
        }
	    
	}

}
