package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;

public abstract class AbstractFormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractForm<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> implements FormOneData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>,Serializable {

	private static final long serialVersionUID = -1043478880255116994L;

	@Getter protected Stack<FormData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>> formDatas = new Stack<>(); 
	@Setter protected ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSetListener;//TODO a temporary solution
	
	/**/
	
	public AbstractFormOneData() {
		submitCommandable = uiProvider.createCommandable(this,"command.save",IconType.ACTION_OK, EventListener.NONE,ProcessGroup.FORM);
	}
	
	@Override
	public <T> T findInputByFieldName(Class<T> aClass, String fieldName) {
		return getSelectedFormData().findInputByFieldName(aClass, fieldName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addChoices(String fieldName, List<SELECTITEM> choices) {
		findInputByFieldName(InputChoice.class, fieldName).getList().addAll(choices);
	}
	
	@Override
	public void __build__() {
		if(Boolean.TRUE.equals(getDynamic())){
			Collection<Class<? extends Annotation>> annotations = new ArrayList<>();
			annotations.add(Input.class);
			annotations.add(IncludeInputs.class);
			FormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> formData = createFormData();
			formData.setData(getData());
			ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> controlSet = formData.createControlSet();
			if(controlSetListener!=null)
				controlSet.getControlSetListeners().add(controlSetListener);
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
		super.__build__();
	}

	private void __objectFields__(List<ObjectField> objectFields,Collection<Class<? extends Annotation>> annotations,Object data){
		for(Field field : commonUtils.getAllFields(data.getClass(), annotations)){
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
		Collection<ObjectField> reorders = new ArrayList<>();
		for(int i=0;i<objectFields.size();)
			if(objectFields.get(i).field.getAnnotation(Sequence.class)==null)
				i++;
			else
				reorders.add(objectFields.remove(i));
		
		for(ObjectField reorder : reorders){
			Sequence sequence = reorder.field.getAnnotation(Sequence.class);
			for(int position=0;position<objectFields.size();)
				if(sequence.field().equals(objectFields.get(position).field.getName())){
					if(Direction.BEFORE.equals(sequence.direction()))
						objectFields.add(position, reorder);
					else
						objectFields.add(position+1, reorder);
					break;
				}else
					position++;
		}
		
		Boolean addRow = null;
		for(ObjectField objectField : objectFields){
			OutputSeperator outputSeparator = objectField.field.getAnnotation(OutputSeperator.class);
			if(outputSeparator!=null){
				String label = null;
				if(ValueType.VALUE.equals(outputSeparator.label().type()))
					label = outputSeparator.label().value();
				else
					label = UIManager.getInstance().textAnnotationValue(objectField.field,outputSeparator.label());
				controlSet.row(null).addSeperator(label);
			}
			
			if(objectField.field.getAnnotation(Input.class)!=null){
				if(addRow==null)
					addRow = Boolean.TRUE;
				
				if(Boolean.TRUE.equals(addRow))
					controlSet.row(objectField.field);
				controlSet.addField(objectField.object,objectField.field);
			}else if(objectField.field.getAnnotation(IncludeInputs.class)!=null){
				Layout layout = objectField.field.getAnnotation(IncludeInputs.class).layout();
				if(Layout.AUTO.equals(layout))
					layout = Layout.HORIZONTAL;
				if(Layout.HORIZONTAL.equals(layout))
					addRow = addRow == null;
				else
					addRow = Boolean.TRUE;
			}
		}
	}
	
	@Override
	public FormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> getSelectedFormData() {
		return formDatas.isEmpty()?null: formDatas.peek();
	}
	
	/**/
	
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
	
	@AllArgsConstructor
	private static class ObjectField implements Serializable {
		
		private static final long serialVersionUID = -4424267370983658359L;
		private Object object;
		private Field field;
		
	}
	
}
