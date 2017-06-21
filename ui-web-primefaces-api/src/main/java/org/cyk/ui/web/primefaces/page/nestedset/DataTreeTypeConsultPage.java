package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.pattern.tree.DataTreeTypeDetails;
import org.cyk.system.root.model.pattern.tree.DataTreeType;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DataTreeTypeConsultPage extends AbstractDataTreeNodeConsultPage<DataTreeType,DataTreeTypeDetails> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
