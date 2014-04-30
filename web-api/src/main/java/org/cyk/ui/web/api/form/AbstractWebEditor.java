package org.cyk.ui.web.api.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.editor.AbstractEditor;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.web.api.ObjectConverter;
import org.cyk.ui.web.api.form.input.WebUIInputSelect;

public abstract class AbstractWebEditor<FORM,OUTPUTLABEL,INPUT> extends AbstractEditor<FORM,OUTPUTLABEL,INPUT,SelectItem> implements WebEditor<FORM,OUTPUTLABEL,INPUT> , Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	@Inject @Getter protected ObjectConverter objectConverter;
	@Getter @Setter private String inputTemplateFile="template/form-inputMessageAtRight.xhtml";
	
	@Override
	public SelectItem item(Object object) {
		return new SelectItem(object,object.toString());
	}
	
	@Override
	public void addItem(UIInputSelectOne<?,SelectItem> anInput, Object object) {
		((WebUIInputSelect<?, ?>)anInput).getItems().add(item(object));
	}
	
	
	
}
