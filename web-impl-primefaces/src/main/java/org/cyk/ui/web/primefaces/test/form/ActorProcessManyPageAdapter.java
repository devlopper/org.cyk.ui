package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.primefaces.page.AbstractProcessManyPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ActorProcessManyPageAdapter extends AbstractProcessManyPage.Listener.Adapter.Default<Actor,String> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;
	
	public ActorProcessManyPageAdapter() {
		super(Actor.class);
	}
	
	@Override
	public Class<?> getFormDataClass(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
		return Form.class;
	}
	
	public static class Form extends AbstractFormModel<Actor> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText private String myinput1;
		@Input @InputText private String myinput2;
		@Input @InputBooleanButton private Boolean mychoice1;
		@Input @InputBooleanCheck private Boolean mychoice2;
	}

}
