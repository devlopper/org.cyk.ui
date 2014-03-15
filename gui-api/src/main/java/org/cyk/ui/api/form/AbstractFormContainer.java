package org.cyk.ui.api.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.Stack;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.AbstractViewContainer;
import org.cyk.ui.api.UIView;
import org.cyk.ui.api.form.input.UIInputComponent;
import org.cyk.ui.api.form.input.UIInputSelectOne;
import org.cyk.utility.common.AbstractMethod;

@Log
public abstract class AbstractFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractViewContainer implements IFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	/**
	 * to keep navigation trace
	 */
	protected Stack<UIFormData<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> stack = new Stack<>();
	@Getter protected UIFormCommand submitCommand;
	@Getter protected Collection<UIFormCommand> commands;
	@Setter @Getter protected AbstractMethod<Object, Object> submitMain,submitDetails;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		submitCommand = new DefaultFormCommand();
		submitCommand.setMessageManager(getMessageManager()); 
		submitCommand.setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -3554292967012003944L;
			@Override
			protected Object __execute__(Object parameter) {
				try {
					onSubmit(parameter);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		});		
	}
	
	@Override
	public void onSubmit(Object object) throws Exception {
		getSelected().updateFieldsValue();
		if(stack.size()==1){
			onSubmitMain(object);
		}else{
			onSubmitDetails(object);
			back();
		}
	}
	
	protected void onSubmitMain(Object object){
		if(submitMain!=null)
			submitMain.execute(object);
	}
	
	protected void onSubmitDetails(Object object){
		UIFormData<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = stack.peek();
		if(form.getParentField() instanceof UIInputSelectOne ){
			try {
				//FieldUtils.writeField(form.getParentField().getField(), form.getParentField().getObject(), form.getObjectModel(), Boolean.TRUE);
				
				//System.out.println(ToStringBuilder.reflectionToString(form.getParentField().getObject()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			/*if(Boolean.TRUE.equals(((IInputSelectOne<?,SELECTITEM>) form.getParentField()).getAddable())){
				addItem((IInputSelectOne<?,SELECTITEM>) form.getParentField(), form.getObjectModel());
			}*/
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void switchTo(UIInputComponent<?> anInput) {
		Object object = commonUtils.readField(anInput.getObject(), anInput.getField(), true);
		((UIInputComponent<Object>)anInput).setValue( object );
		try {
			FieldUtils.writeField(anInput.getField(), anInput.getObject(), object, true);
		} catch (Exception e) {
			log.log(Level.SEVERE,e.toString(),e);
			return;
		}
		switchTo(object,anInput);
	}
	
	private void switchTo(Object object,UIInputComponent<?> anInput){
		@SuppressWarnings("unchecked")
		UIFormData<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = (UIFormData<FORM, OUTPUTLABEL, INPUT, SELECTITEM>) build(object);
		if(anInput!=null)
			form.setParentField(anInput);
	}
	
	@Override
	public UIView build(Object object) {
		if(object==null)
			throw new IllegalArgumentException("Object model cannot be null");
		if(objectModel==null)
			objectModel = object;
		UIFormData<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = createFormData();
		form.setContainer(this);
		form.setObjectModel(object);
		form.build();
		stack.push(form);
		return form;
	}
	
	@Override
	public void back() {
		stack.pop();
	}
		
	@Override
	public UIFormData<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getSelected() {
		return stack.peek();
	}
	
	@Override 
	public void updateValues() throws Exception {
		stack.peek().updateFieldsValue();
	}
	
	
	@Override
	public <T> Collection<T> load(Class<T> aClass) {
		return uiManager.collection(aClass);
	}
	

	
	
}
