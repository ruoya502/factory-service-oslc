/**
 * 
 *   Copyright (C) 2008 Lasse Parikka
 *
 *   This program is free software; you can redistribute it and/or modify it under the terms of
 *   the GNU General Public License as published by the Free Software Foundation; either version 2
 *   of the License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 *   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *   See the GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License along with this program;
 *   if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, 
 *   MA 02111-1307 USA
 *
 *   Also add information on how to contact you by electronic and paper mail.
 *
 */
package checker.gui.svn;

import java.util.HashMap;
import java.util.Map;

import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationProvider;
import org.tmatesoft.svn.core.auth.SVNAuthentication;
import org.tmatesoft.svn.core.auth.SVNSSHAuthentication;



/**
 * Asks user to accept the digital certificate with ssl connections.
 * Provides credentials for SSH connections.
 * @author lparikka, the source code of this class is modified by Huihui Yang in March 2010.
 */
public class AuthenticationProvider implements ISVNAuthenticationProvider {
	
    private static final int MAX_PROMPT_COUNT = 3;
    private Map myRequestsCount = new HashMap();
    private String username;
    private String password;
    
    public AuthenticationProvider(String username, String password) {
        this.username = username;
        this.password = password;
        //parent = parentForCertificateWindow;
        
    }
    
    public int acceptServerAuthentication(SVNURL url, String realm, 
    		Object certificate, boolean resultMayBeStored) {        
    
            return ISVNAuthenticationProvider.ACCEPTED_TEMPORARY;

    }

    public SVNAuthentication requestClientAuthentication(String kind, 
    		SVNURL url, String realm, SVNErrorMessage errorMessage, 
    		SVNAuthentication previousAuth, boolean authMayBeStored) {
       
    	Integer requestsCount = (Integer) myRequestsCount.get(kind + "$" + url + "$" + realm);
        
    	if (requestsCount == null) {
            myRequestsCount.put(kind + "$" + url + "$" + realm, new Integer(1));
        } else if (requestsCount.intValue() == MAX_PROMPT_COUNT) {
            // no more than three requests per realm
            return null;
        } else {
            myRequestsCount.put(kind + "$" + url + "$" + realm, new Integer(requestsCount.intValue() + 1));
        }
        
        if (ISVNAuthenticationManager.SSH.equals(kind)) {
        	return new SVNSSHAuthentication(username, password, 22, authMayBeStored);
            
        }
        return null;
    }

}
//Reviewed by mkupsu 29.11.8
