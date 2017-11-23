package org.cyk.ui.web.api.resources;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.RequestHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton @Named @Getter @Setter @Accessors(chain=true) @Deployment(initialisationType=InitialisationType.EAGER)
public class WebResourcesManager extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static WebResourcesManager INSTANCE;
	
	public static final String FILE_STATIC_EXTENSION = ".xhtml";
	public static final String FILE_PROCESSING_EXTENSION = ".jsf";
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}

	public void initializeContext(ServletContextEvent servletContextEvent){
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_CONTEXT = StringUtils.replace(servletContextEvent.getServletContext().getContextPath(),Constant.CHARACTER_SLASH.toString(),Constant.EMPTY_STRING);
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP = new LinkedHashMap<>();
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP.put(WebResourcesManager.FILE_STATIC_EXTENSION, WebResourcesManager.FILE_PROCESSING_EXTENSION);
		
		ClassHelper.getInstance().map(RequestHelper.Listener.class, org.cyk.ui.web.api.resources.helper.RequestHelper.Listener.class);
	}
	
	public void destroyContext(ServletContextEvent servletContextEvent){
		
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
