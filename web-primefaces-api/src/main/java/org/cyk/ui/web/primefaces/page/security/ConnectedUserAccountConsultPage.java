package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ConnectedUserAccountConsultPage extends AbstractUserAccountConsultPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass) {
		return (T) getUserSession().getUserAccount();
	}
	
}
