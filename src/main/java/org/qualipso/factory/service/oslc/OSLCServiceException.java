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
 */
package org.qualipso.factory.service.oslc;

import javax.xml.ws.WebFault;

import org.qualipso.factory.FactoryException;


/**
 * Exception for the Clock service.
 * 
 * @author <a href="yhh.ruoya@gmail.com">Huihui Yang</a>
 * @date 17 March 2010
 */
@WebFault
@SuppressWarnings("serial")
public class OSLCServiceException extends FactoryException {

	/**
	 * Constructor
	 * 
	 * @param message		human-readable explanation of the exception
	 * @param rootCause		primary exception that caused this exception (real cause of the problem)
	 */
	public OSLCServiceException(String message, Exception rootCause) {
		super(message, rootCause);
	}

	/**
	 * Constructor
	 *
	 * @param rootCause		primary exception that caused this exception (real cause of the problem)
	 */
	public OSLCServiceException(Exception rootCause) {
		super(rootCause);
	}

	/**
	 * Constructor
	 *
	 * @param message		human-readable explanation of the exception
	 */
	public OSLCServiceException(String message) {
		super(message);
	}
	
}
