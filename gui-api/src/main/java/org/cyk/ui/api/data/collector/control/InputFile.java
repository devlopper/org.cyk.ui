package org.cyk.ui.api.data.collector.control;

import java.util.Set;

import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.FileExtension;


public interface InputFile<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<File,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	Boolean getPreviewable();
	void setPreviewable(Boolean aValue);
	
	Boolean getClearable();
	void setClearable(Boolean aValue);
	
	Set<FileExtension> getExtensions();
	void setExtensions(Set<FileExtension> set);
	
	Long getMinimumSize();
	void setMinimumSize(Long size);
	
	Long getMaximumSize();
	void setMaximumSize(Long size);
	
}
