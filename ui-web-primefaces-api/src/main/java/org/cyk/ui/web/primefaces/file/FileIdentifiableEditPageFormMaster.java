package org.cyk.ui.web.primefaces.file;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.userinterface.container.form.FormDetail;

public class FileIdentifiableEditPageFormMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void processColumnsFieldNamesFile(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames){
		CollectionHelper.getInstance().add(fieldNames, File.FIELD_REPRESENTATION_TYPE, File.FIELD_EXTENSION, File.FIELD_MIME);
	}
	
	public static void prepareFile(final FormDetail detail,Class<?> aClass){
		detail.add(File.FIELD_REPRESENTATION_TYPE).addBreak();
		detail.add(File.FIELD_UNIFORM_RESOURCE_IDENTIFIER).addBreak();
		
		detail.setFieldsObjectFromMaster(File.FIELD_GLOBAL_IDENTIFIER);
		detail.add(GlobalIdentifier.FIELD_TEXT).addBreak();
		
		detail.setFieldsObjectFromMaster();
		detail.add(File.FIELD_EXTENSION).addBreak();
		detail.add(File.FIELD_MIME).addBreak();
		detail.add(File.FIELD_GENERATOR).addBreak();
		detail.add(File.FIELD_CONTENT_WRITER).addBreak();
		detail.add(File.FIELD_SENDER).addBreak();
	}
	
	public static void processColumnsFieldNamesScript(org.cyk.utility.common.userinterface.collection.DataTable dataTable,Collection<String> fieldNames){
		CollectionHelper.getInstance().add(fieldNames, Script.FIELD_EVALUATION_ENGINE, FieldHelper.getInstance().buildPath(Script.FIELD_GLOBAL_IDENTIFIER
				,GlobalIdentifier.FIELD_TEXT));
	}
	
	public static void prepareScript(final FormDetail detail,Class<?> aClass){
		detail.add(Script.FIELD_EVALUATION_ENGINE).addBreak();
		detail.setFieldsObjectFromMaster(Script.FIELD_GLOBAL_IDENTIFIER);
		detail.add(GlobalIdentifier.FIELD_TEXT).addBreak();
	}
	
	/**/
	
}