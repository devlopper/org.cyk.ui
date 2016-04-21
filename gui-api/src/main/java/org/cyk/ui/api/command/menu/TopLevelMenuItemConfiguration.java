package org.cyk.ui.api.command.menu;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.Icon;

@Getter @Setter
public class TopLevelMenuItemConfiguration implements Serializable {

	private static final long serialVersionUID = -2457222701023835960L;

	private String labelId;
	private Icon icon;
	private Boolean show = Boolean.TRUE;
	
}
