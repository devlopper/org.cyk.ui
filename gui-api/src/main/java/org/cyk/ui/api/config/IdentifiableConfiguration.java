package org.cyk.ui.api.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;

@Getter @Setter @NoArgsConstructor
public class IdentifiableConfiguration implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	private Class<? extends AbstractIdentifiable> identifiableClass;
	
	private Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> editOneFormModelClass;
	private Class<?> readOneFormModelClass;
	
	private Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> editManyFormModelClass;
	private Class<?> readManyFormModelClass;
	
	private Boolean oneEdit = Boolean.TRUE,globalFiltering=Boolean.TRUE;

	public IdentifiableConfiguration(
			Class<? extends AbstractIdentifiable> identifiableClass,
			Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> editOneFormModelClass,
			Class<?> readOneFormModelClass) {
		super();
		this.identifiableClass = identifiableClass;
		this.editOneFormModelClass = editOneFormModelClass;
		this.readOneFormModelClass = readOneFormModelClass;
		this.editManyFormModelClass = editOneFormModelClass;
		this.readManyFormModelClass = readOneFormModelClass;
		
		if(this.editOneFormModelClass!=null)
			UIManager.getInstance().businessEntityInfos(Person.class).setUiEditViewId(null);
		if(this.readOneFormModelClass!=null)
			UIManager.getInstance().businessEntityInfos(Person.class).setUiConsultViewId(null);
		if(this.readManyFormModelClass!=null)
			UIManager.getInstance().businessEntityInfos(Person.class).setUiListViewId(null);
	}
	
}
