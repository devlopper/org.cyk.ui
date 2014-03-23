package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.layout.UILayout;
import org.cyk.utility.common.cdi.AbstractBean;


public abstract class AbstractView<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractBean implements UIView<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;

	@Getter @Setter protected UILayout layout;
	@Getter protected String title;
	@Getter @Setter protected Integer rowsCount=0,columnsCount = 2,_columnsCounter=0;
	@Getter @Setter protected UIContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> container;
	
	@Getter @Setter protected Object objectModel;
	protected Collection<Class<?>> groups = new LinkedHashSet<>();
	@Getter protected Collection<UIComponent<?>> components = new LinkedHashSet<>();
	
	@Override
	public void group(Class<?>... theGroupsClasses) {
		for(Class<?> clazz : theGroupsClasses)
			groups.add(clazz);
	}
		
}
