package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.AbstractApplicableValueQuestion;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.AbstractWebItemCollection;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.ui.web.api.WebManager;

public class ItemCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends AbstractWebItemCollection<TYPE,IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = -6459718386925539576L;

	public ItemCollection(String identifier,Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass) {
		super(identifier,itemClass,identifiableClass);
		//((Commandable)addCommandable).getButton().setProcess("@this");
		//((Commandable)deleteCommandable).getButton().setProcess("@this");
		
		//((Commandable)deleteCommandable).getButton().setImmediate(Boolean.TRUE);
	}
	
	@Override
	protected void updateTable() {
		WebManager.getInstance().updateInForm(new String[]{identifier,"datatable"});
	}
	
	@Override
	protected AbstractApplicableValueQuestion<SelectItem> createApplicableValueQuestion() {
		return new ApplicableValueQuestion();
	}
	
	/**/
	
	public static class Adapter<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends ItemCollectionWebAdapter<TYPE, IDENTIFIABLE> {

		private static final long serialVersionUID = -91522966404798240L;
		
	}

}
