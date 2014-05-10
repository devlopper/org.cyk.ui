package org.cyk.ui.web.api.handler;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;

public class DynamicResourceHandler extends ResourceHandlerWrapper {

	private static final Set<String> PATHS = new LinkedHashSet<String>();

	static {
		PATHS.add("/META-INF/resources");//images , css , ...
		PATHS.add("/META-INF/contracts");//templates
		PATHS.add("/META-INF/pages");//web pages
	}

	private ResourceHandler wrapped;

	public DynamicResourceHandler(ResourceHandler wrapped) {
		this.wrapped = wrapped;
	}

	public static void addPath(String path) {
		PATHS.add(path);
	}

	@Override
	public ViewResource createViewResource(FacesContext context, String resourceName) {
		// Resolves from WAR.
		ViewResource resource = super.createViewResource(context, resourceName);
		if (resource == null)
			// Resolves from JAR.
			for (String customBasePath : PATHS) {
				final URL url = getClass().getResource(customBasePath + resourceName);
				if (url == null) {
					// System.out.println("NOT IN : "+customBasePath+path);
				} else{
					resource = new ViewResource() {
						@Override
						public URL getURL() {
							return url;
						}
					};
					break;
				}
			}
		return resource;
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrapped;
	}

}