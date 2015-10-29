package org.cyk.ui.web.api;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import javax.inject.Singleton;

import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.utility.common.cdi.AbstractBean;

@Log @Singleton
public class NavigationHelper extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7627367286110326059L;
	
	private static NavigationHelper INSTANCE; 
	
	public static final String QUERY_START = "?";
	private static final String QUERY_SEPARATOR = "&";
	private static final String QUERY_NAME_VALUE_SEPARATOR = "=";
	private static final String QUERY_PARAMETER_ENCODING = "UTF-8";
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String addQueryParameters(String aUrl,Object[] parameters){
		//System.out.println(aUrl);
		StringBuilder url = new StringBuilder(aUrl);
	    if(parameters!=null && parameters.length>0){
	    	for(int i=0;i<parameters.length-1;i=i+2)
	    		addParameter(url, /*(String)*/ parameters[i], parameters[i+1]);
	    }
	    return url.toString();
	}
	
	public void addParameter(StringBuilder url,Object name,Object value){
		if(name instanceof Class<?>)
			if(AbstractIdentifiable.class.isAssignableFrom((Class<?>) name))
				name = UIManager.getInstance().businessEntityInfos((Class<?>) name).getIdentifier();
			else
				;
		else
			name = name.toString();
		if(value instanceof AbstractIdentifiable)
			value = ((AbstractIdentifiable)value).getIdentifier();
		try {
			url.append((url.toString().contains(QUERY_START)?QUERY_SEPARATOR:QUERY_START)+name+QUERY_NAME_VALUE_SEPARATOR+URLEncoder.encode(value.toString(), QUERY_PARAMETER_ENCODING));
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
	}
	
	public Collection<Parameter> getParameters(String url){
		Collection<Parameter> collection = new ArrayList<>();
		for(String pair : StringUtils.split(StringUtils.substringAfter(url, QUERY_START),QUERY_SEPARATOR)){
			String[] values = StringUtils.split(pair,QUERY_NAME_VALUE_SEPARATOR);
			collection.add(new Parameter(values[0], values[1]));
		}
		return collection;
	}
	
	public static NavigationHelper getInstance() {
		return INSTANCE;
	}

}
