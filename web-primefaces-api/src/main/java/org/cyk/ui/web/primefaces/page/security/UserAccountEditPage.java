package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.api.model.security.CredentialsFormModel;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Binding;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Named @ViewScoped @Getter @Setter
public class UserAccountEditPage extends AbstractCrudOnePage<UserAccount> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private ItemCollection<RoleItem,Role> roleCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		roleCollection = createItemCollection(RoleItem.class, Role.class, 
				new ArrayList<Role>(),new ItemCollectionWebAdapter<RoleItem,Role>(){
			private static final long serialVersionUID = -3872058204105902514L;
			@Override
			public void instanciated(AbstractItemCollection<RoleItem, Role,SelectItem> itemCollection,RoleItem item) {
				super.instanciated(itemCollection, item);
				/*
				item.setIdentifier(((Form)form.getData()).getRole());
				item.setNames(item.getIdentifiable().getStudent().getPerson().getNames());
				item.setClassroomSession(RootBusinessLayer.getInstance().getFormatterBusiness().format(item.getIdentifiable().getClassroomSessionDivisionSubject()
						.getClassroomSessionDivision().getClassroomSession()));
				*/
			}	
		});
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(UserAccount.class);
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<UserAccount> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		@Input @InputChoice @InputOneChoice @InputOneCombo @Binding(field="user")
		private Person user;
		
		@IncludeInputs private CredentialsFormModel credentialsFormModel = new CredentialsFormModel();
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private Role role;
		
		public static final String USER_ACCOUNT = "userAccount";
		public static final String ROLE = "role";
	}
	
	@Getter @Setter
	public static class RoleItem extends AbstractItemCollectionItem<Role> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String identifier,name;
	}
	
}
