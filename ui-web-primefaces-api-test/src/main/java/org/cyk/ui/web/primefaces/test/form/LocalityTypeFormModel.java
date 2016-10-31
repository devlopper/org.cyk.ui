package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class LocalityTypeFormModel extends AbstractFormModel<LocalityType> implements Serializable {

	private static final long serialVersionUID = 7821779051431445562L;

	@Input @InputText
	private String code;
	
	@Input @InputText
	private String name;
	
}
