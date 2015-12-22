package org.cyk.ui.api.model.security;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class UserAccountDetails extends AbstractOutputDetails<UserAccount> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String name;
	
	public UserAccountDetails(UserAccount classroomSession) {
		super(classroomSession);
		
	}
}