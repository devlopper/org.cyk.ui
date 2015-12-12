package org.cyk.ui.api.model;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractActorQueryFormModel<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractQueryFormModel<IDENTIFIABLE,String> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	
	/**/
	
	@Getter @Setter
	public static class Default<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractActorQueryFormModel<IDENTIFIABLE> implements Serializable {
		private static final long serialVersionUID = -3756660150800681378L;
		
	}
}