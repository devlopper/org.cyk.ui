package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.ClassLocator;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class FormClassLocator extends ClassLocator implements Serializable {

	private static final long serialVersionUID = -3187769614985951029L;

	private static FormClassLocator INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		setClassType("Form");
		Listener listener = new Listener.Adapter(){
			private static final long serialVersionUID = -979036256355287919L;

			@Override
			public Boolean isLocatable(Class<?> basedClass) {
				return AbstractIdentifiable.class.isAssignableFrom(basedClass);
			}
		};
		
		listener.setGetNameMethod(new Listener.AbstractGetOrgCykSystem() {
			private static final long serialVersionUID = -7213562588417233353L;
			@Override
			protected String getBaseClassPackageName() {
				return "model";
			}
			@Override
			protected String[] getModulePrefixes() {
				return new String[]{"ui.web.primefaces.page"};
			}
			@Override
			protected String[] getModuleSuffixes() {
				return new String[]{"EditPage$Form"};
			}
			
			@Override
			protected String[] __execute__(Class<?> aClass) {
				String[] names =  super.__execute__(aClass);
				names[0] = StringUtils.remove(names[0], "system.root.");
				return names;
			}
		});
		getClassLocatorListeners().add(listener);
	}
	
	public static FormClassLocator getInstance() {
		return INSTANCE;
	}
}
