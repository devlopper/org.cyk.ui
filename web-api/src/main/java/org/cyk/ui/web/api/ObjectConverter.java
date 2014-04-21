package org.cyk.ui.web.api;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.utility.common.AbstractMethod;

import lombok.extern.java.Log;

/**
 * Converter Using View Map to store and retrieve object.
 * No connection to data store (DB) needed
 * @author christian
 *
 */
@Named @Singleton @Log
public class ObjectConverter implements Converter {
	
	private static final String OBJECT_MAP_KEY = ObjectConverter.class.getSimpleName();
	private static final String NULL_STRING_VALUE = "";
	private static AbstractMethod<String, Object> GET_IDENTIFIER_METHOD = new AbstractMethod<String, Object>() {
		private static final long serialVersionUID = -7432836983653696239L;
		@Override
		protected String __execute__(Object parameter) {
			return RandomStringUtils.randomAlphabetic(3)+RandomStringUtils.randomAlphanumeric(3);
		}
	};
	
	private Map<String, Object> getObjectMap(FacesContext context) {
		Map<String, Object> viewMap = context.getViewRoot().getViewMap();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Object> objectMap = (Map) viewMap.get(OBJECT_MAP_KEY);
		if (objectMap == null) {
			objectMap = new HashMap<String, Object>();
			viewMap.put(OBJECT_MAP_KEY, objectMap);
		}
		return objectMap;
	}
	
	private String getIdentifier(Object object){
		if(object == null)
			return null;
		if(GET_IDENTIFIER_METHOD==null){
			log.severe("No object converter set");
			return null;
		}
		return GET_IDENTIFIER_METHOD.execute(object) ;
	}
	
	
	private String createNewIdentifierFrom(String identifier){
		return identifier+System.currentTimeMillis();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String identifier) {
		if (identifier==null || identifier.isEmpty())
			return null;
		return getObjectMap(context).get(identifier);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object object) {
		if (object == null) 
			return NULL_STRING_VALUE;
		String identifier = getIdentifier(object);
		if(identifier==null){
			log.warning("Identifier value of <"+object+"> is null");
			return NULL_STRING_VALUE;
		}
		// handle duplicate id (two object of different type can have the same id value)
		Map<String, Object> objectMap = getObjectMap(context);
		if(objectMap.containsKey(identifier))
			identifier = createNewIdentifierFrom(identifier);
		objectMap.put(identifier, object);
		return identifier;
	}
}