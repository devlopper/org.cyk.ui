package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public class IconHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Mapping extends org.cyk.ui.api.IconHelper.Mapping implements Serializable {
		private static final long serialVersionUID = 1L;
	
		@Deprecated
		public static class FontAwesome extends Mapping implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected Object __execute__() {
				switch(getInput()){
				case ACTION_ADD:return "fa fa-plus-circle";
				case ACTION_CANCEL:return "ui-icon-close";
				case ACTION_OPEN:return "fa fa-folder-open";
				case ACTION_ADMINISTRATE:return "ui-icon-gear";
				case ACTION_HELP:return "ui-icon-help";
				case ACTION_APPLY:return  "fa fa-save" /*"fa fa-check" /*"ui-icon-check"*/;
				case ACTION_EDIT:return "fa fa-pencil";
				case ACTION_GO_BACK:return "ui-icon-arrow-e";
				case ACTION_OK:return "fa fa-check";
				case ACTION_SAVE:return "fa fa-save";
				case ACTION_SEARCH:return "ui-icon-search";
				case ACTION_PREVIEW:return "ui-icon-image";
				case ACTION_LOGOUT:return "ui-icon-extlink";
				case ACTION_EXPORT:return "ui-icon-document";
				case ACTION_PRINT:return "ui-icon-print";
				case ACTION_CLEAR: return "ui-icon-trash";
				case ACTION_EXPORT_EXCEL: return "ui-icon-";
				case ACTION_EXPORT_PDF: return "ui-icon-";
				case ACTION_SET: return "ui-icon-wrench";
				case ACTION_DOWNLOAD: return "fa fa-download";
				case ACTION_UPDATE: return "fa fa-edit";
				case ACTION_REMOVE:return "fa fa-remove";
				case ACTION_DELETE: return "fa fa-trash";
				
				case THING_APPLICATION: return "ui-icon-";
				case THING_CALENDAR: return "ui-icon-calendar";
				case THING_CONTROLPANEL: return "ui-icon-gear";
				case THING_HELP: return "ui-icon-help";
				case THING_LICENCE: return "ui-icon-document";
				case THING_LIST: return "ui-icon-document";
				case THING_NOTIFICATIONS: return "ui-icon-flag";
				case THING_REPORT: return "ui-icon-document";
				case THING_SECURITY: return "ui-icon-key";
				case THING_TOOLS: return "ui-icon-wrench";
				case THING_URL: return "fa fa-link";
				case THING_ROLE: return "fa fa-lock";
				case THING_USERACCOUNT: return "fa fa-key";
				case THING_HOME: return "ui-icon-home";
				case THING_CONNECTED: return "ui-icon-newin";
				case THING_FOLDER_COLLAPSED: return "fa fa-folder";
				case THING_FOLDER_EXPANDED: return "fa fa-folder-open";
				case THING_TABLE: return "fa fa-table";
				case THING_MONEY: return "fa fa-money";
				case THING_AGENDA:return "fa fa-calendar-o";
				
				case PERSON:return "fa fa-user";
				default:return null;
			}
		}
		}
	}
}