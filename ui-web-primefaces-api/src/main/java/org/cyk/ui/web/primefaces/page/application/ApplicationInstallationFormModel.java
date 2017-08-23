package org.cyk.ui.web.primefaces.page.application;

import java.io.Serializable;

import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.model.security.License;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApplicationInstallationFormModel extends AbstractFormModel<Application> implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;

	@IncludeInputs
	@OutputSeperator(label=@Text(value="application"))
	private UserCredentials administratorCredentials = new UserCredentials();
	
	@IncludeInputs
	@OutputSeperator(label=@Text(value="manager"))
	private UserCredentials managerCredentials = new UserCredentials();
	
	@IncludeInputs(label=@Text(value="field.license"),layout=Layout.VERTICAL)
	@OutputSeperator(label=@Text(value="field.license"))
	private License license = new License();
	
	@Input @InputBooleanCheck
	private Boolean fakedData = Boolean.FALSE;
	
	private Installation installation = new Installation();
	
	public ApplicationInstallationFormModel(Application application) {
		/*installation.setApplication(identifiable = application);
		installation.setAdministratorCredentials(administratorCredentials.getCredentialsInputs().getCredentials());
		
		installation.setManager(new Person());
		installation.setManagerCredentials(managerCredentials.getCredentialsInputs().getCredentials());
		
		installation.setLicense(license);
		installation.setFaked(fakedData);*/
	}
	
	@Override
	public void write() {
		super.write();
		installation.getApplication().setName(administratorCredentials.getName());
		installation.getManager().setName(managerCredentials.getName());
	}
	
}
