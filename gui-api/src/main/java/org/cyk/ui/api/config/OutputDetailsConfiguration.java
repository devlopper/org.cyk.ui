package org.cyk.ui.api.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.AbstractOutputDetails;

@Getter @Setter
public class OutputDetailsConfiguration extends ClassConfiguration<AbstractOutputDetails<?>> implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	private String key;
	private FormConfiguration editFormConfiguration;
	
	public OutputDetailsConfiguration(Class<? extends AbstractOutputDetails<?>> detailsClass) {
		super(detailsClass);
		String string = StringUtils.replace(clazz.getName().toLowerCase(), "$", ".");
		this.name = UIManager.getInstance().text(string);
		key = clazz.getSimpleName().toLowerCase();
	}
		
}
