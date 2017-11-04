package org.cyk.ui.web.api.resources;

import java.io.Serializable;

import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton @Named @Getter @Setter @Accessors(chain=true) @Deployment(initialisationType=InitialisationType.EAGER)
public class WebResourcesManager extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static WebResourcesManager INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Class<?> getConverterClass(Class<?> aClass){
		if(ClassHelper.getInstance().isNumber(aClass))
			return ClassHelper.getInstance().getByName("javax.faces.convert."+aClass.getSimpleName()+"Converter");
		return null;
	}
	
	public Converter instanciateConverter(Class<?> aClass){
		Class<?> converterClass = getConverterClass(aClass);
		return converterClass == null ? null :(Converter) ClassHelper.getInstance().instanciateOne(converterClass);
	}
	
	public static WebResourcesManager getInstance() {
		if(INSTANCE==null)
			INSTANCE = new WebResourcesManager();
		return INSTANCE;
	}
}
