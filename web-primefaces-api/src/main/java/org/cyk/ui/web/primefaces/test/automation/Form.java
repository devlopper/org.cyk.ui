package org.cyk.ui.web.primefaces.test.automation;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.generator.RandomDataProvider;
import org.cyk.utility.common.generator.RandomDataProvider.RandomFile;

@Getter @Setter
public class Form extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final List<AbstractInput<?>> inputs = new ArrayList<>();
	private Commandable submitCommandable/*,messageDialogOkCommandable*/;
	
	public Form(String submitCommandableLabelIdentifier) {
		submitCommandableLabelIdentifier = "command."+submitCommandableLabelIdentifier;
		submitCommandable = new Commandable(submitCommandableLabelIdentifier);
		submitCommandable.setNotified(Boolean.TRUE);
		/*
		messageDialogOkCommandable = new Commandable(CascadeStyleSheet.MESSAGE_DIALOG_OK_COMMANDABLE_CLASS){
			private static final long serialVersionUID = 1L;
			@Override
			protected String buildClassName(String labelIdentifier) {
				return labelIdentifier;
			}
			@Override
			public Boolean getIsStatic() {
				return Boolean.FALSE;
			}
		};
		*/
	}
	public Form() {
		this("execute");
	}
	
	/**/
	
	public void sendKeys(){
		SeleniumHelper.getInstance().sendKeys(inputs);
	}
	
	/* Inputs */
	
	public Form addInput(AbstractInput<?> input){
		inputs.add(input);
		return this;
	}
	
	public Form addInputText(String fieldName,String value){
		addInput(new InputText(fieldName, value));
		return this;
	}
	public Form addInputCalendar(String fieldName,String value){
		addInput(new InputCalendar(fieldName, value));
		return this;
	}
	public Form addInputFile(String fieldName,String value){
		addInput(new InputFile(fieldName, value));
		return this;
	}
	public Form addInputPersonImage(String fieldName,Boolean male){
		RandomFile randomFile = (Boolean.TRUE.equals(male) ? RandomDataProvider.getInstance().getMale() : RandomDataProvider.getInstance().getFemale()).photo();
        File file = randomFile.createTemporaryFile();
        return addInputFile(fieldName, file.getPath());
	}
	public Form addInputOneRadio(String fieldName,Integer choiceIndex){
		addInput(new InputOneRadio(fieldName, choiceIndex));
		return this;
	}
	public Form addInputOneAutoComplete(String fieldName,String filter,Integer choiceIndex){
		addInput(new InputOneAutoComplete(fieldName,filter, choiceIndex));
		return this;
	}
	
	/* Commands */
    
	public Commandable getMessageDialogOkCommandable(){
		return new Commandable(CascadeStyleSheet.NOTIFICATION_DIALOG_OK_COMMANDABLE_CLASS){
			private static final long serialVersionUID = 1L;
			@Override
			protected String buildClassName(String labelIdentifier) {
				return labelIdentifier;
			}
		};
	}
	
	public void submit(){
    	submitCommandable.click();
    }
	
}
