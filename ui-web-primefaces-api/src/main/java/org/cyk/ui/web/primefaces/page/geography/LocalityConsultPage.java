package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.geography.LocalityDetails;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.web.primefaces.page.nestedset.AbstractDataTreeNodeConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LocalityConsultPage extends AbstractDataTreeNodeConsultPage<Locality,LocalityDetails> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
