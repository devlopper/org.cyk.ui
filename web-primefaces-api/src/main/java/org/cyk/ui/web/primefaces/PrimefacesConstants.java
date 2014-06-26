package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.web.api.editor.input.InputSelectOne;
import org.cyk.ui.web.api.editor.input.InputText;

@Singleton @Named @Getter
public class PrimefacesConstants implements Serializable {

	private static final long serialVersionUID = -4564649341986027994L;

	public String includeFile(UIInputComponent<?> inputComponent){
		if(inputComponent instanceof InputText)
			return "include/inputTextOneLine.xhtml";
		else if(inputComponent instanceof InputSelectOne)
			return "include/inputOneMenu.xhtml";
		System.out.println("Null");
		return null;
	}
	
}
