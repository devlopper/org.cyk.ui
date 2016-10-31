package org.cyk.ui.web.primefaces.test.form;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntityNoAnnotation {

	private String inputText;
	
	private String inputTextarea;
	
	private String inputPassword;
	
	private String inputEditor;
	
	private Date inputCalendar;
	
	private Boolean inputBooleanButton;
	
	private String inputFile;
	
	private Boolean inputBooleanCheck;
	
	private Integer inputNumber;
	
	private String inputOneList;
	
	private String inputOneCombo;
	
	private String inputOneButton;
	
	private String inputOneCascadeList;
	
	private String inputOneRadio;
	
	private List<String> inputManyCombo;
	
	private List<String> inputManyPickList;
	
	private List<String> inputManyButton;
	
	private List<String> inputManyCheck;
	
	private List<String> inputManyCheckCombo;
}
