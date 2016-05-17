package org.cyk.ui.api.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Clazz;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormMap;
import org.cyk.ui.api.model.AbstractItemCollectionItem;

@Getter @Setter @NoArgsConstructor 
public class IdentifiableConfiguration extends Clazz implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	private FormMap formMap;
	
	private Boolean oneEdit = Boolean.TRUE,globalFiltering=Boolean.TRUE,usableByChild=Boolean.TRUE;

	public IdentifiableConfiguration(Class<? extends AbstractIdentifiable> identifiableClass
			,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> editOneFormModelClass,Class<?> readOneFormModelClass,Class<?> queryOneFormModelClass
			,Class<? extends AbstractItemCollectionItem<? extends AbstractIdentifiable>> editManyFormModelClass,Class<?> queryManyFormModelClass) {
		super(identifiableClass);
		setForms(editOneFormModelClass, readOneFormModelClass,queryOneFormModelClass,editManyFormModelClass,queryManyFormModelClass);
	}
	
	public void setForms(Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> editOneFormModelClass,Class<?> readOneFormModelClass,Class<?> queryOneFormModelClass
			,Class<? extends AbstractItemCollectionItem<? extends AbstractIdentifiable>> editManyFormModelClass,Class<?> queryManyFormModelClass){
		formMap = new FormMap(getClazz(),editOneFormModelClass,readOneFormModelClass,queryOneFormModelClass,editManyFormModelClass,queryManyFormModelClass);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends AbstractIdentifiable> getClazz() {
		return (Class<? extends AbstractIdentifiable>) super.getClazz();
	}
	
}
