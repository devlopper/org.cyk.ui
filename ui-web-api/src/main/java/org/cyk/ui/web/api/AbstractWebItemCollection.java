package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractWebItemCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractItemCollection<TYPE,IDENTIFIABLE,COLLECTION,SelectItem> implements Serializable {

	private static final long serialVersionUID = 3478876936484027644L;
	
	protected String identifier,updateStyleClass=RandomStringUtils.randomAlphabetic(10);
	
	public AbstractWebItemCollection(String identifier,Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass,COLLECTION collection,Crud crud) {
		super(itemClass,identifiableClass,collection,crud);
		this.identifier = identifier;
		applicableValueQuestion.getAnswers().add(new SelectItem(Boolean.TRUE, UIManager.getInstance().getLanguageBusiness().findText(LanguageEntry.YES)));
		applicableValueQuestion.getAnswers().add(new SelectItem(Boolean.FALSE, UIManager.getInstance().getLanguageBusiness().findText(LanguageEntry.NO)));
	}
	
	@Override
	protected SelectItem createSelectItem(AbstractIdentifiable identifiable) {
		return WebManager.getInstance().getSelectItem(identifiable);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IDENTIFIABLE getIdentifiableFromChoice(SelectItem choice) {
		return (IDENTIFIABLE) choice.getValue();
	}

}
