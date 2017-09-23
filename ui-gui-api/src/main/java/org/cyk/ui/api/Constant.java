package org.cyk.ui.api;

public interface Constant {

	String RENDERED = "RENDERED";
	
	String DISABLED = "DISABLED";
	String IMMEDIATE = "IMMEDIATE";
	
	String ONCOMPLETE = "ONCOMPLETE";
	String ONCLICK = "ONCLICK";
	
	String CLASS = "CLASS";
	String STYLE_CLASS = "STYLE_CLASS";
	String STYLE = "STYLE";
	
	String TITLE = "TITLE";

	String WIDGET_VAR = "WIDGET_VAR";
	String ICON = "ICON";
	
	String ACTION = "ACTION";
	String AJAX = "AJAX";
	String PROCESS = "PROCESS";
	String UPDATE = "UPDATE";
	
	String TYPE = "TYPE";
	
	/**/
	
	String NAME_RENDERED = "NAME_RENDERED";
	String ICON_RENDERED = "NAME_RENDERED";
	
	public static interface Component {
			
		public static interface Visible extends Component {
							
			/**/
			
			public static interface Command extends Visible {
								
				public static interface Button extends Visible {
					
				}
				
				public static interface Link extends Visible {
					
				}
				
			}
			
			public static interface DataTable extends Visible {
				
			}
		}
	}
}
