package org.cyk.ui.web.primefaces.component;

import java.util.Arrays;
import java.util.Collection;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.component.input.IInputSelectOne;
import org.cyk.ui.api.form.IForm;
import org.cyk.ui.web.api.AbstractWebFormContainer;
import org.cyk.ui.web.api.component.IWebInputSelect;
import org.cyk.ui.web.primefaces.controller.MyEntity;
import org.cyk.ui.web.primefaces.controller.MyEntity.MyDetails2;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

public class WebFormContainer extends AbstractWebFormContainer<DynaFormModel,DynaFormLabel,DynaFormControl> {

	private static final long serialVersionUID = -2915809915934469649L;

	private MyEntity e;
	
	@Override
	protected void initialisation() {
		e = new MyEntity();
		super.initialisation();
		
		//e.setDetails1(new MyDetails());
				//p.getDetails1().setDetailsName("Glory to the lord");
	}
	
	@Override
	public Object getObjectModel() {
		return e;
	}
	
	@Override
	public IForm<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> createForm() {
		return new WebForm();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> load(Class<T> aClass) {
		//if(MyDetails.class.equals(aClass))
		//	return Arrays.asList(new MyDetails(),new MyDetails());
		if(MyDetails2.class.equals(aClass))
			return (Collection<T>) Arrays.asList(new MyDetails2(),new MyDetails2(),new MyDetails2());
		return null;
	}
	
	@Override
	public SelectItem item(Object object) {
		return new SelectItem(object,object.toString());
	}
	
	@Override
	public void addItem(IInputSelectOne<?,SelectItem> anInput, Object object) {
		((IWebInputSelect<?, ?>)anInput).getItems().add(item(object));
		System.out.println("WebFormContainer.addItem()");
	}
	/*
	@Override
	public void onSubmit(Object object) throws Exception {
		getSelected().updateFieldsValue(); 
		//for(Object model : form.getModelsObject())
		//	debug(model);
		//debug(getSelected().getModelsObject().iterator().next());
		
		debug(e);
		debug(e.getDetails1());
		//if(getSelected().getParent()!=null){
			//debug(e);
		//	debug(getSelected().getModelsObject().iterator().next());
		//	back();
		//}else{
			//debug(e);
			//debug(e.getDetails1());
		//}
		//debug(e);
		//debug(e.getDetails1());
	}*/

}
