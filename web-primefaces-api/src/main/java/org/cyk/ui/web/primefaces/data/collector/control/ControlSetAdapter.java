package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.NoArgsConstructor;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.data.collector.form.ControlSetListener;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@NoArgsConstructor
public class ControlSetAdapter<DATA> extends ControlSetListener.Adapter.Default<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> implements Serializable {
	
	private static final long serialVersionUID = -4469671146996017509L;

	public ControlSetAdapter(Class<?> identifiableClass, Crud crud) {
		super(identifiableClass, crud);
	}

	/**/
	
	@NoArgsConstructor
	public static class Form<DATA> extends ControlSetListener.Adapter.Default.Form<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> implements Serializable {

		private static final long serialVersionUID = 1L;

		public Form(Class<?> identifiableClass, Crud crud) {
			super(identifiableClass, crud);
		}

	}
	
	@NoArgsConstructor
	public static class Details<DATA> extends ControlSetListener.Adapter.Default.Details<DATA, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> implements Serializable {

		private static final long serialVersionUID = 1L;

		public Details(Class<?> identifiableClass, Crud crud) {
			super(identifiableClass, crud);
		}
		
	}
}
