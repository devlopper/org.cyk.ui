package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.userinterface.MenuItem;
import org.cyk.system.root.model.userinterface.MenuItemType;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.web.primefaces.page.geography.AbstractDataTreeNodeEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MenuItemEditPage extends AbstractDataTreeNodeEditPage<MenuItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;	
	
	@Getter @Setter 
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractDataTreeForm.FIELD_PARENT,type=MenuItem.class)
			,@FieldOverride(name=AbstractDataTreeForm.FIELD_TYPE,type=MenuItemType.class)
			})
	public static class Form extends AbstractDataTreeForm<MenuItem,MenuItemType> {
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private UniformResourceLocator uniformResourceLocator;
		
		public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
	
	}
	
}
