package org.cyk.ui.web.api;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;

import lombok.extern.java.Log;

@Log
public class NavigationHelper implements Serializable {

	private static final long serialVersionUID = 7627367286110326059L;
	
	public static final String SLASH = "/";
	
	public static final String QUERY_START = "?";
	private static final String QUERY_SEPARATOR = "&";
	private static final String QUERY_NAME_VALUE_SEPARATOR = "=";
	private static final String QUERY_PARAMETER_ENCODING = "UTF-8";
	
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

}
