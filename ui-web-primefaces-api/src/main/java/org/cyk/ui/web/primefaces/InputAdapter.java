package org.cyk.ui.web.primefaces;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputEditor;
import org.cyk.utility.common.userinterface.input.InputFile;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.InputTextarea;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneCombo;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneRadio;

public class InputAdapter extends org.cyk.ui.web.primefaces.resources.InputAdapter {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Boolean isInputable(Class<?> aClass, String fieldName) {
		if(ClassHelper.getInstance().isInstanceOf(Contact.class, aClass) && ArrayUtils.contains(new String[]{
				FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE)
				,FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME)
		}, fieldName))
			return Boolean.FALSE;
		return super.isInputable(aClass, fieldName);
	}
	
	@Override
	public Collection<String> getFieldNames(Detail form, Object object) {
		if(object instanceof Person)
			return Arrays.asList("inputTextarea","inputText","inputPassword");
		if(object instanceof GlobalIdentifier)
			return Arrays.asList("code","name","description","otherDetails");
		if(object instanceof Person)
			return Arrays.asList("lastnames","sex","nationality");
		return super.getFieldNames(form, object);
	}
	
	@Override
	public Class<? extends Input<?>> getClass(Detail detail, Object object, Field field) {
		if(object instanceof GlobalIdentifier){
			if(field.getName().equals(GlobalIdentifier.FIELD_CODE))
				return InputText.class;
			if(field.getName().equals(GlobalIdentifier.FIELD_IMAGE))
				return InputFile.class;
			if(field.getName().equals(GlobalIdentifier.FIELD_NAME))
				return InputText.class;
			if(field.getName().equals(GlobalIdentifier.FIELD_DESCRIPTION))
				return InputTextarea.class;
			if(field.getName().equals(GlobalIdentifier.FIELD_OTHER_DETAILS))
				return InputEditor.class;
		}else if(object instanceof Person){
			if(field.getName().equals(Person.FIELD_LASTNAMES))
				return InputText.class;
			if(field.getName().equals(Person.FIELD_SEX))
				return InputChoiceOneRadio.class;
			if(field.getName().equals(Person.FIELD_NATIONALITY))
				return InputChoiceOneCombo.class;
		}
		return super.getClass(detail, object, field);
	}
	
	public Class<?> getFileClass(){
		return File.class;
	}		
	
	@Override
	public Object getReadableValue(Input<?> input) {
		Object value = super.getReadableValue(input);
		if(value instanceof File){
			File file = (File) value;
			value = new FileHelper.File();
			((FileHelper.File)value).setBytes(file.getBytes());
			((FileHelper.File)value).setMime(file.getMime());
			((FileHelper.File)value).setName(file.getName());
			((FileHelper.File)value).setExtension(file.getExtension());
		}	
		return value;
	}

}