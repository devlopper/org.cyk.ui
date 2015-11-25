package org.cyk.ui.api.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.Clazz;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.system.root.business.impl.AbstractOutputDetails;

@Getter @Setter
public class OutputDetailsConfiguration extends Clazz implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	//private String key;
	private FormConfiguration editFormConfiguration;
	
	public OutputDetailsConfiguration(Class<? extends AbstractOutputDetails<?>> detailsClass) {
		super(detailsClass);
		//setUiLabel(UIManager.getInstance().text(string));
		//key = getClazz().getSimpleName().toLowerCase();
	}
		
}
