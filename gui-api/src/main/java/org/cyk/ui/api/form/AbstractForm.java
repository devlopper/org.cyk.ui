package org.cyk.ui.api.form;

import java.io.Serializable;
import java.util.Collection;
import java.util.Stack;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommand.EventListener;
import org.cyk.ui.api.command.UICommand.ProcessGroup;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.form.input.UIInputComponent;
import org.cyk.ui.api.form.input.UIInputSelectOne;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Log
public abstract class AbstractForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractBean implements UIForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	/**
	 * to keep navigation trace
	 */
	protected Stack<UISubForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> stack = new Stack<>();
	@Getter protected UICommand submitCommand,backCommand,resetValuesCommand,closeCommand,switchCommand;
	@Getter protected Collection<UICommand> commands;
	@Setter @Getter protected AbstractMethod<Object, Object> submitMethodMain/*,submitDetails*/;
	
	@Getter @Setter protected UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> window;
	@Getter protected String title;
	@Getter @Setter protected UIMenu menu = new DefaultMenu();
	//@Getter protected Collection<UIView> views = new LinkedList<>();
	@Getter protected Object objectModel;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		commandInitialisation();
	}
	
	protected void commandInitialisation(){
		submitCommand = createCommand("button.send", new AbstractMethod<Object, Object>() {
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
		},EventListener.NONE,ProcessGroup.FORM);
		submitCommand.setNotifyOnSucceed(Boolean.TRUE);
		backCommand = createCommand("button.back", new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -3554292967012003944L;
			@Override
			protected Object __execute__(Object parameter) {
				try {
					onBack();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		},EventListener.NONE,ProcessGroup.THIS);
		switchCommand = createCommand("button.edit", new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -3554292967012003944L;
			@Override
			protected Object __execute__(Object parameter) {
				try {
					onSwitch(parameter);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		},EventListener.NONE,ProcessGroup.THIS);
	}
	
	protected UICommand createCommand(String labelId,AbstractMethod<Object, Object> executeMethod,EventListener anExecutionPhase,ProcessGroup aProcessGroup){
		UICommand command = new DefaultCommand();
		command.setLabel(text(labelId));
		command.setMessageManager(getWindow().getMessageManager()); 
		command.setExecuteMethod(executeMethod);
		command.setEventListener(anExecutionPhase);
		command.setProcessGroup(aProcessGroup);
		menu.getCommands().add(command);
		return command;
	}
			
	@Override
	public UISubForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> build(Object object) {
		if(object==null)
			throw new IllegalArgumentException("Object model cannot be null");
		if(objectModel==null)
			objectModel = object;
		UISubForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = createFormData();
		form.setContainer(this);
		form.setObjectModel(object);
		form.build();
		stack.push(form);
		return form;
	}
	
	/* Command Events */
	
	@Override
	public void onSubmit(Object object) throws Exception {
		getSelected().updateFieldsValue();
		if(stack.size()==1){
			onSubmitMain(object);
		}else{
			onSubmitDetails(object);
			onBack();
		}
	}
	
	protected void onSubmitMain(Object object){
		if(submitMethodMain!=null)
			submitMethodMain.execute(object);
	}
	
	protected void onSubmitDetails(Object object){
		UISubForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = stack.peek();
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
	
	@Override
	public void onBack() {
		stack.pop();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onSwitch(Object anObject) {
		UIInputComponent<?> anInput = (UIInputComponent<?>) anObject;
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
		UISubForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = (UISubForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM>) build(object);
		if(anInput!=null)
			form.setParentField(anInput);
	}
	
	@Override
	public void onResetValues() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
	
	/**/
	
	@Override
	public UISubForm<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getSelected() {
		return stack.peek();
	}
	
	@Override 
	public void updateValues() throws Exception {
		stack.peek().updateFieldsValue();
	}
	
	
	@Override
	public <T> Collection<T> load(Class<T> aClass) {
		return getWindow().getUiManager().collection(aClass);
	}
	
	@Override
	public Boolean getRoot() {
		return stack.size()==1;
	}
	
	/**/
	
	protected String text(String id){
		return getWindow().getUiManager().getLanguageBusiness().findText(id);
	}
	
}
