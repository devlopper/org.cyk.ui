package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import lombok.Getter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.impl.AbstractFieldSorter.FieldSorter;
import org.cyk.system.root.business.impl.AbstractFieldSorter.ObjectField;
import org.cyk.system.root.business.impl.AbstractFieldSorter.ObjectFieldSorter;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator.SeperatorLocation;
import org.cyk.utility.common.annotation.user.interfaces.OutputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputText.OutputTextLocation;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;

public abstract class AbstractFormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractForm<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> implements FormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>,Serializable {

	private static final long serialVersionUID = -1043478880255116994L;

	@Getter protected final Stack<FormData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>> formDatas = new Stack<>(); 
	@Getter protected final Collection<ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> controlSetListeners = new ArrayList<>();
	@Getter protected final Collection<AbstractItemCollection<?,?>> itemCollections = new ArrayList<>() ;
	
	/**/
	
	public AbstractFormOneData() {
		submitCommandable = uiProvider.createCommandable(this,"command.save",IconType.ACTION_OK, EventListener.NONE,ProcessGroup.FORM);
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
			/*
			for(Field field : commonUtils.getAllFields(getData().getClass(), annotations)){
				if(field.getAnnotation(Input.class)!=null)
					controlSet.row().addField(field);
				else if(field.getAnnotation(IncludeInputs.class)!=null){
					
				}
			}
			*/
			
			List<ObjectField> objectFields = new ArrayList<>();
			__objectFields__(objectFields,annotations,getData());
			
			__autoBuild__(objectFields, controlSet);
		}
		//submitCommandable.setRendered(Crud.READ.equals(controlSet.get));
		super.__build__();
	}

	private void __objectFields__(List<ObjectField> objectFields,Collection<Class<? extends Annotation>> annotations,Object data){
		List<Field> fields = new ArrayList<>(commonUtils.getAllFields(data.getClass(), annotations));
		new FieldSorter(fields,data.getClass()).sort();
		for(ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> listener : controlSetListeners)
			listener.sort(fields);
		for(Field field : fields){
			objectFields.add(new ObjectField(data, field));
			
			if(field.getAnnotation(IncludeInputs.class)!=null){
				Object details = commonUtils.readField(data, field, Boolean.TRUE);
				try {
					FieldUtils.writeField(field, data, details, Boolean.TRUE);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				__objectFields__(objectFields,annotations, details);
			}
		}
	}
	
	private void __autoBuild__(List<ObjectField> objectFields,ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet){
		logDebug("Auto build starts. number of object fields = {}",objectFields.size());
		new ObjectFieldSorter(objectFields,null).sort();
		Boolean addRow = null;//Boolean seperatorAdded = null;
		for(ObjectField objectField : objectFields){
			Boolean build = Boolean.TRUE;
			for(ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> listener : controlSetListeners){
				Boolean v = listener.build(objectField.getField());
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
					separatorLabel = UIManager.getInstance().getLanguageBusiness().findAnnotationText(objectField.getField(),outputSeperator.label());
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
					text = UIManager.getInstance().getLanguageBusiness().findAnnotationText(objectField.getField(),outputText.label());
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
					logDebug("Row created form field {}",objectField.getField().getName());
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
					layout = Layout.HORIZONTAL;
				
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
		if(Boolean.TRUE.equals(getEditable()))
			try {
				getFormDatas().peek().applyValuesToFields();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}
	
	/**/
	
	
	
	/**/
			
}
