package org.cyk.ui.web.vaadin.editor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.ui.api.editor.input.ISelectItem;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputDate;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.ui.api.editor.output.UIOutputLabel;
import org.cyk.utility.common.annotation.UIField;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

@Getter @Setter
public class Input extends AbstractInputComponent<Object> implements Serializable {

	private static final long serialVersionUID = -7887654678590867794L;
	
	private UIInputComponent<?> __input__;
	
	public Input(Field aField, Class<?> fieldType, UIField annotation, Object anObject) {
		super(aField, fieldType, annotation, anObject);
	}
	
	public Input(org.cyk.ui.web.vaadin.editor.EditorInputs editorInputs,UIInputComponent<?> input) {
		super(input.getField(),input.getFieldType(),input.getAnnotation(),input.getObject());
		__input__ = input;
		label = input.getLabel();
		description = input.getDescription();
		requiredMessage = input.getRequiredMessage();
		readOnly = input.getReadOnly();
		required = input.getRequired();
		field = input.getField();
		fieldType = input.getFieldType();
		object = input.getObject();
		value = input.getValue();
		annotation = input.getAnnotation();
		this.editorInputs = editorInputs;
		this.validationGroupClass = input.getValidationGroupClass();
		width = input.getWidth();
		height = input.getHeight();
	}
	
	public static Object createComponent(UIInputOutputComponent<?> aComponent) {
		if(aComponent instanceof Input){
			AbstractField<?> field = null;
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
			
			field.addValueChangeListener(new ValueChangeListener() {	
				private static final long serialVersionUID = -2335645988868178405L;
				@Override
				public void valueChange(ValueChangeEvent event) {
					
				}
			});
			return field;
		}else if(aComponent instanceof UIOutputLabel){
			return new Label(((UIOutputLabel)aComponent).getValue());
		}
		return null;
	}

}
