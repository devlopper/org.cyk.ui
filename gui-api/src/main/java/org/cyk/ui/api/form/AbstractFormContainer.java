package org.cyk.ui.api.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.Stack;
import java.util.logging.Level;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.IMessageManager.SeverityType;
import org.cyk.ui.api.component.IComponent;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.input.IInputSelectOne;
import org.cyk.utility.common.AbstractMethod;

@Log
public abstract class AbstractFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractView implements IFormContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	protected Stack<IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> stack = new Stack<>();
	@Getter protected IFormCommand submitCommand;
	@Getter protected Collection<IFormCommand> commands;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		switchTo(getObjectModel(),null);
		submitCommand = new DefaultFormCommand();
		submitCommand.setMessageManager(getMessageManager()); 
		submitCommand.setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -3554292967012003944L;
			@Override
			protected Object __execute__(Object parameter) {
				try {
					onSubmit(parameter);
				} catch (Exception e) {
					getMessageManager().add(SeverityType.ERROR, e, false);
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
		
	}
	
	@SuppressWarnings("unchecked")
	protected void onSubmitDetails(Object object){
		IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = stack.peek();
		System.out.println("V : "+commonUtils.readField(form.getParentField().getObject(), form.getParentField().getField(), false));
		if(form.getParentField() instanceof IInputSelectOne){
			addItem((IInputSelectOne<?,SELECTITEM>) form.getParentField(), form.getObjectModel());
		}
	}
	
	@Override
	public void switchTo(IInputComponent<?> anInput) {
		Object object = commonUtils.readField(anInput.getObject(), anInput.getField(), true);
		try {
			FieldUtils.writeField(anInput.getField(), anInput.getObject(), object, true);
		} catch (Exception e) {
			log.log(Level.SEVERE,e.toString(),e);
			return;
		}
		switchTo(object,anInput);
	}
	
	private void switchTo(Object object,IInputComponent<?> anInput){
		if(object==null)
			throw new IllegalArgumentException("Object model cannot be null");
		IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = createForm();
		form.setContainer(this);
		form.setObjectModel(object);
		form.build();
		if(anInput!=null)
			form.setParentField(anInput);
		stack.push(form);
	}
	
	@Override
	public void back() {
		stack.pop();
	}
	
	@Override
	public void createRow() {
		throw new IllegalStateException("Must not be called");
	}
	
	@Override
	public void add(IComponent<?> component) {
		throw new IllegalStateException("Must not be called");
	}
	
	@Override
	public IForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getSelected() {
		return stack.peek();
	}
	
	@Override 
	public void updateValues() throws Exception {
		stack.peek().updateFieldsValue();
	}
	
	
}
