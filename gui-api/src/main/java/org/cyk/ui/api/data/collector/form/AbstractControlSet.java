package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

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
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;
 
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
	
	@Override
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addField(Object object,Field field){
		@SuppressWarnings("unchecked")
		OutputLabel<MODEL, ROW, LABEL, CONTROL, SELECTITEM> label = (OutputLabel<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createLabel(UIManager.getInstance().fieldLabel(field));
		add(label);
		
		@SuppressWarnings("unchecked")
		Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> control = (Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createFieldControl(object, field);
		if(control instanceof Input<?,?,?,?,?,?>){
			((Input<?,?,?,?,?,?>)control).setReadOnly(!Boolean.TRUE.equals(getFormData().getForm().getEditable()));
			if(Boolean.TRUE.equals(((Input<?,?,?,?,?,?>)control).getReadOnly()))
				((Input<?,?,?,?,?,?>)control).setRequired(Boolean.FALSE);
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
	
	/* short cuts methods */

	@SuppressWarnings("unchecked")
	public AbstractControlSet<DATA, MODEL,ROW, LABEL, CONTROL, SELECTITEM> addLabel(String label){
		return add((Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>) UIProvider.getInstance().createLabel(label));
	}
	
	private class ControlComparator implements Comparator<Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM>>,Serializable {

		private static final long serialVersionUID = -7637120052702129609L;

		@Override
		public int compare(Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> o1, Control<MODEL, ROW, LABEL, CONTROL, SELECTITEM> o2) {
			if(o1 instanceof Input){
				System.out.println("AbstractControlSet.ControlComparator.compare() "+o1.getClass().getSimpleName()+" : "+o2.getClass().getSimpleName());
				Input<?,?,?,?,?,?> i1 = (Input<?,?,?,?,?,?>) o1;
				Sequence s1 = i1.getField().getAnnotation(Sequence.class);
				if(o2 instanceof Input){
					Input<?,?,?,?,?,?> i2 = (Input<?,?,?,?,?,?>) o2;
					Sequence s2 = i2.getField().getAnnotation(Sequence.class);
					System.out.println("AbstractControlSet.ControlComparator.compare() "+i1.getField().getName()+" : "+i2.getField().getName());
					if(s1.field().equals(i2.getField().getName()))
						if(Direction.BEFORE.equals(s1.direction()))
							return -1;
						else
							return 1;
					else
						if(s2.field().equals(i1.getField().getName()))
							if(Direction.AFTER.equals(s1.direction()))
								return -1;
							else
								return 1;
				}
			}
			return 0;
		}
		
	}
	
}
