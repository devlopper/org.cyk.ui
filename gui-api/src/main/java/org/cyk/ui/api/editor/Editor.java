package org.cyk.ui.api.editor;

import java.util.Collection;

import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.UIWindowPart;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.utility.common.AbstractMethod;

/**
 * An editor is a window part that allows a user to edit an object. 
 * An editor is always associated with an input object (POJO). 
 * Changes made in an editor are not committed until the user saves them. 
 * @author Komenan Y .Christian
 *
 * @param <FORM>
 * @param <OUTPUTLABEL>
 * @param <INPUT>
 * @param <SELECTITEM>
 */
public interface Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends UIWindowPart {
	
	UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> getWindow();
	
	void setWindow(UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM,?> aWindow);
	
	UIMenu getMenu();
	
	void setMenu(UIMenu aMenu);
	
	Object getObjectModel();
	
	EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> build(Object object);
	
	UICommand getSubmitCommand();
	
	UICommand getSwitchCommand();
	
	UICommand getBackCommand();
	
	UICommand getResetValuesCommand();
	
	UICommand getCloseCommand();
	
	void onSubmit(Object object) throws Exception;
	
	void onBack();
	
	void onSwitch(Object object);
	
	void onResetValues();
	
	void onClose();
	
	void setSubmitMethodMain(AbstractMethod<Object, Object> anAbstractMethod);
	
	EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getSelected();
	
	<T> Collection<T> load(Class<T> aClass);
	
	SELECTITEM item(Object object);
	
	void addItem(UIInputSelectOne<?,SELECTITEM> anInput,Object object);
	
	void updateValues() throws Exception;
	
	EditorInputs<FORM, OUTPUTLABEL, INPUT, SELECTITEM> createFormData();
	
	Boolean getRoot();
	
	void targetDependentInitialisation();
	
}
