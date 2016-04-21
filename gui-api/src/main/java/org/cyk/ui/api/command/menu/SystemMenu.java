package org.cyk.ui.api.command.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.Icon;
import org.cyk.ui.api.command.UICommandable;

@Getter @Setter
public class SystemMenu implements Serializable {

	private static final long serialVersionUID = 1266706219852574657L;
	
	private String name,nameId;
	private Icon icon;
	
	private Collection<UICommandable> referenceEntities = new ArrayList<>();
	
	private Collection<UICommandable> businesses = new ArrayList<>();
	private Collection<UICommandable> mobileBusinesses = new ArrayList<>();
	
	private Collection<UICommandable> reports = new ArrayList<>();

}
