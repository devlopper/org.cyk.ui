package org.cyk.ui.api.model;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QueryFormModel<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractQueryOneFormModel<IDENTIFIABLE,String> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
}