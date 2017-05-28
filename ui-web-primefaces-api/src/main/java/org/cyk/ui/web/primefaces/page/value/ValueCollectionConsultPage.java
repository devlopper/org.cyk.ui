package org.cyk.ui.web.primefaces.page.value;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.value.ValueCollectionItemDetails;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ValueCollectionConsultPage extends AbstractCollectionConsultPage.Extends<ValueCollection,ValueCollectionItem,ValueCollectionItemDetails> implements Serializable {
	
	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		//itemTable.setTitle(inject(LanguageBusiness.class).findClassLabelText(ValueCollectionItem.class));
		itemTable.getAddRowCommandable().addParameter(identifiable);
	}
	
	@Override
	protected Boolean getEnableItemTableInDefaultTab() {
		return Boolean.TRUE;
	}
	
	@Override
	protected String getItemTableTabId() {
		return IdentifierProvider.Adapter.getTabOf(ValueCollection.class);
	}
	
}
