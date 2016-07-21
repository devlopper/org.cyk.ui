package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.ui.api.model.pattern.tree.NestedSetNodeEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

@Getter @Setter @Named @ViewScoped
public class NestedSetNodeEditPage extends AbstractCrudOnePage<NestedSetNode> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends NestedSetNodeEditFormModel implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
	}

}
