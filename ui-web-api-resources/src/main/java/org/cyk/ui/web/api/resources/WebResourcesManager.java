package org.cyk.ui.web.api.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.ContentType;
import org.cyk.utility.common.userinterface.RequestHelper;
import org.omnifaces.util.Faces;

@Singleton @Named @Getter @Setter @Accessors(chain=true) @Deployment(initialisationType=InitialisationType.EAGER)
public class WebResourcesManager extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static WebResourcesManager INSTANCE;
	
	public static final String STYLE_SHEET_HREF_FORMAT = "/javax.faces.resource/css/%s.css.jsf?ln=%s";
	public static final String FILE_STATIC_EXTENSION = ".xhtml";
	public static final String FILE_PROCESSING_EXTENSION = ".jsf";
	
	private final List<String> styleSheetsHrefs = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}

	public void initializeContext(ServletContextEvent servletContextEvent){
		ContentType.DEFAULT = ContentType.HTML;
		Component.RENDER_AS_CONTENT_TYPE = ContentType.DEFAULT;
		Component.ClassLocator.GetOrgCykSystem.WINDOW = "Page";
		
		addStyleSheetHrefFromName("common","page","datatable","form");
		
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.IDENTIFIER_HOME = "homeView";
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_CONTEXT = StringUtils.replace(servletContextEvent.getServletContext().getContextPath(),Constant.CHARACTER_SLASH.toString(),Constant.EMPTY_STRING);
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP = new LinkedHashMap<>();
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP.put(WebResourcesManager.FILE_STATIC_EXTENSION, WebResourcesManager.FILE_PROCESSING_EXTENSION);
		
		//Realm.DATA_SOURCE = applicationBusiness.findShiroConfigurator().getDataSource();
		//WebEnvironmentAdapter.DATA_SOURCE = Realm.DATA_SOURCE;
		//WebEnvironmentListener.Adapter.DATA_SOURCE = Realm.DATA_SOURCE;
		
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
	
	public void redirect(String url){
		//logTrace("Redirect to {} , Committed = {}", url,Faces.isResponseCommitted());
		try {
			//throw new RuntimeException();
			Faces.redirect(url);
			//Faces.responseComplete();
		} catch (Exception e) {
			logThrowable(e);
			//log.log(Level.SEVERE,e.toString(),e);
		}
	}
	
	public void addStyleSheetHref(String href){
		styleSheetsHrefs.add(href);
	}
	
	public void addStyleSheetHrefFromName(String name,String library){
		addStyleSheetHref(String.format(STYLE_SHEET_HREF_FORMAT, name,library));
	}
	
	public void addStyleSheetHrefFromName(String...names){
		for(String name : names)
			addStyleSheetHrefFromName(name,"org.cyk.ui.web.primefaces.resources");
	}
	
	public static WebResourcesManager getInstance() {
		if(INSTANCE==null)
			INSTANCE = new WebResourcesManager();
		return INSTANCE;
	}
}
