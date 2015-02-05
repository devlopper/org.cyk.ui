package org.cyk.ui.api.data.collector.control;

import java.util.Set;

import org.cyk.system.root.model.file.File;


public interface InputFile<MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> extends Input<File,MODEL, ROW, LABEL, CONTROL, CHOICE_ITEM> {

	Boolean getPreviewable();
	void setPreviewable(Boolean aValue);
	
	Boolean getClearable();
	void setClearable(Boolean aValue);
	
	Set<String> getExtensions();
	void setExtensions(Set<String> set);
	
	Long getMinimumSize();
	void setMinimumSize(Long size);
	
	Long getMaximumSize();
	void setMaximumSize(Long size);
	
}
