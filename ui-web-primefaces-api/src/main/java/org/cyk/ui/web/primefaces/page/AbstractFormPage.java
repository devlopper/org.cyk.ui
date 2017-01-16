package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractFormPage<FORM> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 1L;

	protected FormOneData<FORM> form;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		form = (FormOneData<FORM>) createFormOneData(newInstance(commonUtils.getClassParameterAt(getClass(), 0)),Crud.CREATE);
		form.setDynamic(Boolean.TRUE);
		
	}
	
}
