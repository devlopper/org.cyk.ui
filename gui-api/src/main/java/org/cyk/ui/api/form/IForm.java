package org.cyk.ui.api.form;

import java.util.Collection;

import org.cyk.ui.api.IViewContent;

public interface IForm extends IViewContent {
	
	Collection<IFormField> getFields();
	
	Collection<IFormField> getCommands();

}
