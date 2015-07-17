package org.cyk.ui.api.command.menu;

import java.io.Serializable;

import org.cyk.ui.api.command.UICommandable.IconType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TopLevelMenuItemConfiguration implements Serializable {

	private static final long serialVersionUID = -2457222701023835960L;

	private String labelId;
	private IconType iconType;
	private Boolean show = Boolean.TRUE;
	
}
