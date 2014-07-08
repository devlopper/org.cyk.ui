package org.cyk.ui.desktop.api;

import java.io.Serializable;

import org.cyk.ui.api.UIMessageManager.SeverityType;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter @AllArgsConstructor
public class DesktopUIMessage implements Serializable {

	private static final long serialVersionUID = 1554516722729542971L;

	private SeverityType severity;
	private String summary,details;
	
}
