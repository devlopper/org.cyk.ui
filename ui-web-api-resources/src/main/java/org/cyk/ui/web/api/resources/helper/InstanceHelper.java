package org.cyk.ui.web.api.resources.helper;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.omnifaces.util.Faces;

public class InstanceHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Listener extends org.cyk.utility.common.helper.InstanceHelper.Listener.Adapter.Default{
    	private static final long serialVersionUID = 1L;

		@Override
		public Object act(Constant.Action action, Object instance) {
			super.act(action, instance);
			if(Constant.Action.LOGOUT.equals(action)){
				Faces.invalidateSession();
			}
			return null;
		}
    }
	
	public static class Label extends org.cyk.utility.common.helper.InstanceHelper.Stringifier.Label.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
		
	}
}
