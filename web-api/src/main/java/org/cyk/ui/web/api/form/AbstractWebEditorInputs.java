package org.cyk.ui.web.api.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.component.output.UIOutputText;
import org.cyk.ui.api.editor.AbstractEditorInputs;
import org.cyk.ui.web.api.OutputText;
import org.cyk.ui.web.api.form.input.WebUIOutputComponent;
import org.cyk.ui.web.api.form.input.WebUIOutputText;

public abstract class AbstractWebEditorInputs<FORM,OUTPUTLABEL,INPUT> extends AbstractEditorInputs<FORM,OUTPUTLABEL,INPUT,SelectItem> implements WebEditorInputs<FORM,OUTPUTLABEL,INPUT,SelectItem> , Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	@Getter @Setter private String inputTemplateFile=getInputTemplateFileAtRight();
	
	@Override
	public UIOutputComponent<?> output(UIOutputComponent<?> anIOutput) {
		WebUIOutputComponent<?> component = null;
		if(anIOutput instanceof UIOutputText)
			component = outputText((UIOutputText) anIOutput);
		return component;
	}
	
	@Override
	public WebUIOutputText outputText(UIOutputText anIOutputText) {
		return new OutputText((UIOutputText) anIOutputText);
	}
	
	/*
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
		}
		
		return inputSelectOne;
	}
	
	@Override
	public WebUIInputText inputText(UIInputText anIInput) {
		return new InputText(this,anIInput);
	}
	
	
	*/
	
}
