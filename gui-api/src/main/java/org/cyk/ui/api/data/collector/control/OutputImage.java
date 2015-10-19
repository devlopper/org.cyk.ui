package org.cyk.ui.api.data.collector.control;

import org.cyk.system.root.model.file.File;


public interface OutputImage<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> extends Output<MODEL,ROW,LABEL,CONTROL,CHOICE_ITEM> {

	File getValue();
	void setValue(File file);
	
	Integer getPreviewWidth();
	void setPreviewWidth(Integer file);
	
	Integer getPreviewHeight();
	void setPreviewHeight(Integer file);
	
}
