package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.ui.web.api.AbstractWebItemCollection;
import org.cyk.ui.web.api.WebManager;

public class ItemCollection<TYPE> extends AbstractWebItemCollection<TYPE> implements Serializable {

	private static final long serialVersionUID = -6459718386925539576L;

	public ItemCollection(String identifier,Class<TYPE> itemClass) {
		super(identifier,itemClass);
		//((Commandable)addCommandable).getButton().setProcess("@this");
		//((Commandable)deleteCommandable).getButton().setProcess("@this");
	}
	
	@Override
	protected void updateTable() {
		WebManager.getInstance().updateInForm(new String[]{identifier,"datatable"});
	}

}
