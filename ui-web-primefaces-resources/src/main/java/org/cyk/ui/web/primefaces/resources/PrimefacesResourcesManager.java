package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.web.api.resources.NumberConverter;
import org.cyk.ui.web.api.resources.ObjectConverter;
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
import org.cyk.utility.common.helper.SelectItemHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.InteractivityBlocker;
import org.cyk.utility.common.userinterface.Notifications;
import org.cyk.utility.common.userinterface.Request;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail.Builder.Target;
import org.cyk.utility.common.userinterface.container.Window;
import org.cyk.utility.common.userinterface.event.Confirm;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputBooleanButton;
import org.cyk.utility.common.userinterface.input.InputBooleanCheckBox;
import org.cyk.utility.common.userinterface.input.InputCalendar;
import org.cyk.utility.common.userinterface.input.InputEditor;
import org.cyk.utility.common.userinterface.input.InputFile;
import org.cyk.utility.common.userinterface.input.InputPassword;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.InputTextarea;
import org.cyk.utility.common.userinterface.input.Watermark;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyAutoComplete;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyButton;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyCheck;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyCombo;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyList;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyPickList;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneAutoComplete;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneButton;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneCascadeList;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneCombo;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneList;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceOneRadio;
import org.cyk.utility.common.userinterface.input.choice.SelectItems;
import org.cyk.utility.common.userinterface.input.number.InputNumber;
import org.cyk.utility.common.userinterface.output.OutputText;
import org.cyk.utility.common.userinterface.panel.ConfirmationDialog;
import org.cyk.utility.common.userinterface.panel.Dialog;
import org.cyk.utility.common.userinterface.panel.NotificationDialog;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.DualListModel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@SuppressWarnings("unchecked")
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
		
		Properties.setDefaultValues(Window.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/page/desktop/default.xhtml"
				,Properties.CONTRACTS,"defaultDesktop",Properties.INCLUDE,"/org.cyk.ui.web.primefaces.resources/include/page/default.xhtml"});
		
		Properties.setDefaultValue(OutputText.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/outputText.xhtml");
		
		Properties.setDefaultValues(Form.Master.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/form/include.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/form/default.xhtml"});
		
		Properties.setDefaultValues(Form.Detail.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/dynaForm.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/dynaForm/default.xhtml"});
		
		Properties.setDefaultValues(InteractivityBlocker.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/blockUI/blockUI.xhtml"
				,Properties.INCLUDE,"/org.cyk.ui.web.primefaces.resources/include/blockUI/default.xhtml",Properties.CENTER_X,Boolean.TRUE,Properties.CENTER_Y,Boolean.TRUE});
		/*
		Properties.setDefaultValues(Watermark.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/watermark/watermark.xhtml"
				,Properties.INCLUDE,"/org.cyk.ui.web.primefaces.resources/include/watermark/default.xhtml"});
		*/
		setComponentTemplateAndIncludeDefaultValues(Watermark.class, "support");
		
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
		Properties.setDefaultValue(Notifications.class, Properties.REDISPLAY, Boolean.FALSE);
		Properties.setDefaultValue(Notifications.class, Properties.ESCAPE, Boolean.FALSE);
	
		Properties.setDefaultValue(Request.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/ajax/ajaxStatus.xhtml");
		Properties.setDefaultValue(Request.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/dummyemptycontent.xhtml");
		
		Properties.setDefaultValues(Input.class, new Object[]{Properties.STRUCTURE_TEMPLATE
				, "/org.cyk.ui.web.primefaces.resources/template/decorate/input/__layout__/1.xhtml"});
		
		setInputDefaultValues(InputText.class, "text/oneline");
		setInputDefaultValues(InputPassword.class, "text/oneline",Boolean.TRUE);
		
		setInputDefaultValues(InputTextarea.class, "text/manyline");
		setInputDefaultValues(InputEditor.class, "text/manyline",Boolean.TRUE);
		
		setInputDefaultValues(InputBooleanCheckBox.class, "choice/one/boolean","selectBooleanCheckBox");
		
		setInputDefaultValues(InputBooleanButton.class, "choice/one/boolean","selectBooleanButton");
		Properties.setDefaultValues(InputBooleanButton.class, new Object[]{Properties.ON_LABEL, StringHelper.getInstance().getResponse(Boolean.TRUE)
				,Properties.OFF_LABEL,StringHelper.getInstance().getResponse(Boolean.FALSE)});
		
		setInputDefaultValues(InputNumber.class, "number");
		
		setInputDefaultValues(InputCalendar.class, "time",Boolean.TRUE);
		
		Properties.setDefaultValues(InputChoice.class, new Object[]{Properties.CONVERTER, inject(ObjectConverter.class)
				,Properties.SELECT_ITEM_WRAPPABLE, Boolean.TRUE});
		setInputDefaultValues(InputChoiceOneAutoComplete.class, "choice","autoComplete");
		setInputDefaultValues(InputChoiceOneCombo.class, "choice/one","selectOneMenu");
		setInputDefaultValues(InputChoiceOneList.class, "choice/one","selectOneListbox");
		setInputDefaultValues(InputChoiceOneButton.class, "choice/one","selectOneButton");
		setInputDefaultValues(InputChoiceOneRadio.class, "choice/one","selectOneRadio");
		setInputDefaultValues(InputChoiceOneCascadeList.class, "choice/one","multiSelectListbox");
		
		setInputDefaultValues(InputChoiceManyAutoComplete.class, "choice","autoComplete");
		setInputDefaultValues(InputChoiceManyButton.class, "choice/many","selectManyButton");
		setInputDefaultValues(InputChoiceManyCheck.class, "choice/many","selectManyCheckbox");
		setInputDefaultValues(InputChoiceManyList.class, "choice/many","selectManyMenu");
		setInputDefaultValues(InputChoiceManyCombo.class, "choice/many","selectCheckboxMenu");
		Properties.setDefaultValues(InputChoiceManyCombo.class, new Object[]{Properties.LABEL, StringHelper.getInstance().getWord("choice", Boolean.TRUE, Boolean.TRUE)});
		setInputDefaultValues(InputChoiceManyPickList.class, "choice/many","pickList");
		Properties.setDefaultValues(InputChoiceManyPickList.class, new Object[]{Properties.SELECT_ITEM_WRAPPABLE, Boolean.FALSE});
		
		setInputDefaultValues(InputFile.class, "file","fileUpload");
		Properties.setDefaultValues(InputFile.class, new Object[]{Properties.PREVIEW_WIDTH, 150
				,Properties.PREVIEW_HEIGHT,150,Properties.MODE,"simple",Properties.PREVIEWABLE,Boolean.FALSE,Properties.CLEARABLE,Boolean.FALSE
				,Properties.SKIN_SIMPLE,Boolean.TRUE});
		
		Form.Detail.Builder.Target.Adapter.Default.DEFAULT_CLASS = (Class<? extends Target<?, ?, ?, ?>>) ClassHelper.getInstance().getByName(FormBuilderBasedOnDynamicForm.class);
	
		BeanListener.COLLECTION.add(new BeanListener.Adapter(){
			private static final long serialVersionUID = 1L;

			@Override
			public void propertiesMapInstanciated(AbstractBean instance,Properties properties) {
				super.propertiesMapInstanciated(instance,properties);
				if(instance instanceof Component){
					properties.setWidgetVar(RandomHelper.getInstance().getAlphabetic(10));
				}else if(instance instanceof SelectItems){
					/*properties.setGetter(Properties.LABEL, new Properties.Getter() {
						@Override
						public Object execute(Properties properties, Object key, Object nullValue) {
							return CollectionHelper.getInstance().getSize(FacesContext.getCurrentInstance().getMessageList()) > 0;
						}
					});*/
				}
				if(instance instanceof ConfirmationDialog){
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
					((Window)instance).getLayout().getPropertiesMap().setTemplate("/template/default.xhtml");
				}else if(instance instanceof Form.Master){
					Form.Master form = (Form.Master) instance;
					form.getSubmitCommand().getPropertiesMap().setProcess("@this "+form.getDetail().getPropertiesMap().getIdentifier());
					form.getSubmitCommand().getPropertiesMap().setUpdate(form.getDetail().getPropertiesMap().getIdentifier());
					form.getSubmitCommand().getPropertiesMap().setType("submit");
					form.getSubmitCommand().getPropertiesMap().setPartialSubmit(Boolean.TRUE);
					
					//setInteractivityBlocker(form, Boolean.TRUE);
				}else if(instance instanceof InputChoice<?>){
					/*properties.setGetter(Properties.CONVERTER, new Properties.Getter() {
						@Override
						public Object execute(Properties properties, Object key, Object nullValue) {
							return inject(ObjectConverter.class);//.getInstance();
						}
					});*/
					
					if(instance instanceof InputChoiceManyPickList){
						if(((InputChoiceManyPickList)instance).getChoices().getElements() == null)
							((InputChoiceManyPickList)instance).getChoices().setElements(new ArrayList<Object>());
						if(((InputChoiceManyPickList)instance).getValue() == null)
							((InputChoiceManyPickList)instance).setValue(new ArrayList<Object>());
						((InputChoiceManyPickList)instance).setValueObject(new DualListModel<Object>((List<Object>) ((InputChoiceManyPickList)instance).getChoices().getElements()
								, (List<Object>) ((InputChoiceManyPickList)instance).getValue()));
					}
					
				}else if(instance instanceof InputFile){
					((Command)properties.getClearCommand()).getPropertiesMap().setProcess("@this");
				}else if(instance instanceof InputNumber){
					
				}
				
			}
		});
		
		NotificationHelper.Notification.Viewer.Adapter.Default.DEFAULT_CLASS = (Class<NotificationHelper.Notification.Viewer>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.resources.NotificationHelper.Viewer.class);
		SelectItemHelper.Builder.One.Adapter.Default.DEFAULT_CLASS = org.cyk.ui.web.api.resources.SelectItemHelper.OneBuilder.class;
		InputChoiceManyPickList.DEFAULT_CLASS = org.cyk.ui.web.primefaces.resources.input.InputChoiceManyPickList.class;
		
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
	/*
	public static final String CONTROL_TEMPLATE_FORMAT = "/org.cyk.ui.web.primefaces.resources/template/decorate/%s/%s/%s.xhtml";
	public static final String CONTROL_INCLUDE_FORMAT = "/org.cyk.ui.web.primefaces.resources/include/%s/%s/default.xhtml";
	*/
	
	public static final String COMPONENT_TEMPLATE_FORMAT = "/org.cyk.ui.web.primefaces.resources/template/decorate/%s/%s/%s.xhtml";
	public static final String COMPONENT_INCLUDE_FORMAT = "/org.cyk.ui.web.primefaces.resources/include/%s/%s/%s/default.xhtml";
	
	protected static <COMPONENT extends Component> void setComponentTemplateAndIncludeDefaultValues(Class<COMPONENT> aClass,String family,String relativePath,String fileName){
		Properties.setDefaultValues(aClass, new Object[]{
				Properties.TEMPLATE, String.format(COMPONENT_TEMPLATE_FORMAT, family,relativePath,fileName)
				, Properties.INCLUDE, String.format(COMPONENT_INCLUDE_FORMAT, family,relativePath,fileName)
				, Properties.TYPE, getComponentTypeForDynaForm(aClass)
			});
	}
	
	protected static <COMPONENT extends Component> void setComponentTemplateAndIncludeDefaultValues(Class<COMPONENT> aClass,String family,String relativePath){
		setComponentTemplateAndIncludeDefaultValues(aClass, family, relativePath, getTemplateFileName(aClass));
	}
	
	protected static <COMPONENT extends Component> void setComponentTemplateAndIncludeDefaultValues(Class<COMPONENT> aClass,String family){
		setComponentTemplateAndIncludeDefaultValues(aClass, family, aClass.getSimpleName().toLowerCase());
	}
	
	protected static <CONTROL extends Control> void setInputDefaultValues(Class<CONTROL> aClass,String relativePath,String fileName){
		setComponentTemplateAndIncludeDefaultValues(aClass,"input",relativePath,fileName);
	}
	
	protected static <CONTROL extends Control> void setInputDefaultValues(Class<CONTROL> aClass,String relativePath,Boolean removeInputPrefixFromFileName){
		String fileName = getTemplateFileName(aClass);
		if(Boolean.TRUE.equals(removeInputPrefixFromFileName))
			fileName = getTemplateFileNameFromClassName(StringUtils.substringAfter(fileName, "input"));
		setInputDefaultValues(aClass, relativePath,  fileName );
	}
	
	protected static <CONTROL extends Control> void setInputDefaultValues(Class<CONTROL> aClass,String relativePath){
		setInputDefaultValues(aClass, relativePath, getTemplateFileName(aClass));
	}
	
	protected static <CONTROL extends Control> void setOutputDefaultValues(Class<CONTROL> aClass,String relativePath){
		setComponentTemplateAndIncludeDefaultValues(aClass,"output",relativePath);
	}
	
	public Object getWatermarkDefaultTemplate(){
		return Properties.getDefaultValue(Watermark.class, Properties.TEMPLATE);
	}
	
	public Object getInputTextDefaultTemplate(){
		return Properties.getDefaultValue(InputText.class, Properties.TEMPLATE);
	}
	
	public Object getInputTextareaDefaultTemplate(){
		return Properties.getDefaultValue(InputTextarea.class, Properties.TEMPLATE);
	}
	
	public Object getInputCalendarDefaultTemplate(){
		return Properties.getDefaultValue(InputCalendar.class, Properties.TEMPLATE);
	}
	
	public Object getInputPasswordDefaultTemplate(){
		return Properties.getDefaultValue(InputPassword.class, Properties.TEMPLATE);
	}
	
	public Object getInputEditorDefaultTemplate(){
		return Properties.getDefaultValue(InputEditor.class, Properties.TEMPLATE);
	}
	
	public Object getInputNumberDefaultTemplate(){
		return Properties.getDefaultValue(InputNumber.class, Properties.TEMPLATE);
	}
	
	public Object getInputBooleanCheckBoxDefaultTemplate(){
		return Properties.getDefaultValue(InputBooleanCheckBox.class, Properties.TEMPLATE);
	}
	
	public Object getInputBooleanButtonDefaultTemplate(){
		return Properties.getDefaultValue(InputBooleanButton.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceOneAutoCompleteDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceOneAutoComplete.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceOneComboDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceOneCombo.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceOneListDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceOneList.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceOneButtonDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceOneButton.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceOneRadioDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceOneRadio.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceOneCascadeListDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceOneCascadeList.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceManyAutoCompleteDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceManyAutoComplete.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceManyCheckDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceManyCheck.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceManyButtonDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceManyButton.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceManyComboDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceManyCombo.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceManyPickListDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceManyPickList.class, Properties.TEMPLATE);
	}
	
	public Object getInputChoiceManyListDefaultTemplate(){
		return Properties.getDefaultValue(InputChoiceManyList.class, Properties.TEMPLATE);
	}
	
	public Object getInputFileDefaultTemplate(){
		return Properties.getDefaultValue(InputFile.class, Properties.TEMPLATE);
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
	
	public static String getComponentTypeForDynaForm(Class<?> aClass){
		if(ClassHelper.getInstance().isInstanceOf(InputNumber.class,aClass))
			aClass = InputNumber.class;
		return aClass.getSimpleName();
	}
	
	public static String getTemplateFileName(Class<?> aClass){
		return getTemplateFileNameFromClassName(aClass.getSimpleName());
	}
	
	public static String getTemplateFileNameFromClassName(String classSimpleName){
		return StringHelper.getInstance().applyCaseType(classSimpleName,CaseType.FL);
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
			return getComponentTypeForDynaForm(control.getClass());
		}
		
	}

	public static void setInteractivityBlocker(Form.Master form,Boolean global){
		form.getSubmitCommand().getPropertiesMap().setGlobal(global);
		if(Boolean.TRUE.equals(global)){
			form.getPropertiesMap().setInteractivityBlocker(null);
		}else {
			form.getPropertiesMap().setInteractivityBlocker(new InteractivityBlocker());
			form.getSubmitCommand().getPropertiesMap().setOnStart(PrimefacesJavascriptHelper.getInstance()
					.getMethodCallBlock(form.getPropertiesMap().getInteractivityBlocker()));
			form.getSubmitCommand().getPropertiesMap().setOnComplete(PrimefacesJavascriptHelper.getInstance()
					.getMethodCallUnBlock(form.getPropertiesMap().getInteractivityBlocker()));
			
			((InteractivityBlocker)form.getPropertiesMap().getInteractivityBlocker()).getPropertiesMap().setSource(form.getSubmitCommand().getPropertiesMap().getIdentifier());
			((InteractivityBlocker)form.getPropertiesMap().getInteractivityBlocker()).getPropertiesMap().setTarget(form.getDetail().getPropertiesMap().getIdentifier());
		}
	}
}
