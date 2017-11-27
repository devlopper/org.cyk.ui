package org.cyk.ui.web.api.resources.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.userinterface.container.window.Window;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class BlankPage extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

}
