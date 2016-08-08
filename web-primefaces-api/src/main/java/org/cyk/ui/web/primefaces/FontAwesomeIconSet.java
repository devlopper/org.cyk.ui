package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.ui.api.Icon;

public class FontAwesomeIconSet extends org.cyk.ui.api.Icon.GetIdentifierListener.Adapter.Default<String> implements Serializable {

	private static final long serialVersionUID = 2468247673572346932L;

	public static final FontAwesomeIconSet INSTANCE = new FontAwesomeIconSet();
	
	@Override
	protected String __get__(Icon icon) {
		switch(icon){
		case ACTION_ADD:return "fa fa-plus";
		case ACTION_CANCEL:return "fa fa-close";
		case ACTION_OPEN:return "fa fa-folder-open";
		case ACTION_REMOVE:return "fa fa-trash";
		case ACTION_ADMINISTRATE:return "fa fa-gears";
		case ACTION_HELP:return "fa fa-question";
		case ACTION_APPLY:return  "fa fa-save" /*"fa fa-check" /*"ui-icon-check"*/;
		case ACTION_EDIT:return "fa fa-pencil";
		case ACTION_GO_BACK:return "fa fa-arrow-left";
		case ACTION_OK:return "fa fa-check";
		case ACTION_SAVE:return "fa fa-save";
		case ACTION_SEARCH:return "fa fa-search";
		case ACTION_PREVIEW:return "fa fa-eye";
		case ACTION_LOGOUT:return "fa fa-sign-out";
		case ACTION_LOGIN:return "fa fa-sign-in";
		case ACTION_EXPORT:return "fa fa-file";
		case ACTION_PRINT:return "fa fa-print";
		case ACTION_CLEAR: return "fa fa-eraser";
		case ACTION_EXPORT_EXCEL: return "fa fa-file-excel-o";
		case ACTION_EXPORT_PDF: return "fa fa-file-pdf-o";
		case ACTION_SET: return "ui-icon-wrench";
		case ACTION_DOWNLOAD: return "fa fa-download";
		case ACTION_UPDATE: return "fa fa-edit";
		case ACTION_DELETE: return "fa fa-remove";
		
		case THING_APPLICATION: return "ui-icon-";
		case THING_CALENDAR: return "fa fa-calendar";
		case THING_CONTROLPANEL: return "fa fa-gears";
		case THING_HELP: return "fa fa-question";
		case THING_LICENCE: return "ui-icon-document";
		case THING_LIST: return "fa fa-list";
		case THING_NOTIFICATIONS: return "fa fa-flag";
		case THING_REPORT: return "ui-icon-document";
		case THING_SECURITY: return "fa fa-lock";
		case THING_TOOLS: return "ui-icon-wrench";
		case THING_URL: return "fa fa-link";
		case THING_ROLE: return "fa fa-lock";
		case THING_USERACCOUNT: return "fa fa-key";
		case THING_HOME: return "fa fa-home";
		case THING_CONNECTED: return "ui-icon-newin";
		case THING_FOLDER_COLLAPSED: return "fa fa-folder";
		case THING_FOLDER_EXPANDED: return "fa fa-folder-open";
		case THING_TABLE: return "fa fa-table";
		case THING_MONEY: return "fa fa-money";
		case THING_AGENDA:return "fa fa-calendar-o";
		case THING_FILE:return "fa fa-file";
		
		case PERSON:return "fa fa-user";
		default:return getDefault();
		}
	}

}
