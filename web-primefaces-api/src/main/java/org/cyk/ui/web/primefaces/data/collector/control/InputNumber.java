package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.logging.Level;

import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ClassUtils;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter @NoArgsConstructor @Log
public class InputNumber extends AbstractInput<Number> implements org.cyk.ui.web.api.data.collector.control.WebInputNumber<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputNumber<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = 1390099136018097004L;
	
	private Number minimum,maximum;
	
	@Override
	public Converter getConverter() {
		Class<?> wrapper = ClassUtils.primitiveToWrapper(field.getType());
		try{
			return (Converter) Class.forName("javax.faces.convert."+wrapper.getSimpleName()+"Converter").newInstance();
		}catch(Exception e){
			log.log(Level.SEVERE,e.toString(),e);
		}
		return null;
	}
	
}
