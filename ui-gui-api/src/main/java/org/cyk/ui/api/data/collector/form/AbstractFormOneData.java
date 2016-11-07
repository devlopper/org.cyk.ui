package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.utility.common.AbstractFieldSorter.ObjectField;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Input.RendererStrategy;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator.SeperatorLocation;
import org.cyk.utility.common.annotation.user.interfaces.OutputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputText.OutputTextLocation;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractFormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractForm<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> implements FormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>,Serializable {

	private static final long serialVersionUID = -1043478880255116994L;

	@Getter @Setter protected UIWindow<MODEL, LABEL, CONTROL, SELECTITEM> window;
	@Getter protected final Stack<FormData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>> formDatas = new Stack<>(); 
	@Getter protected final Collection<ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> controlSetListeners = new ArrayList<>();
	@Getter protected final Collection<AbstractItemCollection<?,?,?>> itemCollections = new ArrayList<>() ;
	
	/**/
	
	public AbstractFormOneData(String submitCommandableLabelId) {
		submitCommandable = Builder.instanciateOne().setLabelFromId(submitCommandableLabelId).setCommandListener(this).setIcon(Icon.ACTION_SAVE)
				.setEventListener(EventListener.NONE).setProcessGroup(ProcessGroup.FORM).create();
	}
	public AbstractFormOneData() {
		this("command.execute");
	}
	
	public void addControlSetListener(ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> listener){
		if(listener!=null)
			controlSetListeners.add(listener);
	}
	
	@Override
	public <T> T findInputByClassByFieldName(Class<T> aClass, String fieldName) {
		return getSelectedFormData().findInputByClassByFieldName(aClass, fieldName);
	}
	
	@Override
	public <T> T findControlByClassByIndex(Class<T> aClass, Integer index) {
		return getSelectedFormData().findControlByClassByIndex(aClass, index);
	}
	
	@Override
	public org.cyk.ui.api.data.collector.control.Input<?, ?, ?, ?, ?, ?> findInputByFieldName(String fieldName) {
		return getSelectedFormData().findInputByFieldName(fieldName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addChoices(String fieldName, List<SELECTITEM> choices) {
		findInputByClassByFieldName(InputChoice.class, fieldName).getList().addAll(choices);
	}
	
	@Override
	public void __build__() {
		if(Boolean.TRUE.equals(getDynamic())){
			Collection<Class<? extends Annotation>> annotations = new ArrayList<>();
			annotations.add(Input.class);
			annotations.add(IncludeInputs.class);
			FormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> formData = createFormData();
			formData.setData(getData());
			formData.setUserDeviceType(userDeviceType);
			ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet = formData.createControlSet();
			controlSet.setUserDeviceType(formData.getUserDeviceType());
			
			controlSet.getControlSetListeners().addAll(controlSetListeners);
			
			List<ObjectField> objectFields = new ArrayList<>();
			__objectFields__(objectFields,annotations,getData());
			
			__autoBuild__(objectFields, controlSet);
			
		}
		//submitCommandable.setRendered(Crud.READ.equals(controlSet.get));
		super.__build__();
	}

	private void __objectFields__(List<ObjectField> objectFields,Collection<Class<? extends Annotation>> annotations,final Object data){
		final List<Field> fields = new ArrayList<>(commonUtils.getAllFields(data.getClass(), annotations));
		
		listenerUtils.execute(controlSetListeners, new ListenerUtils.VoidMethod<ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>>() {
			@Override
			public void execute(ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> listener) {
				listener.sort(fields);
			}
		});
		
		//new FieldSorter(fields,data.getClass()).sort();
		
		for(ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> listener : controlSetListeners)
			listener.sort(fields);
		
		for(Field field : fields){
			final Field f = field;
			if(Boolean.TRUE.equals(listenerUtils.getBoolean(controlSetListeners, new ListenerUtils.BooleanMethod<ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>>() {
				@Override
				public Boolean execute(ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> listener) {
					return listener.build(data,f);
				}
				@Override
				public Boolean getNullValue() {
					return Boolean.TRUE;
				}
			}))){
				Input input = field.getAnnotation(Input.class);
				if(input==null){
					objectFields.add(new ObjectField(data, field));	
				}else{
					if( isInput(data,field, getUserSession()) )
						objectFields.add(new ObjectField(data, field));	
				}
				
				if(field.getAnnotation(IncludeInputs.class)!=null){
					Object details = commonUtils.readField(data, field, Boolean.TRUE);
					try {
						FieldUtils.writeField(field, data, details, Boolean.TRUE);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					__objectFields__(objectFields,annotations, details);
				}
			}else{
				
			}
				
		}
	}
	
	public static Boolean isInput(Object data,Field field,AbstractUserSession<?, ?> userSession){
		Input input = field.getAnnotation(Input.class);
		if(input==null)
			return Boolean.FALSE;
		if(data instanceof AbstractFormModel<?> && !Boolean.TRUE.equals(((AbstractFormModel<?>)data).isFieldRendered(field)))
			return Boolean.FALSE;
		RendererStrategy rendererStrategy = input.rendererStrategy();
		if(RendererStrategy.AUTO.equals(rendererStrategy))
			rendererStrategy = RendererStrategy.ALWAYS;
		return RendererStrategy.ALWAYS.equals(rendererStrategy)
			|| (RendererStrategy.ADMINISTRATION.equals(rendererStrategy) && Boolean.TRUE.equals(userSession.getIsAdministrator()))
			|| (RendererStrategy.MANAGEMENT.equals(rendererStrategy) && Boolean.TRUE.equals(userSession.getIsManager())) ;
	}
	
	private void __autoBuild__(final List<ObjectField> objectFields,ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet){
		logDebug("Auto build starts. number of object fields = {}",objectFields.size());
		listenerUtils.execute(controlSetListeners, new ListenerUtils.VoidMethod<ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>>() {
			@Override
			public void execute(ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> listener) {
				listener.sortObjectFields(objectFields,data.getClass());
			}
		});
		//new ObjectFieldSorter(objectFields,null).sort();
		Boolean addRow = null;//Boolean seperatorAdded = null;
		for(ObjectField objectField : objectFields){
			Boolean build = Boolean.TRUE;
			for(ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> listener : controlSetListeners){
				Boolean v = listener.build(objectField.getObject(),objectField.getField());
				if(v!=null)
					build = Boolean.TRUE.equals(v);
			}
			if(Boolean.FALSE.equals(build)){
				logTrace("field {} will not be built",objectField.getField().getName());
				continue;
			}
			OutputSeperator outputSeperator = objectField.getField().getAnnotation(OutputSeperator.class);
			SeperatorLocation seperatorLocation = null;
			String separatorLabel = null;
			if(outputSeperator!=null){
				seperatorLocation = outputSeperator.location();
				if(ValueType.VALUE.equals(outputSeperator.label().type()))
					separatorLabel = outputSeperator.label().value();
				else
					separatorLabel = UIManager.getInstance().getLanguageBusiness().findAnnotationText(objectField.getField(),outputSeperator.label()).getValue();
				if(OutputSeperator.SeperatorLocation.AUTO.equals(outputSeperator.location()))
					seperatorLocation = OutputSeperator.SeperatorLocation.BEFORE;
				if(OutputSeperator.SeperatorLocation.BEFORE.equals(seperatorLocation))
					controlSet.row(null).addSeperator(separatorLabel);
				//seperatorAdded = Boolean.TRUE;
				//controlSet.row(null);
				addRow = null;
			}
			
			OutputText outputText = objectField.getField().getAnnotation(OutputText.class);
			OutputTextLocation outputTextLocation = null;
			String text = null;
			if(outputText!=null){
				outputTextLocation = outputText.location();
				if(ValueType.VALUE.equals(outputText.label().type()))
					text = outputText.label().value();
				else
					text = UIManager.getInstance().getLanguageBusiness().findAnnotationText(objectField.getField(),outputText.label()).getValue();
				if(OutputText.OutputTextLocation.AUTO.equals(outputText.location()))
					outputTextLocation = OutputText.OutputTextLocation.BEFORE;
				if(OutputText.OutputTextLocation.BEFORE.equals(outputTextLocation))
					controlSet.row(null).addText(text);
				addRow = null;
			}
			if(objectField.getField().getAnnotation(Input.class)!=null){
				if(addRow==null)
					addRow = Boolean.TRUE;
				if(Boolean.TRUE.equals(addRow)){
					controlSet.row(objectField.getField());
					logDebug("Row created from field {}",objectField.getField().getName());
				}
				/*if(Boolean.TRUE.equals(seperatorAdded)){
					controlSet.row(null);
					seperatorAdded = Boolean.FALSE;
				}*/
				controlSet.addField(objectField.getObject(),objectField.getField());
				//if(objectField.getField().getAnnotation(Input.class)!=null)
					//System.out.println(objectField.getObject().getClass());
					//inputChoice((InputChoice<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>) objectField.getObject());
			}else if(objectField.getField().getAnnotation(IncludeInputs.class)!=null){
				Layout layout = objectField.getField().getAnnotation(IncludeInputs.class).layout();
				if(Layout.AUTO.equals(layout))
					layout = Layout.VERTICAL;
				
				if(Layout.HORIZONTAL.equals(layout)){
					addRow = addRow == null;
				}else
					addRow = Boolean.TRUE;
			}
			
			if(OutputSeperator.SeperatorLocation.AFTER.equals(seperatorLocation))
				controlSet.row(null).addSeperator(separatorLabel);
			
			if(OutputText.OutputTextLocation.AFTER.equals(outputTextLocation))
				controlSet.row(null).addText(text);
		}
	}
	
	@Override
	public FormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> getSelectedFormData() {
		return formDatas.isEmpty()?null: formDatas.peek();
	}
	
	/**/
	
	protected void inputChoice(InputChoice<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> inputChoice){
		
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) {
		if(getCollection()==null){
			transfer();
		}else{
			for(FormOneData<?, ?, ?, ?, ?, ?> formOneData : getCollection().getCollection())
				formOneData.transfer();	
		}
	}
	
	public void transfer(){
		if(Boolean.TRUE.equals(getEditable()))
			try {
				getFormDatas().peek().applyValuesToFields();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}
	
	@Override
	public void validate() {
		//TODO to be implemented
	}
	
	/**/
	
	
	
	/**/
			
}
