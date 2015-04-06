package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.data.collector.control.AbstractFieldSorter.FieldSorter;
import org.cyk.ui.api.data.collector.control.AbstractFieldSorter.ObjectField;
import org.cyk.ui.api.data.collector.control.AbstractFieldSorter.ObjectFieldSorter;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
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
		//submitCommandable.setRendered(Crud.READ.equals(controlSet.get));
		super.__build__();
	}

	private void __objectFields__(List<ObjectField> objectFields,Collection<Class<? extends Annotation>> annotations,Object data){
		List<Field> fields = new ArrayList<>(commonUtils.getAllFields(data.getClass(), annotations));
		
		new FieldSorter(fields,data.getClass()).sort();
		
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
		
		new ObjectFieldSorter(objectFields,null).sort();
		
		Boolean addRow = null;//Boolean seperatorAdded = null;
		for(ObjectField objectField : objectFields){
			OutputSeperator outputSeparator = objectField.getField().getAnnotation(OutputSeperator.class);
			if(outputSeparator!=null){
				String label = null;
				if(ValueType.VALUE.equals(outputSeparator.label().type()))
					label = outputSeparator.label().value();
				else
					label = UIManager.getInstance().textAnnotationValue(objectField.getField(),outputSeparator.label());
				controlSet.row(null).addSeperator(label);
				//seperatorAdded = Boolean.TRUE;
				//controlSet.row(null);
				addRow = null;
			}
			
			if(objectField.getField().getAnnotation(Input.class)!=null){
				if(addRow==null)
					addRow = Boolean.TRUE;
				
				if(Boolean.TRUE.equals(addRow))
					controlSet.row(objectField.getField());
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
