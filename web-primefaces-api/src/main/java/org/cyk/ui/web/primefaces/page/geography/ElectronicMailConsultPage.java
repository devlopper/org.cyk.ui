package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ElectronicMail;

@Named @ViewScoped @Getter @Setter
public class ElectronicMailConsultPage extends AbstractContactConsultPage<ElectronicMail> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;


}
