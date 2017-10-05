package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class WebsiteEditPage extends AbstractContactEditPage<Website> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static class Form extends AbstractForm<Website> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private UniformResourceLocator uniformResourceLocator;
		
		public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
		
	}
	
}
