package org.cyk.ui.api.model.party;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.AbstractQueryManyFormModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractActorQueryManyFormModel<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractQueryManyFormModel<IDENTIFIABLE,String> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	
	/**/
	
	@Getter @Setter
	public static class Default<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractActorQueryManyFormModel<IDENTIFIABLE> implements Serializable {
		private static final long serialVersionUID = -3756660150800681378L;
		
	}
}