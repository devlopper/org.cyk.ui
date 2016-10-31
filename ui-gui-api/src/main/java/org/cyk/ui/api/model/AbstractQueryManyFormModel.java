package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyPickList;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractQueryManyFormModel<IDENTIFIABLE extends AbstractIdentifiable,IDENTIFIER> extends AbstractBean implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputChoice(load=false) @InputManyChoice @InputManyPickList @NotNull protected List<IDENTIFIABLE> identifiables;
	
	@IncludeInputs(layout=Layout.VERTICAL) protected CodesFormModel codes = new CodesFormModel();
	
	protected Boolean showIdentifiables = Boolean.TRUE,showCodes = Boolean.FALSE;
	
	public static final String FIELD_IDENTIFIABLES = "identifiables";
	
	/**/
	
	@Getter @Setter
	public static class Default<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractQueryManyFormModel<IDENTIFIABLE,String> implements Serializable {
		private static final long serialVersionUID = -3756660150800681378L;
		
	}
	
}