package org.cyk.ui.api.model;

import java.io.Serializable;

import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.table.AbstractTable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DetailsBlock<MENU_MODEL> implements Serializable {

	private static final long serialVersionUID = -1962971794962204899L;

	private String title;
	private FormOneData<?, ?, ?, ?, ?, ?> formOneData;
	private AbstractTable<?, ?, ?> table;
	private Boolean rendered = Boolean.TRUE;
	
	@Getter @Setter private UIMenu menu;
	@Getter private MENU_MODEL menuModel;
	
	public DetailsBlock(String title, FormOneData<?, ?, ?, ?, ?, ?> formOneData) {
		super();
		this.title = title;
		this.formOneData = formOneData;
	}

	public DetailsBlock(String title, AbstractTable<?, ?, ?> table) {
		super();
		this.title = title;
		this.table = table;
	}
	
	
	
	
}
