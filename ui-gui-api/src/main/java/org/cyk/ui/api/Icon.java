package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.BeanAdapter;

public enum Icon{
	
	ACTION_GO_BACK,ACTION_OK,ACTION_APPLY,ACTION_SAVE,ACTION_ADD,ACTION_UPDATE,ACTION_DELETE,ACTION_CLEAR,ACTION_EDIT,ACTION_CANCEL,ACTION_REMOVE,ACTION_OPEN
	,ACTION_ADMINISTRATE,ACTION_HELP,ACTION_PREVIEW,ACTION_SEARCH,ACTION_EXPORT,ACTION_EXPORT_PDF,ACTION_EXPORT_EXCEL,ACTION_LOGIN,ACTION_LOGOUT,ACTION_PRINT,ACTION_DOWNLOAD
	
	,THING_APPLICATION,THING_LICENCE,THING_TOOLS,THING_CALENDAR,THING_AGENDA,THING_USERACCOUNT,THING_CONTROLPANEL,THING_LIST,THING_FOLDER_COLLAPSED,THING_FOLDER_EXPANDED
	,THING_TABLE, THING_SECURITY, THING_NOTIFICATIONS, THING_HELP, THING_REPORT, THING_HOME, THING_CONNECTED,THING_URL,THING_ROLE,THING_MONEY,THING_FILE,THING_LOCATION_ARROW
	
	,PERSON, ACTION_SET
	
	;
	
	/**/
	
	public static GetIdentifierListener<?> DEFAULT_GET_IDENTIFIER_LISTENER;
	
	/**/
	
	public static interface GetIdentifierListener<IDENTIFIER>{
		IDENTIFIER get(Icon icon);
		IDENTIFIER getDefault();
		/**/
		
		public static class Adapter<IDENTIFIER> extends BeanAdapter implements GetIdentifierListener<IDENTIFIER>,Serializable{

			private static final long serialVersionUID = 390223813843448212L;

			@Override
			public IDENTIFIER get(Icon icon) {
				return null;
			}
			
			@Override
			public IDENTIFIER getDefault() {
				return null;
			}
			
			/**/
			
			public static class Default<IDENTIFIER> extends Adapter<IDENTIFIER> implements Serializable{
				private static final long serialVersionUID = -1794051632042084273L;
				
				@Override
				public IDENTIFIER get(Icon icon) {
					if(icon==null)
						return getDefault();
					return __get__(icon);
				}

				protected IDENTIFIER __get__(Icon icon) {
					return getDefault();
				}
			}
		}
	}
	
	public static <IDENTIFIER> IDENTIFIER get(Icon icon,GetIdentifierListener<IDENTIFIER> listener){
		return listener.get(icon);
	}
	
	public static Object get(Icon icon){
		return get(icon,DEFAULT_GET_IDENTIFIER_LISTENER);
	}
}