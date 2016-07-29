package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class UniformResourceLocatorConsultPage extends AbstractConsultPage<UniformResourceLocator> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
