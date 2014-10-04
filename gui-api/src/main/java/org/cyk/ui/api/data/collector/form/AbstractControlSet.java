package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	@Getter protected Collection<Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM>> controls = new ArrayList<>();
	@Getter protected MODEL model;
	@Getter @Setter protected Boolean showInFrame;
	
	@Getter protected Collection<ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM>> controlSetListeners = new ArrayList<>();
		
	/**
	 * Add a row
	 * @return
	 */
	@Override
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> row(){
		if(Boolean.TRUE.equals(__collectPositions__)){
			__rowCount__++;
			__columnCount__=0;
		}else{
			for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners) 
				__row__ = listener.createRow(this);
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
			controls.add(control);
		}else{
			for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners)
				if(control instanceof OutputLabel)
					__label__ = listener.createLabel(this,(OutputLabel<MODEL,ROW, LABEL, CONTROL, SELECTITEM>) control,__row__);
				else{
					__control__ = listener.createControl(this,control,__row__);
					if(__label__!=null && __control__!=null)
						setControlLabel(__label__, __control__);
				}
		}
		return this;
	}
	
	@Override
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addField(Field field){
		@SuppressWarnings("unchecked")
		OutputLabel<MODEL, ROW, LABEL, CONTROL, SELECTITEM> label = (OutputLabel<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createLabel(UIManager.getInstance().fieldLabel(field));
		add(label);
		
		@SuppressWarnings("unchecked")
		Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> control = (Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createFieldControl(formData.getData(), field);
		add(control);
		return this;
	}
	
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addField(String fieldName){
		return addField(CommonUtils.getInstance().getFieldFromClass(formData.getData().getClass(), fieldName));
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
		for(ControlSetListener<DATA,MODEL,ROW,LABEL,CONTROL,SELECTITEM> listener : controlSetListeners) 
			model = listener.createModel(this);
		for(int rowIndex = 0;rowIndex < __rowCount__; rowIndex++){
			row();
			for(Control<MODEL,ROW,LABEL,CONTROL,SELECTITEM> control : controls){
				if(control.getPosition().getRow().getIndex()==rowIndex)
					add(control);
			}
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void applyValuesToFields() throws Exception {
		for(Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> control : controls)
			if(control instanceof Input)
				((Input<?, MODEL, ROW, LABEL, CONTROL, SELECTITEM>)control).applyValueToField(formData.getData());
		
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
	
	/* short cuts methods */

	@SuppressWarnings("unchecked")
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addLabel(String label){
		return add((Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createLabel(label));
	}	
	
}
