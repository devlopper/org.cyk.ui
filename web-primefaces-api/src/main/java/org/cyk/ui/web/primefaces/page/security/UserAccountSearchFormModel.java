package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class UserAccountSearchFormModel implements Serializable {

	private static final long serialVersionUID = -392868128587378419L;

	@Input @InputText
	private String username;
	
}
