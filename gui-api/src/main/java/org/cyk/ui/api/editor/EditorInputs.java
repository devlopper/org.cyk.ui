package org.cyk.ui.api.editor;

import java.util.Collection;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.layout.UILayout;

/**
 * A form data is a special kind of view.
 * It use input field to present data
 * @author Komenan Y .Christian
 *
 * @param <FORM>
 * @param <OUTPUTLABEL>
 * @param <INPUT>
 * @param <SELECTITEM>
 */
public interface EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM>  {
	
	UILayout getLayout();

	void setLayout(UILayout aLayout);
	
	Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getEditor();
	
	void setEditor(Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> anEditor);
	
	String getTitle();

	Integer getColumnsCount();
	
	Integer getRowsCount();
	
	void createRow();
	
	void add(UIComponent<?> component);
	
	Collection<UIComponent<?>> getComponents();
	
	void setObjectModel(Object anObjectModel);
	
	Object getObjectModel();
	
	void group(Class<?>...theGroupsClasses);
	
	void build();
	
	UIInputComponent<?> input(UIInputComponent<?> anIInput);
	
	UIOutputComponent<?> output(UIOutputComponent<?> anIOutput);
	
	FORM createDataModel();
	
	FORM getDataModel();
	
	Object createComponent(UIComponent<?> aComponent);
	
	Collection<UIInputComponent<?>> getInputFields();
	
	void link(OUTPUTLABEL anOutputLabel,INPUT anInput);
	
	void updateFieldsValue() throws Exception;
	
	UIInputComponent<?> getParentField();
	
	void setParentField(UIInputComponent<?> aParentField);
	
}
