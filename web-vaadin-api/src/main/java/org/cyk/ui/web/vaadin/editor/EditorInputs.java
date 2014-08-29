package org.cyk.ui.web.vaadin.editor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.editor.AbstractEditorInputs;
import org.cyk.ui.api.editor.input.ISelectItem;
import org.cyk.ui.api.editor.input.UIInputDate;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.ui.api.editor.output.UIOutputLabel;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EditorInputs extends AbstractEditorInputs<FormLayout,Label,AbstractField<?>,Object> implements Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	@Getter private Panel panel;
	
	@Override
	public UIOutputComponent<?> output(UIOutputComponent<?> anIOutput) {
		//WebUIOutputComponent<?> component = null;
		//if(anIOutput instanceof UIOutputText)
			//component = outputText((UIOutputText) anIOutput);
		return null;//component;
	}

	@Override
	public void createRow() {}

	@Override
	public FormLayout createDataModel() {
		FormLayout layout = new FormLayout();
		layout.setMargin(true);
		layout.setSizeUndefined();
		return layout;
	}

	@Override
	public Object createComponent(UIInputOutputComponent<?> aComponent) {
		Object field = null;
		if(aComponent instanceof Input){
			if( ((Input)aComponent).get__input__() instanceof UIInputText){
				field =  ((Input)aComponent).get__input__().getAnnotation().textArea()?new TextArea():new TextField();
			}else if( ((Input)aComponent).get__input__() instanceof UIInputSelectOne<?, ?>){
				field = new ComboBox();
				@SuppressWarnings("unchecked")
				UIInputSelectOne<Object, ?> inputSelectOne = (UIInputSelectOne<Object, ?>) ((Input)aComponent).get__input__();
				ComboBox comboBox = (ComboBox) field;
				for(Object selectItem : inputSelectOne.getItems()){
					ISelectItem iSelectItem = (ISelectItem) selectItem;
					comboBox.addItem(iSelectItem.getLabel());
				}
				;//getItems().add(new SelectItem(selectItem.getValue(), selectItem.getLabel()));
				
				if(inputSelectOne.isSelectItemForeign() && (inputSelectOne.getItems()==null || inputSelectOne.getItems().isEmpty())){
					@SuppressWarnings("unchecked")
					Collection<Object> datas = (Collection<Object>) UIManager.getInstance().getCollectionLoadMethod().execute((Class<Object>) inputSelectOne.getFieldType());
					
					//if(inputSelectOne.getValue()!=null){
						if(datas==null)
							if(inputSelectOne.getValue()==null)
								;
							else
								datas = Arrays.asList(inputSelectOne.getValue());
						else if(inputSelectOne.getValue()!=null && !datas.contains(inputSelectOne.getValue()))
							datas.add(inputSelectOne.getValue());
					//}
					
					if(datas!=null)
						for(Object object : datas)
							comboBox.addItem(UIManager.getInstance().getToStringMethod().execute(object));
				}
				
			}else if( ((Input)aComponent).get__input__() instanceof UIInputDate){
				field =  new DateField();
			}
		}else if(aComponent instanceof UIOutputLabel){
			field = new Label(((UIOutputLabel)aComponent).getValue());
		}
		return field;
	}

	@Override
	public void link(Label anOutputLabel, AbstractField<?> anInput) {
		anInput.setCaption(anOutputLabel.getValue());
		dataModel.addComponent(anInput);
	}

	@Override
	public void targetDependentInitialisation() {
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(dataModel);
	
		layout.addComponent(((Editor)getEditor()).getSubmitButton());
		
		panel = new Panel(layout);
		panel.setSizeUndefined();
		
	}
	
}
