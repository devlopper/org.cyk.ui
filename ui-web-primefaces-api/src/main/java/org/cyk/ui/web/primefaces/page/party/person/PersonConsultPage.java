package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.userinterface.container.window.ConsultWindow;

@Named @ViewScoped @Getter @Setter
public class PersonConsultPage extends ConsultWindow implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

}
