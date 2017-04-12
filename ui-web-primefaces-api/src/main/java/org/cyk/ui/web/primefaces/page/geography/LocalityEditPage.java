package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LocalityEditPage extends AbstractCrudOnePage<Locality> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		// TODO Auto-generated method stub
		super.initialisation();
		if(!Crud.CREATE.equals(crud)){
			identifiable.getParents().clear();
			identifiable.getParents().add(inject(LocalityBusiness.class).findParent(identifiable));
			System.out.println("LocalityEditPage.initialisation() : "+inject(LocalityBusiness.class).findParent(identifiable));
		}
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		identifiable.setAutomaticallyMoveToNewParent(Crud.UPDATE.equals(crud));
		form.getSubmitCommandable().getCommand().setConfirm(Boolean.TRUE);
		WebInput<?, ?, ?, ?> webInput = (WebInput<?, ?, ?, ?>) form.getInputByFieldName(Form.FIELD_PARENT);
		webInput.getWebInputListeners().add(new WebInput.Listener.Adapter.Default(){
			private static final long serialVersionUID = 138235165190874360L;
			@Override
			public void validate(FacesContext facesContext, UIComponent uiComponent, Object value)throws ValidatorException {
				super.validate(facesContext, uiComponent, value);
				/*if( ((Form)form.getData()).isCurrentParentEqualsNewParent() )
					;
				else
					messageManager.message(SeverityType.WARNING, ((Form)form.getData()).getCurrentParent()+" node will be moved under "+value,false).showGrowl();
				*/
					//webManager.throwValidationException();
			}
		});
	}
	
	@Override
	protected Locality instanciateIdentifiable() {
		Locality locality = super.instanciateIdentifiable();
		locality.getParents().clear();
		locality.getParents().add(webManager.getIdentifiableFromRequestParameter(Locality.class,Boolean.TRUE));
		return locality;
	}
	
	@Getter @Setter 
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractDataTreeForm.FIELD_PARENT,type=Locality.class)
			,@FieldOverride(name=AbstractDataTreeForm.FIELD_TYPE,type=LocalityType.class)
			})
	public static class Form extends AbstractDataTreeForm<Locality,LocalityType> {
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText private String residentName;
		
		public static final String FIELD_RESIDENT_NAME = "residentName";
	
	}
	
}
