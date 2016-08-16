package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
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

@Named @ViewScoped @Getter @Setter
public class UniformResourceLocatorEditPage extends AbstractCrudOnePage<UniformResourceLocator> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private ItemCollection<UniformResourceLocatorParameterItem,UniformResourceLocatorParameter> uniformResourceLocatorParameterCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		uniformResourceLocatorParameterCollection = createItemCollection(UniformResourceLocatorParameterItem.class, UniformResourceLocatorParameter.class
				,new ItemCollectionWebAdapter<UniformResourceLocatorParameterItem,UniformResourceLocatorParameter>(){
			private static final long serialVersionUID = -3872058204105902514L;
			@Override
			public Collection<UniformResourceLocatorParameter> load() {
				return inject(UniformResourceLocatorParameterBusiness.class).findByUniformResourceLocator(identifiable);
			}
			
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
			@Override
			public Crud getCrud() {
				return crud;
			}
			@Override
			public Boolean isShowAddButton() {
				return Boolean.TRUE;
			}
		});
		((Commandable)uniformResourceLocatorParameterCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
	}
	
	@Override
	protected void create() {
		inject(UniformResourceLocatorBusiness.class).save(identifiable, uniformResourceLocatorParameterCollection.getIdentifiables());
	}
	@Override
	protected void update() {
		inject(UniformResourceLocatorBusiness.class).save(identifiable, uniformResourceLocatorParameterCollection.getIdentifiables());
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
