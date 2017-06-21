package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.pattern.tree.DataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.web.primefaces.page.geography.AbstractDataTreeNodeEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DataTreeEditPage extends AbstractDataTreeNodeEditPage<DataTree> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;	
	
	@Getter @Setter 
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractDataTreeForm.FIELD_PARENT,type=DataTree.class)
			,@FieldOverride(name=AbstractDataTreeForm.FIELD_TYPE,type=DataTreeType.class)
			})
	public static class Form extends AbstractDataTreeForm<DataTree,DataTreeType> {
		private static final long serialVersionUID = -4741435164709063863L;
		
		
	}
	
}
