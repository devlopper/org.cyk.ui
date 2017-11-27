package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.container.window.EditWindow;

public class ClassLocators implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class EditFormMasterClassLocator extends EditWindow.FormMaster.ClassLocator {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Class<?> locate(Class<?> basedClass) {
			return super.locate(basedClass);
		}
		
	}
	
}
