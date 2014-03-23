package org.cyk.ui.web.api.resolver;
/*
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.faces.view.facelets.ResourceResolver;

import lombok.extern.java.Log;

@Log
public class FaceletsResourceResolver extends ResourceResolver {

    private ResourceResolver parent;
    private static Set<String> PATHS = new LinkedHashSet<String>();
    
    static{
    	PATHS.add("/META-INF/resources");
    	PATHS.add("/META-INF/pages");
    }
    
    public static void addPath(String path){
    	PATHS.add(path);
    }
    
    public FaceletsResourceResolver(ResourceResolver parent) {
        this.parent = parent;
    }

    @Override
    public URL resolveUrl(String path) {
        URL url = parent.resolveUrl(path); // Resolves from WAR.
        if (url == null)
        	for(String customBasePath : PATHS){
        		url = getClass().getResource(customBasePath + path); // Resolves from JAR.
        		if(url==null){
        			//System.out.println("NOT IN : "+customBasePath+path);
        		}else
        			break;
        	}
        return url;
    }
    
    public static void showInfos(){
    	StringBuilder s = new StringBuilder();
    	s.append("Resources Paths :\r\n");
    	for(String customBasePath : PATHS)
    		s.append("\r\n\t"+customBasePath);
    	log.info(s.toString());		
    }

}*/
