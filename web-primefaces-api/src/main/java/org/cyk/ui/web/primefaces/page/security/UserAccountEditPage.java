package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.api.model.security.CredentialsFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Binding;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyPickList;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Named @ViewScoped @Getter @Setter
public class UserAccountEditPage extends AbstractCrudOnePage<UserAccount> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			private static final long serialVersionUID = 1L;

			@Override
			public void input(
					ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
					org.cyk.ui.api.data.collector.control.Input<?, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> input) {
				super.input(controlSet, input);
				if(input.getField().getName().equals(Credentials.FIELD_PASSWORD) || input.getField().getName().equals(CredentialsFormModel.FIELD_PASSWORD_CONFIRMATION))
					if(Crud.UPDATE.equals(crud))
						input.setRequired(Boolean.FALSE);
			}
		});
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		org.cyk.ui.api.data.collector.control.InputChoice<?,?,?,?,?,?> roleInputChoice = 
				form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputChoice.class, Form.FIELD_ROLES);
		for(Role role : identifiable.getRoles())
			roleInputChoice.getList().remove(role);
		
		((Form)form.getData()).getRoles().addAll(identifiable.getRoles());
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(UserAccount.class);
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<UserAccount> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		@Input @InputChoice @InputOneChoice @InputOneCombo @Binding(field="user") @NotNull private Person user;
		@IncludeInputs private CredentialsFormModel credentialsForm = new CredentialsFormModel();
		@Input @InputChoice @InputManyChoice @InputManyPickList private List<Role> roles;
		
		public static final String FIELD_USER = "user";
		public static final String FIELD_ROLES = "roles";
		
		@Override
		public void read() {
			super.read();
			user = (Person) identifiable.getUser();
			credentialsForm.getCredentials().setUsername(identifiable.getCredentials().getUsername());
			//credentialsForm.getCredentials().setPassword(identifiable.getCredentials().getPassword());
		}
		
		@Override
		public void write() {
			super.write();
			identifiable.setUser(user);
			identifiable.getCredentials().setUsername(credentialsForm.getCredentials().getUsername());
			if(identifiable.getIdentifier()==null || StringUtils.isNotBlank(credentialsForm.getCredentials().getPassword()))
				identifiable.getCredentials().setPassword(credentialsForm.getCredentials().getPassword());
			identifiable.getRoles().clear();
			identifiable.getRoles().addAll(roles);
		}
	}
	
	@Getter @Setter
	public static class RoleItem extends AbstractItemCollectionItem<Role> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String identifier,name;
	}
	
}
