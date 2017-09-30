package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.geography.ElectronicMailAddress;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ElectronicMailAddressConsultPage extends AbstractContactConsultPage<ElectronicMailAddress> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;


}
