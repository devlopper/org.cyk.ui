package org.cyk.ui.web.primefaces.page.value;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;
import org.cyk.ui.web.primefaces.page.AbstractCollectionListPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ValueCollectionListPage extends AbstractCollectionListPage.Extends<ValueCollection,ValueCollectionItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
}
