package org.cyk.ui.api.command.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.IconType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SystemMenu implements Serializable {

	private static final long serialVersionUID = 1266706219852574657L;
	
	private String name,nameId;
	private IconType iconType;
	
	private Collection<UICommandable> referenceEntities = new ArrayList<>();
	
	private Collection<UICommandable> businesses = new ArrayList<>();
	private Collection<UICommandable> mobileBusinesses = new ArrayList<>();
	
	private Collection<UICommandable> reports = new ArrayList<>();

}
