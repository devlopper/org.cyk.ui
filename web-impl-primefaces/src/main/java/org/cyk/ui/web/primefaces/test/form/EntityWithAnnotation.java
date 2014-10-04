package org.cyk.ui.web.primefaces.test.form;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.ControlSet;
import org.cyk.utility.common.annotation.user.interfaces.ControlSetColumn;
import org.cyk.utility.common.annotation.user.interfaces.ControlSetPosition;
import org.cyk.utility.common.annotation.user.interfaces.ControlSetRow;
import org.cyk.utility.common.annotation.user.interfaces.ControlSets;
import org.cyk.utility.common.annotation.user.interfaces.ControlSetsPositions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputEditor;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputManyButton;
import org.cyk.utility.common.annotation.user.interfaces.InputManyCheck;
import org.cyk.utility.common.annotation.user.interfaces.InputManyCheckCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputManyCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputManyPickList;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneButton;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCascadeList;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputOneList;
import org.cyk.utility.common.annotation.user.interfaces.InputOneRadio;
import org.cyk.utility.common.annotation.user.interfaces.InputPassword;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Getter @Setter
@ControlSets(value={
		@ControlSet(identifier="p1",labelId="input.value")
		,@ControlSet(identifier="p2",labelId="input.choice.one")
		,@ControlSet(identifier="p3",labelId="input.choice.many")
})
@ControlSetsPositions(value={
		@ControlSetPosition(identifier="p3",column=@ControlSetColumn(index=0),row=@ControlSetRow(index=0))
		,@ControlSetPosition(identifier="p1",column=@ControlSetColumn(index=0),row=@ControlSetRow(index=0))
		,@ControlSetPosition(identifier="p2",column=@ControlSetColumn(index=0),row=@ControlSetRow(index=0))
})
public class EntityWithAnnotation {

	@Input(setId="p1")
	@InputText
	@NotNull
	private String inputText;
	
	@Input(setId="p1")
	@InputTextarea
	//@NotNull
	private String inputTextarea;
	
	@Input(setId="p1")
	@InputPassword
	//@NotNull
	private String inputPassword;
	
	@Input(setId="p1")
	@InputEditor
	//@NotNull
	private String inputEditor;
	
	@Input(setId="p1")
	@InputCalendar
	//@NotNull
	private Date inputCalendar;
	
	@Input(setId="p1")
	@InputBooleanButton
	private Boolean inputBooleanButton;
	
	@Input(setId="p1")
	@InputFile
	@NotNull
	private String inputFile;
	
	
	@Input(setId="p1")
	@InputBooleanCheck
	private Boolean inputBooleanCheck;
	
	@Input(setId="p1")
	@InputNumber
	//@NotNull
	private Integer inputNumber;
	
	@Input(setId="p2")
	@InputOneList
	//@NotNull
	private String inputOneList;
	
	@Input(setId="p2")
	@InputOneCombo
	//@NotNull
	private String inputOneCombo;
	
	@Input(setId="p2")
	@InputOneButton
	//@NotNull
	private String inputOneButton;
	
	@Input(setId="p2")
	@InputOneCascadeList
	//@NotNull
	private String inputOneCascadeList;
	
	@Input(setId="p2")
	@InputOneRadio
	//@NotNull
	private String inputOneRadio;
	
	@Input(setId="p3")
	@InputManyCombo
	//@NotNull
	@Size(min=1)
	private List<String> inputManyCombo;
	
	@Input(setId="p3")
	@InputManyPickList
	//@NotNull
	@Size(min=1)
	private List<String> inputManyPickList;
	
	@Input(setId="p3")
	@InputManyButton
	//@NotNull
	@Size(min=1)
	private List<String> inputManyButton;
	
	@Input(setId="p3")
	@InputManyCheck
	//@NotNull
	@Size(min=1)
	private List<String> inputManyCheck;
	
	@Input(setId="p3")
	@InputManyCheckCombo
	//@NotNull
	@Size(min=1)
	private List<String> inputManyCheckCombo;
	
}
