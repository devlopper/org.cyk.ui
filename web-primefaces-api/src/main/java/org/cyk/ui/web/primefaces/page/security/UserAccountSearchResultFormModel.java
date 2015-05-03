package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;

@Getter @Setter
public class UserAccountSearchResultFormModel extends AbstractFormModel<UserAccount> implements Serializable {

	private static final long serialVersionUID = -392868128587378419L;

	@Input private String username,roles,status,creationDate;
	
	@Override
	public void read() {
		super.read();
		username = identifiable.getCredentials().getUsername();
		creationDate = UIManager.getInstance().getTimeBusiness().formatDateTime(identifiable.getCreationDate());
		Set<String> set = new LinkedHashSet<>();
		for(Role role : identifiable.getRoles())
			set.add(role.getName());
		roles = StringUtils.join(set,",");
		status = identifiable.getStatus();
	}
	
}
