package org.cyk.ui.web.primefaces.globalidentification;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;

@Getter @Setter @Named @ViewScoped
public class GlobalIdentifierEditPage extends AbstractCrudOnePage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
	}
	
	@Override
	protected void update() {
		RootBusinessLayer.getInstance().getGlobalIdentifierBusiness().update(identifiable.getGlobalIdentifier());
	}
	
	@Override
	protected Object data(Class<?> aClass) {
		GlobalIdentifierForm globalIdentifierForm = new GlobalIdentifierForm();
		globalIdentifierForm.setIdentifiable(identifiable);
		globalIdentifierForm.read();
		return globalIdentifierForm;
	}
	
	@Override
	protected Class<?> __formModelClass__() {
		return GlobalIdentifierForm.class;
	}
	
	@Getter @Setter
	protected static class GlobalIdentifierForm extends AbstractFormModel<AbstractIdentifiable> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputBooleanButton @NotNull private Boolean readable;
		@Input @InputBooleanButton @NotNull private Boolean updatable;
		@Input @InputBooleanButton @NotNull private Boolean deletable;
		
		@Override
		public void read() {
			super.read();
			readable = identifiable.getGlobalIdentifier().getRud().getReadable();
			if(readable==null)
				readable = Boolean.TRUE;
			updatable = identifiable.getGlobalIdentifier().getRud().getUpdatable();
			if(updatable==null)
				updatable = Boolean.TRUE;
			deletable = identifiable.getGlobalIdentifier().getRud().getDeletable();
			if(deletable==null)
				deletable = Boolean.TRUE;
		}
		
		@Override
		public void write() {
			super.write();
			if(identifiable.getGlobalIdentifier().getRud().getReadable()!=null || !readable)
				identifiable.getGlobalIdentifier().getRud().setReadable(readable);
			if(identifiable.getGlobalIdentifier().getRud().getUpdatable()!=null || !updatable)
				identifiable.getGlobalIdentifier().getRud().setUpdatable(updatable);
			if(identifiable.getGlobalIdentifier().getRud().getDeletable()!=null || !deletable)
				identifiable.getGlobalIdentifier().getRud().setDeletable(deletable);
		}
		
		/**/
		
		
	}

}
