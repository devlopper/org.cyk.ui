package org.cyk.ui.api;

import java.util.Map;

public interface INavigationManager {
	
	/**
	 * The view represented by this id
	 * @param id
	 * @param parameters
	 * @return
	 */
	Object view(Object id,Map<String,Object> parameters);

}
