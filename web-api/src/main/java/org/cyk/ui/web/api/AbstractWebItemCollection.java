package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractWebItemCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends AbstractItemCollection<TYPE,IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3478876936484027644L;
	
	protected String identifier;
	protected List<SelectItem> applicableItems = new ArrayList<>();
	
	public AbstractWebItemCollection(String identifier,Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass) {
		super(itemClass,identifiableClass);
		this.identifier = identifier;
		applicableItems.add(new SelectItem(Boolean.TRUE, UIManager.getInstance().getLanguageBusiness().findText(LanguageEntry.YES)));
		applicableItems.add(new SelectItem(Boolean.FALSE, UIManager.getInstance().getLanguageBusiness().findText(LanguageEntry.NO)));
	}

	

}
