package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.utility.common.CommonUtils;
 
@NoArgsConstructor
public abstract class AbstractControlSet<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractView implements ControlSet<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>,Serializable {

	private static final long serialVersionUID = -1043478880255116994L;
	 
	private Boolean __collectPositions__ = Boolean.TRUE;
	private Integer __rowCount__=0,__columnCount__;
	private ROW __row__;
	private LABEL __label__;
	private CONTROL __control__;
	
	@Getter protected ControlSetDescriptor descriptor = new ControlSetDescriptor();
	@Getter @Setter protected FormData<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> formData;
	@Getter protected List<Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM>> controls = new ArrayList<>();
	@Getter protected MODEL model;
	@Getter @Setter protected Boolean showInFrame;
	
	@Getter protected Collection<ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>> controlSetListeners = new ArrayList<>();
		
	/**
	 * Add a row
	 * @return
	 */
	@Override
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> row(Object object){
		if(Boolean.TRUE.equals(__collectPositions__)){
			Boolean create = null;
			for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners){ 
				Boolean c = listener.canCreateRow(this, object);
				if(c!=null)
					create = c;
			}
			if(controls.isEmpty() || Boolean.TRUE.equals(create)){
				__rowCount__++;
				__columnCount__=0;
			}
		}else{
			for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners){ 
				ROW c = listener.createRow(this);
				if(c!=null)
					__row__ = c;
			}
		}
		return this;
	}

	/**
	 * Add a control
	 * @param control
	 * @return
	 */
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> add(Control<MODEL,ROW, LABEL, CONTROL, SELECTITEM> control){
		if(Boolean.TRUE.equals(__collectPositions__)){
			control.getPosition().getRow().setIndex(__rowCount__-1);
			control.getPosition().getColumn().setIndex(++__columnCount__);
			control.setSet(this);
			controls.add(control);
		}else{
			for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners)
				if(control instanceof OutputLabel){
					LABEL l = listener.createLabel(this,(OutputLabel<MODEL,ROW, LABEL, CONTROL, SELECTITEM>) control,__row__);
					if(l!=null)
						__label__ = l;
				}else{
					__control__ = listener.createControl(this,control,__row__);
					if(__label__!=null && __control__!=null)
						setControlLabel(__label__, __control__);
				}
		}
		return this;
	}
	
	protected String getFieldLabel(Object data,Field field){
		String fieldLabel = UIManager.getInstance().getLanguageBusiness().findFieldLabelText(field).getValue();
		for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners){ 
			String c = listener.fiedLabel(this, data,field);
			if(StringUtils.isNotBlank(c))
				fieldLabel = c;
		}
		return fieldLabel;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addField(Object object,Field field){
		Boolean showFieldLabel = Boolean.TRUE;
		for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners){ 
			Boolean value = listener.showFieldLabel(this, field);
			if(value!=null)
				showFieldLabel = value;
		}
		String fieldLabel = getFieldLabel(object,field);
		if(Boolean.TRUE.equals(showFieldLabel)){
			OutputLabel<MODEL, ROW, LABEL, CONTROL, SELECTITEM> label = (OutputLabel<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createLabel(fieldLabel);
			/*
			for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners) 
				listener.labelBuilt(this, field, (LABEL) label);
			*/
			add(label);
		}
		Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> control = (Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createFieldControl(object, field);
		if(control instanceof Input<?,?,?,?,?,?>){
			org.cyk.utility.common.annotation.user.interfaces.Input inputAnnotation = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class);
			Input<?,?,?,?,?,?> input = (Input<?,?,?,?,?,?>)control;
			input.setRequiredMessage(null);//Required message will be computed base on label
			input.setLabel(fieldLabel);
			input.setReadOnly(Boolean.TRUE.equals(inputAnnotation.readOnly()) || !Boolean.TRUE.equals(getFormData().getForm().getEditable()));
			input.setKeepShowingInputOnReadOnly(inputAnnotation.readOnly());//FIXME a mic mac to solve issue of value not submitted when rendering output text
			input.setDisabled(Boolean.TRUE.equals(inputAnnotation.disabled()));
			
			if(Boolean.TRUE.equals(input.getReadOnly())){
				if(input.getField().getType().equals(File.class)){
					control = (Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createOutputImage((File) input.getValue());
				}else{
					input.setRequired(Boolean.FALSE);
				}
			}
			
			if(control instanceof Input<?,?,?,?,?,?>){
				for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners) 
					listener.input(this,(Input<?, MODEL, ROW, LABEL, CONTROL, SELECTITEM>)control);
			}
		}
			
		add(control);
		return this;
	}
	
	@Override
	public ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> addSeperator(String label) {
		@SuppressWarnings("unchecked")
		Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> control = (Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createSeparator(label);
		control.getPosition().getColumn().setSpan(2);//TODO to be done automatically
		add(control);
		return this;
	}
	
	@Override
	public ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> addText(String text) {
		@SuppressWarnings("unchecked")
		Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> control = (Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createOutputText(text);
		control.getPosition().getColumn().setSpan(2);//TODO to be done automatically
		add(control);
		return this;
	}
		
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addField(Object object,String fieldName){
		return addField(object, fieldName);//TODO recursive?
	}
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addField(String fieldName){
		return addField(CommonUtils.getInstance().getFieldFromClass(formData.getData().getClass(), fieldName),fieldName);
	}
	
	public void setControlLabel(LABEL label,CONTROL control){
		for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners) 
			listener.setControlLabel(this, control, label);
	}
	
	/**/
	
	@Override
	public void __build__() {
		super.__build__();
		__collectPositions__ = Boolean.FALSE;
		for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners){ 
			MODEL m = listener.createModel(this);
			if(m!=null)
				model = m;
		}
		// sort the controls 
		/*
		Collection<Input<?,?,?,?,?,?>> sequences = new ArrayList<>();
		for(int i=0;i<controls.size(); ){
			Control<?,?,?,?,?> control = controls.get(i);
			if(control instanceof Input<?,?,?,?,?,?>){
				Input<?,?,?,?,?,?> input = (Input<?, ?, ?, ?, ?, ?>) control;
				if(input.getField().getAnnotation(Sequence.class)==null)
					i++;
				else{
					sequences.add((Input<?, ?, ?, ?, ?, ?>)controls.remove(i));
					i += 2;
				}
			}else
				i++;
		}
		*/
		
		// found the longest row length
		Integer maximumColumnCount = 0;
		for(int rowIndex = 0;rowIndex < __rowCount__; rowIndex++){
			Integer columnCount = 0;
			for(Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control : controls)
				if(control.getPosition().getRow().getIndex()==rowIndex)
					columnCount += control.getPosition().getColumn().getSpan();
			if(columnCount > maximumColumnCount)
				maximumColumnCount = columnCount;
		}
		// actualize the control column span
		for(int rowIndex = 0;rowIndex < __rowCount__; rowIndex++){
			row(null);
			Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> lastControl = null;
			for(Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control : controls)
				if(control.getPosition().getRow().getIndex()==rowIndex)
					lastControl = control;
			
			lastControl.getPosition().getColumn().setSpan(maximumColumnCount-lastControl.getPosition().getColumn().getIndex()+1);
		}		
		// use the target specific control
		for(int rowIndex = 0;rowIndex < __rowCount__; rowIndex++){
			row(null);
			Integer columnCount = 0;
			for(Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control : controls){
				if(control.getPosition().getRow().getIndex()==rowIndex){
					add(control);
					columnCount += control.getPosition().getColumn().getSpan();
				}
			}
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void applyValuesToFields() throws Exception {
		for(Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> control : controls)
			if(control instanceof Input)
				((Input<?, MODEL, ROW, LABEL, CONTROL, SELECTITEM>)control).applyValueToField();
		
	}
		
	@Override @SuppressWarnings("unchecked")
	public <T> T findInputByFieldName(Class<T> aClass, String fieldName) {
		for(Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control : controls){
			if( (control instanceof Input<?,?,?,?,?,?>) 
					&& ( ((Input<?,?,?,?,?,?>)control).getField().getName().equals(fieldName)) 
					&& aClass.isAssignableFrom(control.getClass()))
				return (T) control;
		}
		return null;
	}
	
	@Override @SuppressWarnings("unchecked")
	public <T> T findControlByIndex(Class<T> aClass, Integer index) {
		Integer position = 0;
		for(Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control : controls){
			if( aClass.isAssignableFrom(control.getClass())){
				if(position++ == index)
					return (T) control;
			}
		}
		return null;
	}
	
	@Override
	public Input<?, ?, ?, ?, ?, ?> findInputByFieldName(String fieldName) {
		for(Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control : controls){
			if( (control instanceof Input<?,?,?,?,?,?>) 
					&& ( ((Input<?,?,?,?,?,?>)control).getField().getName().equals(fieldName)) 
					)
				return (Input<?, ?, ?, ?, ?, ?>) control;
		}
		return null;
	}
	
	/* short cuts methods */

	@SuppressWarnings("unchecked")
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addLabel(String label){
		return add((Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createLabel(label));
	}
		
}
