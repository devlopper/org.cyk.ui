package org.cyk.ui.web.api.form;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.component.output.UIOutputText;
import org.cyk.ui.api.editor.AbstractEditorInputs;
import org.cyk.ui.api.editor.input.ISelectItem;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputDate;
import org.cyk.ui.api.editor.input.UIInputNumber;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.ui.web.api.OutputText;
import org.cyk.ui.web.api.form.input.InputDate;
import org.cyk.ui.web.api.form.input.InputNumber;
import org.cyk.ui.web.api.form.input.InputSelectOne;
import org.cyk.ui.web.api.form.input.InputText;
import org.cyk.ui.web.api.form.input.WebUIInputComponent;
import org.cyk.ui.web.api.form.input.WebUIInputDate;
import org.cyk.ui.web.api.form.input.WebUIInputNumber;
import org.cyk.ui.web.api.form.input.WebUIInputSelectOne;
import org.cyk.ui.web.api.form.input.WebUIInputText;
import org.cyk.ui.web.api.form.input.WebUIOutputComponent;
import org.cyk.ui.web.api.form.input.WebUIOutputText;

public abstract class AbstractWebEditorInputs<FORM,OUTPUTLABEL,INPUT> extends AbstractEditorInputs<FORM,OUTPUTLABEL,INPUT,SelectItem> implements WebEditorInputs<FORM,OUTPUTLABEL,INPUT,SelectItem> , Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	@Getter @Setter private String inputTemplateFile=getInputTemplateFileAtRight();
	
	@SuppressWarnings("unchecked")
	public UIInputComponent<?> input(UIInputComponent<?> aComponent){
		WebUIInputComponent<?> component = null;
		if(aComponent instanceof UIInputText)
			component = inputText((UIInputText) aComponent);
		else if(aComponent instanceof UIInputDate)
			component = inputDate((UIInputDate) aComponent);
		else if(aComponent instanceof UIInputNumber)
			component = inputNumber((UIInputNumber) aComponent);
		else if(aComponent instanceof UIInputSelectOne<?,?>)
			component = inputSelectOne((UIInputSelectOne<Object,ISelectItem>) aComponent);
		
		return component;
	}
	
	@Override
	public UIOutputComponent<?> output(UIOutputComponent<?> anIOutput) {
		WebUIOutputComponent<?> component = null;
		if(anIOutput instanceof UIOutputText)
			component = outputText((UIOutputText) anIOutput);
		return component;
	}
	
	@Override
	public WebUIInputDate inputDate(UIInputDate anIInput) {
		return new InputDate(this,anIInput);
	}
	
	@Override
	public WebUIInputNumber inputNumber(UIInputNumber anIInput) {
		return new InputNumber(this,anIInput);
	}
	
	@Override
	public WebUIInputSelectOne<Object,FORM> inputSelectOne(UIInputSelectOne<Object,ISelectItem> anIInput) {
		if(getEditor()==null)
			throw new IllegalArgumentException("No editor has been set");
		WebUIInputSelectOne<Object,FORM> inputSelectOne = new InputSelectOne<FORM>(this,anIInput);
		if(inputSelectOne.getItems()==null || inputSelectOne.getItems().isEmpty()){
			@SuppressWarnings("unchecked")
			Collection<Object> datas = (Collection<Object>) getEditor().load(anIInput.getField().getType());
			if(anIInput.getValue()!=null){
				if(datas==null)
					datas = Arrays.asList(anIInput.getValue());
				else
					datas.add(anIInput.getValue());
			}
			/*	
			if(datas!=null){
				Collection<SelectItem> items = new LinkedHashSet<>();
				for(Object object : datas)
					items.add(((IFormContainer<?,?,?,?>)container).item(object));
				inputSelectOne.setItems(items);
			}*/
		}
		
		return inputSelectOne;
	}
	
	@Override
	public WebUIInputText inputText(UIInputText anIInput) {
		return new InputText(this,anIInput);
	}
	
	@Override
	public WebUIOutputText outputText(UIOutputText anIOutputText) {
		return new OutputText((UIOutputText) anIOutputText);
	}
	
}
