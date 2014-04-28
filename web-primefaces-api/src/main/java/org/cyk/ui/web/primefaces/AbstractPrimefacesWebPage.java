package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.form.UIForm;
import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.web.api.AbstractWebPage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

public abstract class AbstractPrimefacesWebPage extends AbstractWebPage<DynaFormModel,DynaFormLabel,DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -1367372077209082614L;
	
	@Inject @Getter protected UIManager uiManager;
	@Inject @Getter protected PrimefacesMessageManager messageManager;
	
	@Override
	public UIForm<DynaFormModel, DynaFormLabel, DynaFormControl, SelectItem> formInstance() {
		return new PrimefacesForm();
	}
	
	@Override
	public <DATA> Table<DATA> tableInstance(Class<DATA> aDataClass) {
		return new PrimefacesTable<>(aDataClass, this);
	}
	
}
