package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormOneData;

@Getter @Setter
public abstract class AbstractItemCollectionItem<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractFormModel<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 8464025197835827526L;

	protected FormOneData<? extends AbstractItemCollectionItem<IDENTIFIABLE>,?,?,?,?,?> form;
	
	protected Boolean applicable=Boolean.TRUE;
	
	
}
