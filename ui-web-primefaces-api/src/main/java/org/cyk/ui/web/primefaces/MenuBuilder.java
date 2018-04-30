package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.generator.StringGenerator;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.information.IdentifiableCollection;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.pattern.tree.DataTree;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.UserInterface;
import org.cyk.system.root.model.value.Value;

public class MenuBuilder extends org.cyk.ui.web.primefaces.resources.MenuBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void populateNodeIdentifiablesManagePackageFromClass(Collection<Class<?>> packageFromClass) {
		super.populateNodeIdentifiablesManagePackageFromClass(packageFromClass);
		packageFromClass.addAll(Arrays.asList(ContactCollection.class,Event.class,File.class,Party.class,Movement.class
				,Value.class
				,UserInterface.class,Period.class,Role.class,DataTree.class,Computer.class,SmtpProperties.class,Language.class
				,IdentifiableCollection.class,StringGenerator.class,Script.class));
	}
	
}