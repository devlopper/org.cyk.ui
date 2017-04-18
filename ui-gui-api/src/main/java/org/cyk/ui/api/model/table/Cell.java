package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.utility.common.model.table.DefaultCell;

@Getter @Setter
public class Cell extends DefaultCell implements Serializable {

	private static final long serialVersionUID = -2816856709391842461L;
	
	private Control<?, ?, ?, ?, ?> control;
	
	private Boolean isFile,isImage,showFileLink;
	private String url,tooltip;
	private CascadeStyleSheet cascadeStyleSheet = new CascadeStyleSheet();
}
