/*package org.cyk.ui.api.editor;

import java.util.Collection;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.UIPart;
import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.layout.UILayout;

*//**
 * A form data is a special kind of view.
 * It use input field to present data
 * @author Komenan Y .Christian
 *
 * @param <FORM>
 * @param <OUTPUTLABEL>
 * @param <INPUT>
 * @param <SELECTITEM>
 *//*
public interface EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends UIPart  {
	
	UILayout getLayout();

	void setLayout(UILayout aLayout);
	
	Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getEditor();
	
	void setEditor(Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> anEditor);
	
	void createRow();
	
	void add(UIInputOutputComponent<?> component);
	
	Collection<UIInputOutputComponent<?>> getComponents();
	
	void setObjectModel(Object anObjectModel);
	
	Object getObjectModel();
	
	void group(Class<?>...theGroupsClasses);
	
	void build(Crud crud);
	
	UIOutputComponent<?> output(UIOutputComponent<?> anIOutput);
	
	FORM createDataModel();
	
	FORM getDataModel();
	
	Object createComponent(UIInputOutputComponent<?> aComponent);
	
	Collection<UIInputComponent<?>> getInputFields();
	
	void link(OUTPUTLABEL anOutputLabel,INPUT anInput);
	
	void updateFieldsValue() throws Exception;
	
	UIInputComponent<?> getParentField();
	
	void setParentField(UIInputComponent<?> aParentField);
	
	Collection<EditorInputsListener<FORM,OUTPUTLABEL,INPUT,SELECTITEM>> getListeners();
	
}
*/