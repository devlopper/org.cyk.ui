package org.cyk.ui.web.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.Part;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.validation.Client;

@Singleton @Named @Getter @Deployment(initialisationType=InitialisationType.EAGER)
public class WebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1690449792881915040L;
	
	private static WebManager INSTANCE;
	public static WebManager getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	private final Map<Class<? extends AbstractWebPage<?, ?,?, ?>>,Collection<Field>> requestParameterFieldsMap = new HashMap<Class<? extends AbstractWebPage<?,?,?,?>>, Collection<Field>>();
	
	private final String clientValidationGroupClass = Client.class.getName();
	
	private final String blockUIDialogWidgetId = "blockUIDialogWidget";
	private final String messageDialogWidgetId = "messageDialogWidget";
	private final String reportDataTableServletUrl = "/_cyk_report_/_datatable_/_jasper_/";
	
	@Setter private String decoratedTemplateInclude;
	
	private final String requestParameterFormModel = UIManager.getInstance().getFormModelParameter();
	private final String requestParameterClass = UIManager.getInstance().getClassParameter();
	private final String requestParameterIdentifiable = UIManager.getInstance().getIdentifiableParameter();
	private final String requestParameterWindowMode = "windowmode";
	private final String requestParameterWindowModeDialog = "windowmodedialog";
	private final String requestParameterWindowModeNormal = "windowmodenormal";
	private final String requestParameterPreviousUrl = "previousurl";
	private final String requestParameterPrint = "print";
	/*
	private final String requestParameterFileExtension = "fileextension";
	private final String requestParameterPdf = "pdf";
	private final String requestParameterXls = "xls";
	*/
	private final String requestParameterUrl = "url";
	private final String requestParameterOutcome = "outcome";
	
	public String facesMessageSeverity(FacesMessage facesMessage){
		switch(facesMessage.getSeverity().getOrdinal()){
		case 0:return "info";
		case 1:return "warn";
		case 2:return "error";
		case 3:return "fatal";
		default: return "info";
		}
	}
	
	public String fileName(Part part) {
		if(part==null || part.getHeader("content-disposition")==null)
			return null;
		for (String content : part.getHeader("content-disposition").split(";"))
			if (content.trim().startsWith("filename"))
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
		return null;
	}
	
	
}
