package org.cyk.ui.web.primefaces.resources.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.userinterface.container.window.LoginWindow;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LoginPage extends LoginWindow implements Serializable {

	private static final long serialVersionUID = 1L;

}
