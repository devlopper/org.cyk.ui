package org.cyk.ui.web.api.resources.converter;
import java.io.Serializable;

import org.cyk.utility.common.helper.InstanceHelper;

public class ObjectIdentifierConverter extends AbstractBasedMapConverter implements Serializable {
	private static final long serialVersionUID = -1615078449226502960L;

	@Override
	protected String getString(Object object) {
		Object identifier = InstanceHelper.getInstance().getIdentifier(object);
		if(identifier==null){
			System.out.println("ObjectIdentifierConverter.getString() : Identifier is null for object "+object);
		}
		return identifier == null ? null : identifier.toString();
	}
	
}