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
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Named @ViewScoped @Getter @Setter
public class RoleEditPage extends AbstractCrudOnePage<Role> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private ItemCollection<RoleUniformResourceLocatorItem,RoleUniformResourceLocator> roleUniformResourceLocatorCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		roleUniformResourceLocatorCollection = createItemCollection(RoleUniformResourceLocatorItem.class, RoleUniformResourceLocator.class
				,new ItemCollectionWebAdapter<RoleUniformResourceLocatorItem,RoleUniformResourceLocator>(){
			private static final long serialVersionUID = -3872058204105902514L;
			@Override
			public Collection<RoleUniformResourceLocator> load() {
				return rootBusinessLayer.getRoleUniformResourceLocatorBusiness().findByRole(identifiable);
			}
			@Override
			public void instanciated(AbstractItemCollection<RoleUniformResourceLocatorItem, RoleUniformResourceLocator,SelectItem> itemCollection,RoleUniformResourceLocatorItem item) {
				super.instanciated(itemCollection, item);
				if(item.getIdentifiable().getUniformResourceLocator()==null)
					item.getIdentifiable().setUniformResourceLocator(((Form)form.getData()).getUniformResourceLocator());
				if(item.getIdentifiable().getRole()==null)
					item.getIdentifiable().setRole(identifiable);
				item.setName(item.getIdentifiable().getUniformResourceLocator().getName());
		
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
	}
	
	/*@Override
	public void transfer(UICommand command, Object object) throws Exception {
		super.transfer(command, object);
		if(roleUniformResourceLocatorCollection.getAddCommandable().getCommand() == command ){
			form.getSelectedFormData().applyValuesToFields();
		}
	}*/
	
	@Override
	protected void create() {
		RootBusinessLayer.getInstance().getRoleBusiness().save(identifiable, roleUniformResourceLocatorCollection.getIdentifiables());
	}
	
	@Override
	protected void update() {
		RootBusinessLayer.getInstance().getRoleBusiness().save(identifiable, roleUniformResourceLocatorCollection.getIdentifiables());
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<Role> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		@Input @InputText @NotNull private String code;
		@Input @InputText @NotNull private String name;
		@Input @InputChoice @InputOneChoice @InputOneCombo private UniformResourceLocator uniformResourceLocator;
		
		public static final String FIELD_CODE = "code";
		public static final String FIELD_NAME = "name";
		
	}
	
	@Getter @Setter
	public static class RoleUniformResourceLocatorItem extends AbstractItemCollectionItem<RoleUniformResourceLocator> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String name;
	}
	
}
