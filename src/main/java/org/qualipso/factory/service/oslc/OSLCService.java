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

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.qualipso.factory.FactoryNamingConvention;
import org.qualipso.factory.FactoryService;



/**
 * Provides a time service for the factory.
 * 
 * @author <a href="mailto:christophe.bouthier@loria.fr">Christophe Bouthier</a>
 * @date 27 July 2009
 */
@Remote
@WebService(name = OSLCService.SERVICE_NAME, targetNamespace = FactoryNamingConvention.SERVICE_NAMESPACE + OSLCService.SERVICE_NAME)

@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface OSLCService extends FactoryService {

	/**
	 * Return the current time.
	 * 
	 * @return the current time, in a String
	 * @throws ClockServiceException
	 */

    public static final String SERVICE_NAME = "OSLCService";
    public static final String[] RESOURCE_TYPE_LIST = new String[0];
    public static final String PROFILES_PATH = "/profiles";
    public static final String UNAUTHENTIFIED_IDENTIFIER = "guest";
    public static final String SUPERUSER_IDENTIFIER = "root";
	@WebMethod
    @WebResult(name = "checkResult")
    public Results getLicensesResult(String svnPath,String userName,String password) throws OSLCServiceException;
	

}
