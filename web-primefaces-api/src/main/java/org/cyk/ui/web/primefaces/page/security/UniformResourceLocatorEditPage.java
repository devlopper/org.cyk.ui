package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UniformResourceLocatorEditPage extends AbstractCrudOnePage<UniformResourceLocator> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private ItemCollection<UniformResourceLocatorParameterItem,UniformResourceLocatorParameter> uniformResourceLocatorParameterCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		List<UniformResourceLocatorParameter> uniformResourceLocatorParameters = Crud.CREATE.equals(crud) ? new ArrayList<UniformResourceLocatorParameter>() 
				: new ArrayList<UniformResourceLocatorParameter>(RootBusinessLayer.getInstance().getUniformResourceLocatorParameterBusiness().findByUniformResourceLocator(identifiable));
		uniformResourceLocatorParameterCollection = createItemCollection(UniformResourceLocatorParameterItem.class, UniformResourceLocatorParameter.class, 
				uniformResourceLocatorParameters,new ItemCollectionWebAdapter<UniformResourceLocatorParameterItem,UniformResourceLocatorParameter>(){
			private static final long serialVersionUID = -3872058204105902514L;
			@Override
			public void instanciated(AbstractItemCollection<UniformResourceLocatorParameterItem, UniformResourceLocatorParameter,SelectItem> itemCollection,UniformResourceLocatorParameterItem item) {
				super.instanciated(itemCollection, item);
				item.setName(item.getIdentifiable().getName());
				item.setValue(item.getIdentifiable().getValue());
			}	
			@Override
			public void write(UniformResourceLocatorParameterItem item) {
				super.write(item);
				item.getIdentifiable().setName(item.getName());
				item.getIdentifiable().setValue(item.getValue());
			}
		});
		
		((Commandable)uniformResourceLocatorParameterCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
	}
	
	@Override
	protected void create() {
		RootBusinessLayer.getInstance().getUniformResourceLocatorBusiness().save(identifiable, uniformResourceLocatorParameterCollection.getIdentifiables());
	}
	@Override
	protected void update() {
		RootBusinessLayer.getInstance().getUniformResourceLocatorBusiness().save(identifiable, uniformResourceLocatorParameterCollection.getIdentifiables());
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<UniformResourceLocator> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		@Input @InputText @NotNull private String code;
		@Input @InputText @NotNull private String name;
		@Input @InputText @NotNull private String address;
		
		public static final String FIELD_CODE = "code";
		public static final String FIELD_NAME = "name";
		
	}
	
	@Getter @Setter
	public static class UniformResourceLocatorParameterItem extends AbstractItemCollectionItem<UniformResourceLocatorParameter> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String name,value;
	}
	
}