package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.security.RoleIdentifiableGlobalIdentifier;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifierEditPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class RoleIdentifiableGlobalIdentifierEditPage extends AbstractJoinGlobalIdentifierEditPage<RoleIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends AbstractForm<RoleIdentifiableGlobalIdentifier> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		//@Input @InputChoice(load=true) @InputOneChoice @InputOneCombo @NotNull private File internalFile;
		
		
		
		public static final String FIELD_EXTERNAL_FILE = "externalFile";
		public static final String FIELD_INTERNAL_FILE = "internalFile";
		
	}
	
}
