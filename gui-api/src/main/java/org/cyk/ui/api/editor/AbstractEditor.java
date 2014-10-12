/*package org.cyk.ui.api.editor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import java.util.logging.Level;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Log
public abstract class AbstractEditor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractBean implements Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	*//**
	 * to keep navigation trace
	 *//*
	protected Stack<EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> stack = new Stack<>();
	@Setter @Getter protected ValidationPolicy validationPolicy;
	
	@Getter protected UICommandable submitCommand,backCommand,resetValuesCommand,closeCommand,switchCommand;
	@Getter protected Collection<UICommand> commands;
	@Setter @Getter protected AbstractMethod<Object, Object> submitMethodMain,submitDetails,onDebugSubmitMethodMain;
	@Getter @Setter protected Boolean showCommands = Boolean.TRUE,submitMethodMainExecuted=Boolean.FALSE;
	
	@Getter @Setter protected UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> window;
	@Getter protected String title;
	@Getter @Setter protected UIMenu menu = new DefaultMenu();
	@Getter protected Object objectModel;
	@Getter @Setter protected Crud crud;
	
	@Getter protected Collection<EditorListener<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> listeners = new ArrayList<>();
	
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
				AbstractIdentifiable identifiable;
				if(parameter instanceof AbstractFormData<?>){
					((AbstractFormData<?>)parameter).write();
					identifiable = ((AbstractFormData<?>)parameter).getIdentifiable();
				}else if(parameter instanceof AbstractIdentifiable)
					identifiable = (AbstractIdentifiable) parameter;
				else	
					return null;
				switch(crud){
				case CREATE:getWindow().getGenericBusiness().create(identifiable);break;
				case READ:nothing to do break;
				case UPDATE:getWindow().getGenericBusiness().update(identifiable);break;
				case DELETE:getWindow().getGenericBusiness().delete(identifiable);break;
				}
				
				return null;
			}
		};
		submitCommand = UIManager.getInstance().createCommandable("command.save",IconType.ACTION_OK, new AbstractMethod<Object, Object>() {
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
		
		submitCommand.getCommand().setNotifyOnSucceedMethod(new AbstractNotifyOnSucceedMethod<Object>() {
			private static final long serialVersionUID = 8882624469264441089L;
			@Override
			protected Boolean __execute__(Object parameter) {
				return Boolean.TRUE.equals(getSubmitMethodMainExecuted());
			}
		});
		
		backCommand = UIManager.getInstance().createCommandable("command.back",IconType.ACTION_GO_BACK, new AbstractMethod<Object, Object>() {
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
		switchCommand = UIManager.getInstance().createCommandable("command.edit",IconType.ACTION_EDIT, new AbstractMethod<Object, Object>() {
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
		switchCommand.setShowLabel(Boolean.FALSE);
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
		for(EditorListener<FORM,OUTPUTLABEL,INPUT,SELECTITEM> listener : listeners)
			listener.editorInputsCreated(this,inputs);
		inputs.build(crud);
		stack.push(inputs);
		return inputs;
	}
	
	 Command Events 
	
	@Override
	public void onSubmit(Object object) throws Exception {
		submitMethodMainExecuted=Boolean.FALSE;
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
		submitMethodMainExecuted=Boolean.TRUE;
	}
	
	protected void onSubmitDetails(Object object){
		EditorInputs<FORM, OUTPUTLABEL, INPUT, SELECTITEM> form = stack.peek();
		if(form.getParentField() instanceof UIInputSelectOne ){
			try {
				form.getParentField().updateReadOnlyValue();
				//FieldUtils.writeField(form.getParentField().getField(), form.getParentField().getObject(), form.getObjectModel(), Boolean.TRUE);
				//debug(form.getParentField().getObject());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			if(Boolean.TRUE.equals(((IInputSelectOne<?,SELECTITEM>) form.getParentField()).getAddable())){
				addItem((IInputSelectOne<?,SELECTITEM>) form.getParentField(), form.getObjectModel());
			}
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
	
	
	
	protected String text(String id){
		return getWindow().getUiManager().getLanguageBusiness().findText(id);
	}
	
	
	
	public void debug(){
		submitMethodMain = new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 9188822029169134807L;
			@Override
			protected Object __execute__(Object parameter) {
				debug(parameter);
				if(onDebugSubmitMethodMain==null)
					return null;
				return onDebugSubmitMethodMain.execute(parameter);
			}
		};
	}
}
*/