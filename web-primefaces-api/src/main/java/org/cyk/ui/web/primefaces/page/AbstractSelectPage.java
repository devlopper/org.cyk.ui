package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractSelectPage<ENTITY extends AbstractIdentifiable,FORM> extends AbstractBusinessEntityFormOnePage<ENTITY> implements CommandListener,Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	public static enum Type{IDENTIFIER,IDENTIFIABLE}
	
	protected Type type = Type.IDENTIFIABLE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		form.setShowCommands(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.ok"));
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			@Override
			public Boolean build(Field field) {
				switch(type){
				case IDENTIFIER:
					return AbstractSelectForm.FIELD_IDENTIFIER.equals(field.getName());
				case IDENTIFIABLE:
					return AbstractSelectForm.FIELD_IDENTIFIABLE.equals(field.getName());
				}
				return super.build(field);
			}
		});
	}
		
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		addInputAdapter(AbstractSelectForm.FIELD_IDENTIFIER,new WebInput.WebInputAdapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {
				identifiable = find((String) value);
				if(identifiable==null)
					webManager.throwValidationExceptionUnknownValue(value);
				super.validate(facesContext, uiComponent, value);
			}
		});
		
		addInputAdapter(AbstractSelectForm.FIELD_IDENTIFIABLE,new WebInput.WebInputAdapter(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {
				identifiable = (ENTITY) value;
				if(identifiable==null)
					webManager.throwValidationExceptionUnknownValue(value);
				super.validate(facesContext, uiComponent, value);
			}
		});
	}
	
	
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(identifiableClass());
	}
	
	@Override
	protected Object data(Class<?> aClass) {
		return newInstance(__formModelClass__());
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		WebNavigationManager.getInstance().redirectToDynamicConsultOne(identifiable);
	}
	
	protected abstract Class<ENTITY> identifiableClass();
	protected abstract ENTITY find(String identifier);
	
	@Getter @Setter
	public static abstract class AbstractSelectForm<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBean implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText @NotNull protected String identifier;
		@Input @InputChoice @InputOneCombo @NotNull protected IDENTIFIABLE identifiable;
		
		public static final String FIELD_IDENTIFIER = "identifier";
		public static final String FIELD_IDENTIFIABLE = "identifiable";
		
	}
	
}
