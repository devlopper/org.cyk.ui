package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.model.table.Table;
import org.primefaces.model.menu.MenuModel;

public class PrimefacesTable<DATA> extends Table<DATA> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	private CommandBuilder commandBuilder = new CommandBuilder();
	
	@Getter private MenuModel menuModel;
	
	public PrimefacesTable(Class<DATA> aDataClass,UIWindow<?, ?, ?, ?> aWindow){
		super(aDataClass,aWindow);
	}
	
	public void bindToField(String fieldName){
		menuModel = commandBuilder.buildMenuModel(menu, window.getClass(), fieldName);
	}
		
}
