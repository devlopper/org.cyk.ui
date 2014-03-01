package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.IView;

public interface IForm/*<DTO>*/ extends IView {
	
	Collection<IFormField> getFields();
	
	Collection<IFormCommand> getCommands();
	
	//DTO getDto();
	
	//void build(Class<?>...groups);

}
