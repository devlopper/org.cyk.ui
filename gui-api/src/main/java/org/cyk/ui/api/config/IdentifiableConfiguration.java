package org.cyk.ui.api.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;

@Getter @Setter @NoArgsConstructor
public class IdentifiableConfiguration implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	private Class<? extends AbstractIdentifiable> identifiableClass;
	private Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> formModelClass;
	private Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> tableModelClass;
	private Boolean oneEdit = Boolean.TRUE,globalFiltering=Boolean.TRUE,fileSupport=Boolean.FALSE;
	
	public IdentifiableConfiguration(Class<? extends AbstractIdentifiable> identifiableClass, 
			Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> formModelClass
					/*,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> tableModelClass*/) {
		super();
		this.identifiableClass = identifiableClass;
		this.formModelClass = formModelClass;
		//this.tableModelClass = tableModelClass;
	}
	
	/**/
	
}
