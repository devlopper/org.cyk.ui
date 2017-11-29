package org.cyk.ui.web.api.resources.shiro;

import java.io.Serializable;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.cyk.system.root.business.api.datasource.DataSource;

public class Realm extends JdbcRealm implements Serializable {

	private static final long serialVersionUID = -659895508700873034L;

	public static DataSource DATA_SOURCE;
	
	public Realm() {
		
		authenticationQuery = DATA_SOURCE.getAuthenticationQuery();
		userRolesQuery = DATA_SOURCE.getUserRolesQuery();
		permissionsQuery = DATA_SOURCE.getPermissionsQuery();
		permissionsLookupEnabled=true;
		
		
	}
	
	/*@Override
	protected Set<String> getPermissions(Connection connection, String username, Collection<String> roles) throws SQLException {
		Set<String> set = new HashSet<>();
		for(Permission permission : RoleManager.getInstance().getPermissionBusiness().findByUsername(username))
			set.add(permission.getIdentifier().toString());
		return set;
	}*/
	
	
}
