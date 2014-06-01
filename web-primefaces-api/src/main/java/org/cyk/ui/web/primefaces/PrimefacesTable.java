package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;

import org.cyk.ui.api.model.table.Table;
import org.primefaces.model.menu.MenuModel;

public class PrimefacesTable<DATA> extends Table<DATA> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuModel menuModel;
	
	@Override
	public void targetDependentInitialisation() {
		Field field = commonUtils.getField(getWindow(), this);
		menuModel = CommandBuilder.getInstance().menuModel(menu, window.getClass(), field.getName());
	}
	
	public String includeFileFor(Object object){
		return "include/inputTextOneLine.xhtml";
		/*
		if(object instanceof UIInputComponent<?>){
			UIInputComponent<?> inputComponent = (UIInputComponent<?>) object;
			System.out.println("PrimefacesHelper.includeFileFor()");
			return "include/inputTextOneLine.xhtml";
		}
		System.out.println("PrimefacesTable.includeFileFor()");	
		*/
		//return null;
	}
		
}
