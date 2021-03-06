package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.pattern.tree.DataTreeBusinessImpl;
import org.cyk.system.root.model.pattern.tree.DataTree;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("rawtypes")
@Named @ViewScoped @Getter @Setter
public class DataTreeConsultPage extends AbstractDataTreeNodeConsultPage<DataTree,DataTreeBusinessImpl.Details> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
