package org.cyk.ui.api.editor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Stack;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Log
public abstract class AbstractEditor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractBean implements Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	/**
	 * to keep navigation trace
	 */
	protected Stack<EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> stack = new Stack<>();
	@Getter protected UICommandable submitCommand,backCommand,resetValuesCommand,closeCommand,switchCommand;
	@Getter protected Collection<UICommand> commands;
	@Setter @Getter protected AbstractMethod<Object, Object> submitMethodMain/*,submitDetails*/;
	@Getter @Setter protected Boolean showCommands = Boolean.TRUE;
	
	@Getter @Setter protected UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> window;
	@Getter protected String title;
	@Getter @Setter protected UIMenu menu = new DefaultMenu();
	//@Getter protected Collection<UIView> views = new LinkedList<>();
	@Getter protected Object objectModel;
	@Getter @Setter protected Crud crud;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		commandInitialisation();
	}
	
	protected void commandInitialisation(){
		submitMethodMain = new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -3528789218248076908L;
			@Override
			protected Object __execute__(Object parameter) { 
				//TODO must know which crud operation is it
				//getWindow().getGenericBusiness().create((AbstractIdentifiable) parameter);
				return null;
			}
		};
		submitCommand = createCommandable("command.send", new AbstractMethod<Object, Object>() {
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
		submitCommand.getCommand().setNotifyOnSucceed(Boolean.TRUE);
		backCommand = createCommandable("command.back", new AbstractMethod<Object, Object>() {
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
		switchCommand = createCommandable("command.edit", new AbstractMethod<Object, Object>() {
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
	
	protected UICommandable createCommandable(String labelId,AbstractMethod<Object, Object> executeMethod,EventListener anExecutionPhase,ProcessGroup aProcessGroup){
		UICommandable commandable = new DefaultCommandable();
		commandable.setCommand(new DefaultCommand());
		commandable.setLabel(text(labelId));
		commandable.getCommand().setMessageManager(getWindow().getMessageManager()); 
		commandable.getCommand().setExecuteMethod(executeMethod);
		commandable.setEventListener(anExecutionPhase);
		commandable.setProcessGroup(aProcessGroup);
		menu.getCommandables().add(commandable);
		return commandable;
	}
			
	@Override
	public EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> build(Object object) {
		if(object==null)
			throw new IllegalArgumentException("Object model cannot be null");
		if(objectModel==null)
			objectModel = object;
		EditorInputs<FORM, OUTPUTLABEL, INPUT, SELECTITEM> inputs = createEditorInputs();
		inputs.setEditor(this);
		inputs.setObjectModel(object);
		inputs.build(crud);
		stack.push(inputs);
		return inputs;
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
		EditorInputs<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = stack.peek();
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
		EditorInputs<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = (EditorInputs<FORM, OUTPUTLABEL, INPUT, SELECTITEM>) build(object);
		if(anInput!=null)
			form.setParentField(anInput);
	}
	
	@Override
	public void onResetValues() {
		
	}
	
	@Override
	public void onClose() {
		
	}
	
	/**/
	
	@Override
	public EditorInputs<FORM, OUTPUTLABEL, INPUT, SELECTITEM> getSelected() {
		return stack.peek();
	}
	
	@Override 
	public void updateValues() throws Exception {
		stack.peek().updateFieldsValue();
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
