package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractItemCollectionItem extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 8464025197835827526L;

	protected FormOneData<? extends AbstractItemCollectionItem,?,?,?,?,?> form;
	
}
