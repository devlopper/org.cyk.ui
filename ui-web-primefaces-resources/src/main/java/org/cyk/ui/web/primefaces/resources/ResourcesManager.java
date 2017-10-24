package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.userinterface.Component.Visible;
import org.cyk.utility.common.userinterface.input.InputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton @Named @Getter @Setter @Accessors(chain=true)
public class ResourcesManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Visible confirmDialog = new Visible();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
	}
	
	public Object getInputTextDefaultTemplate(){
		return Properties.getDefaultValue(InputText.class, Properties.TEMPLATE);
	}
	
}
