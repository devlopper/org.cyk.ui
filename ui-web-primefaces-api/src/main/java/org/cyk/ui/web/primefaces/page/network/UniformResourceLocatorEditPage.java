package org.cyk.ui.web.primefaces.page.network;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.InputCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UniformResourceLocatorEditPage extends AbstractCrudOnePage<UniformResourceLocator> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private InputCollection<UniformResourceLocatorParameterItem> uniformResourceLocatorParameterCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		inject(UniformResourceLocatorBusiness.class).prepare(identifiable, crud,new String[]{UniformResourceLocator.FIELD_PARAMETERS});
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		uniformResourceLocatorParameterCollection = instanciateInputCollection(UniformResourceLocatorParameter.class,identifiable.getParameters());	
		
	}
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<UniformResourceLocator> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText @NotNull private String address;
		
		public static final String FIELD_ADDRESS = "address";
	}
	
	@Getter @Setter
	public static class UniformResourceLocatorParameterItem extends InputCollection.Element<UniformResourceLocatorParameter> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		@Input @InputText @NotNull private String name;
		@Input @InputText @NotNull private String value;
		
		public static final String FIELD_NAME = "name";
		public static final String FIELD_VALUE = "value";
	}
	
}
