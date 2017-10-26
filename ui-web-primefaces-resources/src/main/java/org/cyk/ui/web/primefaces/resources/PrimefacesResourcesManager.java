package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.ui.web.primefaces.resources.page.Page;
import org.cyk.ui.web.primefaces.resources.page.layout.NorthEastSouthWestCenter;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanListener;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.NotificationHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Notifications;
import org.cyk.utility.common.userinterface.Request;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail.Builder.Target;
import org.cyk.utility.common.userinterface.container.Window;
import org.cyk.utility.common.userinterface.event.Confirm;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.InputTextarea;
import org.cyk.utility.common.userinterface.output.OutputText;
import org.cyk.utility.common.userinterface.panel.ConfirmationDialog;
import org.cyk.utility.common.userinterface.panel.Dialog;
import org.cyk.utility.common.userinterface.panel.NotificationDialog;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton @Named @Getter @Setter @Accessors(chain=true) @Deployment(initialisationType=InitialisationType.EAGER)
public class PrimefacesResourcesManager extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static PrimefacesResourcesManager INSTANCE;
	
	private ConfirmationDialog confirmationDialog = new ConfirmationDialog();
	private NotificationDialog notificationDialog = new NotificationDialog();
	
	public static PrimefacesResourcesManager getInstance() {
		if(INSTANCE == null)
			INSTANCE = new PrimefacesResourcesManager();
		return INSTANCE;
	}
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		
		//Properties.setDefaultValue(Window.class, Properties.TEMPLATE, Page.TEMPLATE);
		
		Properties.setDefaultValue(OutputText.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/outputText.xhtml");
		
		Properties.setDefaultValue(Form.Master.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/form.xhtml");
		Properties.setDefaultValue(Form.Master.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/form/default.xhtml");
		
		Properties.setDefaultValue(Form.Detail.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/dynaForm.xhtml");
		Properties.setDefaultValue(Form.Detail.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/dynaForm/default.xhtml");
		
		Properties.setDefaultValue(Command.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/command/commandButton.xhtml");
		Properties.setDefaultValue(Command.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/command/commandButton/default.xhtml");
		Properties.setDefaultValue(Command.class, Properties.AJAX, Boolean.TRUE);
		Properties.setDefaultValue(Command.class, Properties.GLOBAL, Boolean.TRUE);
		
		Properties.setDefaultValue(Confirm.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/event/confirm.xhtml");
		Properties.setDefaultValue(Confirm.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/event/confirm/default.xhtml");
		Properties.setDefaultValue(Confirm.class, Properties.DISABLED, Boolean.FALSE);
		
		Properties.setDefaultValue(Dialog.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/panel/dialog/dialog.xhtml");
		Properties.setDefaultValue(Dialog.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/panel/dialog/default.xhtml");
		Properties.setDefaultValue(Dialog.class, Properties.SHOW_HEADER, Boolean.TRUE);
		Properties.setDefaultValue(Dialog.class, Properties.HEADER, "DIALOG HEADER...");
		Properties.setDefaultValue(Dialog.class, Properties.MODAL, Boolean.TRUE);
		Properties.setDefaultValue(Dialog.class, Properties.APPEND_TO, "@(body)");
		Properties.setDefaultValue(Dialog.class, Properties.DRAGGABLE, Boolean.FALSE);
		Properties.setDefaultValue(Dialog.class, Properties.RESIZABLE, Boolean.FALSE);
		Properties.setDefaultValue(Dialog.class, Properties.CLOSABLE, Boolean.TRUE);
		
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/panel/dialog/confirmation/confirmationDialog.xhtml");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/panel/dialog/confirmation/confirmationDialog/default.xhtml");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.DISABLED, Boolean.FALSE);
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.GLOBAL, Boolean.TRUE);
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.HEADER, "Your Confirmation Dialog Header");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.MESSAGE, "Your Confirmation Dialog Message");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.SEVERITY, "alert");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.WIDTH, "auto");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.HEIGHT, "auto");
		
		Properties.setDefaultValue(NotificationDialog.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/panel/dialog/dialog.xhtml");
		Properties.setDefaultValue(NotificationDialog.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/panel/dialog/notifications.xhtml");
		Properties.setDefaultValue(NotificationDialog.class, Properties.SHOW_HEADER, Boolean.TRUE);
		Properties.setDefaultValue(NotificationDialog.class, Properties.HEADER, "NOTIFICATION HEADER...");
		Properties.setDefaultValue(NotificationDialog.class, Properties.MODAL, Boolean.TRUE);
		Properties.setDefaultValue(NotificationDialog.class, Properties.APPEND_TO, "@(body)");
		Properties.setDefaultValue(NotificationDialog.class, Properties.DRAGGABLE, Boolean.FALSE);
		Properties.setDefaultValue(NotificationDialog.class, Properties.RESIZABLE, Boolean.FALSE);
		Properties.setDefaultValue(NotificationDialog.class, Properties.CLOSABLE, Boolean.TRUE);
		
		Properties.setDefaultValue(Notifications.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/message/messages.xhtml");
		Properties.setDefaultValue(Notifications.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/dummyemptycontent.xhtml");
		Properties.setDefaultValue(Notifications.class, Properties.GLOBAL_ONLY, Boolean.TRUE);
		Properties.setDefaultValue(Notifications.class, Properties.SHOW_SUMMARY, Boolean.TRUE);
		Properties.setDefaultValue(Notifications.class, Properties.SHOW_DETAIL, Boolean.FALSE);
		Properties.setDefaultValue(Notifications.class, Properties.SHOW_ICON, Boolean.TRUE);
		Properties.setDefaultValue(Notifications.class, Properties.REDISPLAY, Boolean.TRUE);
		Properties.setDefaultValue(Notifications.class, Properties.ESCAPE, Boolean.FALSE);
	
		Properties.setDefaultValue(Request.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/ajax/ajaxStatus.xhtml");
		Properties.setDefaultValue(Request.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/dummyemptycontent.xhtml");
		
		Properties.setDefaultValue(InputText.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/input/text/oneline/inputText.xhtml");
		Properties.setDefaultValue(InputText.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/input/text/oneline/default.xhtml");
		Properties.setDefaultValue(InputText.class, Properties.TYPE, "InputText");
		
		Properties.setDefaultValue(InputTextarea.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/input/text/manyline/inputTextarea.xhtml");
		Properties.setDefaultValue(InputTextarea.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/input/text/manyline/inputTextarea/default.xhtml");
		Properties.setDefaultValue(InputTextarea.class, Properties.TYPE, "InputTextarea");
		
		Form.Detail.Builder.Target.Adapter.Default.DEFAULT_CLASS = (Class<? extends Target<?, ?, ?, ?>>) ClassHelper.getInstance().getByName(FormBuilderBasedOnDynamicForm.class);
	
		BeanListener.COLLECTION.add(new BeanListener.Adapter(){
			private static final long serialVersionUID = 1L;

			@Override
			public void propertiesMapInstanciated(AbstractBean instance,Properties properties) {
				super.propertiesMapInstanciated(instance,properties);
				if(instance instanceof Component){
					properties.setWidgetVar(RandomHelper.getInstance().getAlphabetic(10));
				}
				if(instance instanceof ConfirmationDialog){
					//((ConfirmationDialog)instance).getPropertiesMap().setWidgetVar(RandomHelper.getInstance().getAlphabetic(10));
					((ConfirmationDialog)instance).getYesCommand().getPropertiesMap().setType("button");
					((ConfirmationDialog)instance).getYesCommand().getPropertiesMap().setStyleClass("ui-confirmdialog-yes");
					((ConfirmationDialog)instance).getYesCommand().getPropertiesMap().setIcon("ui-icon-check");
					
					((ConfirmationDialog)instance).getNoCommand().getPropertiesMap().setType("button");
					((ConfirmationDialog)instance).getNoCommand().getPropertiesMap().setStyleClass("ui-confirmdialog-no");
					((ConfirmationDialog)instance).getNoCommand().getPropertiesMap().setIcon("ui-icon-close");
				}else if(instance instanceof NotificationDialog){
					properties.setGetter(Properties.VISIBLE, new Properties.Getter() {
						@Override
						public Object execute(Properties properties, Object key, Object nullValue) {
							return CollectionHelper.getInstance().getSize(FacesContext.getCurrentInstance().getMessageList()) > 0;
						}
					});
					((NotificationDialog)instance).getOkCommand().getPropertiesMap().setType("button");
					((NotificationDialog)instance).getOkCommand().getPropertiesMap().setOnClick(PrimefacesJavascriptHelper.getInstance().getMethodCallHide(properties));
				}else if(instance instanceof Request){
					properties.setOnStart(PrimefacesJavascriptHelper.getInstance().getMethodCallShow(((Request)instance).getStatusDialog()));
					properties.setOnComplete(PrimefacesJavascriptHelper.getInstance().getMethodCallHide(((Request)instance).getStatusDialog()));

					((Request)instance).getStatusDialog().getPropertiesMap().setInclude("/org.cyk.ui.web.primefaces.resources/include/processingstatus/dialog/default.xhtml");
					((Request)instance).getStatusDialog().getPropertiesMap().setMessage(StringHelper.getInstance().get("notification.request.status.processing", new Object[]{}));
					((Request)instance).getStatusDialog().getPropertiesMap().setClosable(Boolean.FALSE);
					((Request)instance).getStatusDialog().getPropertiesMap().setHeader("REQUEST STATUS HEADER HERE...");
				}else if(instance instanceof Window){
					properties.setContracts("defaultDesktop");
					properties.setTemplate("/org.cyk.ui.web.primefaces.resources/template/page/desktop/default.xhtml");
					properties.setInclude("/org.cyk.ui.web.primefaces.resources/include/page/default.xhtml");
					
					((Window)instance).getLayout().getPropertiesMap().setTemplate("/template/default.xhtml");
				}
			}
		});
		
		NotificationHelper.Notification.Viewer.Adapter.Default.DEFAULT_CLASS = (Class<NotificationHelper.Notification.Viewer>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.resources.NotificationHelper.Viewer.class);
	
		Component.Listener.COLLECTION.add(new Component.Listener.Adapter(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object build(Component component) {
				if(component instanceof Form.Detail)
					return Form.Detail.buildTarget((Form.Detail) component);
				return super.build(component);
			}
			
		});
		
		Window.Listener.COLLECTION.add(new Window.Listener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public Object createLayout(Window window) {
				return new NorthEastSouthWestCenter();
			}
		});
	}
		
	public Object getInputTextDefaultTemplate(){
		return Properties.getDefaultValue(InputText.class, Properties.TEMPLATE);
	}
	
	public Object getInputTextareaDefaultTemplate(){
		return Properties.getDefaultValue(InputTextarea.class, Properties.TEMPLATE);
	}
	
	public String getFacesMessageSeverityAsString(FacesMessage facesMessage){
		switch(facesMessage.getSeverity().getOrdinal()){
		case 0:return "info";
		case 1:return "warn";
		case 2:return "error";
		case 3:return "fatal";
		default: return "info";
		}
	}
	
	/**/
	
	public static class FormBuilderBasedOnDynamicForm extends Form.Detail.Builder.Target.Adapter.Default<DynaFormModel, DynaFormControl,DynaFormRow, DynaFormLabel> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public DynaFormRow createRow(DynaFormModel model) {
			return model.createRegularRow();
		}
		
		@Override
		public DynaFormControl addControl(DynaFormRow row, org.cyk.utility.common.userinterface.Control control,String type) {
			return row.addControl(control, type,control.getArea().getLength().getDistance().intValue()*2-1,control.getArea().getWidth().getDistance().intValue());
		}
		
		@Override
		public DynaFormLabel addLabel(DynaFormRow row, OutputText outputText) {
			return row.addLabel(outputText.getPropertiesMap().getValue().toString(),1,outputText.getArea().getWidth().getDistance()==null ? 1 : outputText.getArea().getWidth().getDistance().intValue());  
		}
		
		@Override
		public Target<DynaFormModel,DynaFormControl, DynaFormRow, DynaFormLabel> link(DynaFormControl control,DynaFormLabel label) {
			label.setForControl(control);  
			return this;
		}
		
		@Override
		public String getType(org.cyk.utility.common.userinterface.Control control) {
			return control.getClass().getSimpleName();
		}
		
	}
}
