package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.primefaces.Commandable;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.UploadedFile;

@Getter @Setter
public class InputFile extends AbstractInput<File> implements  org.cyk.ui.web.api.data.collector.control.WebInputFile<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputFile<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = 1390099136018097004L;

	private UploadedFile uploadedFile;
	
	private UICommandable clearCommandable;
	
	private Boolean previewable = Boolean.FALSE,clearable = Boolean.FALSE; 
	private Long minimumSize,maximumSize;
	private Set<String> extensions = new HashSet<>();
	private String allowTypes,extensionsAsString,mode="simple";
	
	public InputFile() {
		clearCommandable = UIProvider.getInstance().createCommandable(null, "command.delete", IconType.ACTION_CLEAR, null, null);
		((Commandable)clearCommandable).getButton().setProcess("@this");
		clearCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = 1247729459254167284L;
			@Override
			public void serve(UICommand command, Object parameter) {
				value = null;
				((Commandable)clearCommandable).getButton().setRendered(Boolean.FALSE);
			}
		});
	}
	
	public String getAllowTypes(){
		if(allowTypes==null)
			allowTypes = "/(\\.|\\/)("+StringUtils.join(extensions,"|")+")$/";
		return allowTypes;
	}
	
	public String getExtensionsAsString(){
		if(extensionsAsString==null)
			extensionsAsString = StringUtils.join(extensions,",");
		return extensionsAsString;
	}
	
	@Override
	public void applyValueToField() throws IllegalAccessException {
		debug(uploadedFile);
	}
	
	
	
}
