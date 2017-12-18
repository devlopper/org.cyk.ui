package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.web.api.resources.converter.ObjectIdentifierConverter;
import org.cyk.ui.web.api.resources.converter.ObjectLabelConverter;
import org.cyk.ui.web.primefaces.resources.page.layout.NorthEastSouthWestCenter;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanListener;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.JQueryHelper;
import org.cyk.utility.common.helper.JavaScriptHelper;
import org.cyk.utility.common.helper.NotificationHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.SelectItemHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.InteractivityBlocker;
import org.cyk.utility.common.userinterface.Notifications;
import org.cyk.utility.common.userinterface.Request;
import org.cyk.utility.common.userinterface.RequestHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail.Builder.Target;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.common.userinterface.event.Confirm;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.common.userinterface.hierarchy.Hierarchy;
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
import org.cyk.utility.common.userinterface.output.Output;
import org.cyk.utility.common.userinterface.output.OutputFile;
import org.cyk.utility.common.userinterface.output.OutputLink;
import org.cyk.utility.common.userinterface.output.OutputText;
import org.cyk.utility.common.userinterface.panel.ConfirmationDialog;
import org.cyk.utility.common.userinterface.panel.Dialog;
import org.cyk.utility.common.userinterface.panel.NotificationDialog;
import org.cyk.utility.common.userinterface.tree.Tree;
import org.cyk.utility.common.userinterface.tree.TreeNode;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DualListModel;

@SuppressWarnings("unchecked")
@Singleton @Named @Getter @Setter @Accessors(chain=true) @Deployment(initialisationType=InitialisationType.EAGER)
public class PrimefacesResourcesManager extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static PrimefacesResourcesManager INSTANCE;
	
	public static FileHelper.File LOGO_FILE;
	
	private ConfirmationDialog confirmationDialog = new ConfirmationDialog();
	private NotificationDialog notificationDialog = new NotificationDialog();
	
	public static PrimefacesResourcesManager getInstance() {
		if(INSTANCE == null)
			INSTANCE = new PrimefacesResourcesManager();
		return INSTANCE;
	}
	
	public void initialize(){
		JQueryHelper.JQUERY = "$";
		NotificationHelper.Notification.Viewer.Adapter.Default.DEFAULT_CLASS = (Class<NotificationHelper.Notification.Viewer>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.resources.NotificationHelper.Viewer.class);
		SelectItemHelper.Builder.One.Adapter.Default.DEFAULT_CLASS = org.cyk.ui.web.api.resources.SelectItemHelper.OneBuilder.class;
		ClassHelper.getInstance().map(Menu.Builder.Adapter.Default.class,MenuBuilder.class);
		ClassHelper.getInstance().map(UniformResourceLocatorHelper.Listener.class,org.cyk.ui.web.api.resources.helper.UniformResourceLocatorHelper.Listener.class);
		ClassHelper.getInstance().map(Output.Listener.class,OutputAdapter.class);
		ClassHelper.getInstance().map(Component.Listener.class,ComponentAdapter.class);
		
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_UNIFORM_RESOURCE_LOCATOR_LISTENER_CLASS = org.cyk.ui.web.api.resources.helper.UniformResourceLocatorHelper.Listener.class;
		
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		
		Properties.setDefaultValues(Window.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/page/desktop/default.xhtml"
				,Properties.CONTRACTS,"org.cyk.ui.web.primefaces.resources.desktop.default"
				,Properties.INCLUDE,"/org.cyk.ui.web.primefaces.resources/include/page/default.xhtml"
				//,Properties.MAIN_MENU,Menu.build(null, Menu.Type.MAIN)
				,Properties.FOOTER,"MY FOOT HERE"
		});
		
		Properties.setDefaultValue(OutputText.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/outputText.xhtml");
		Properties.setDefaultValue(OutputText.class, Properties.ESCAPE, Boolean.FALSE);
		
		Properties.setDefaultValue(OutputFile.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/output/file/file.xhtml");
		Properties.setDefaultValue(OutputFile.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/output/file/outputFile/default.xhtml");
		
		Properties.setDefaultValue(Event.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/ajax/ajax.xhtml");
		Properties.setDefaultValue(Event.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/ajax/ajax/default.xhtml");
		Properties.setDefaultValue(Event.class, Properties.GLOBAL, Boolean.TRUE);
		
		Properties.setDefaultValue(OutputLink.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/outputLink.xhtml");
		Properties.setDefaultValue(OutputLink.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/outputLink/default.xhtml");
		
		Properties.setDefaultValues(Form.Master.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/form/include.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/form/default.xhtml"});
		
		Properties.setDefaultValues(Form.Detail.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/dynaForm.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/dynaForm/default.xhtml"});
		
		Properties.setDefaultValues(InteractivityBlocker.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/blockUI/blockUI.xhtml"
				,Properties.INCLUDE,"/org.cyk.ui.web.primefaces.resources/include/blockUI/default.xhtml",Properties.CENTER_X,Boolean.TRUE,Properties.CENTER_Y,Boolean.TRUE});
		
		Properties.setDefaultValues(Image.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/output/file/graphicImage.xhtml"
				,Properties.ALT,"Image",Properties.STREAM,Boolean.TRUE});
		
		Properties.setDefaultValues(TreeNode.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/hierarchy/tree/node.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/hierarchy/tree/node/default.xhtml"
				,Properties.TYPE,"default"});
		
		Properties.setDefaultValues(Tree.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/hierarchy/tree/tree.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/hierarchy/tree/default.xhtml"});
		
		Properties.setDefaultValues(DataTable.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/collection/dataTable.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/collection/dataTable/default.xhtml",Properties.FIRST,0
				,Properties.PAGE_LINKS,10,Properties.PAGINATOR_POSITION,"both"
				,Properties.EMPTY_MESSAGE,StringHelper.getInstance().get("notification.data.collection.empty", new Object[]{})});
		
		Properties.setDefaultValues(DataTable.Column.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/collection/column.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/collection/column/default.xhtml"});
		
		Properties.setDefaultValues(DataTable.Columns.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/collection/columns.xhtml"
				,Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/collection/columns/default.xhtml"});
		/*
		Properties.setDefaultValues(Watermark.class, new Object[]{Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/watermark/watermark.xhtml"
				,Properties.INCLUDE,"/org.cyk.ui.web.primefaces.resources/include/watermark/default.xhtml"});
		*/
		setComponentTemplateAndIncludeDefaultValues(Watermark.class, "support");
		//setComponentTemplateAndIncludeDefaultValues(Image.class, "support");
		
		Properties.setDefaultValue(Command.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces.resources/template/decorate/command/commandButton.xhtml");
		Properties.setDefaultValue(Command.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/command/commandButton/default.xhtml");
		Properties.setDefaultValue(Command.class, Properties.AJAX, Boolean.TRUE);
		Properties.setDefaultValue(Command.class, Properties.GLOBAL, Boolean.TRUE);
		
		//Properties.setDefaultValue(Hierarchy.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/dummyemptycontent.xhtml");
		Properties.setDefaultValue(Menu.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces.resources/include/dummyemptycontent.xhtml");
		
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
		
		Properties.setDefaultValues(InputChoice.class, new Object[]{Properties.SELECT_ITEM_WRAPPABLE, Boolean.TRUE});
		setInputDefaultValues(InputChoiceOneAutoComplete.class, "choice","autoComplete");
		setInputDefaultValues(InputChoiceOneCombo.class, "choice/one","selectOneMenu");
		setInputDefaultValues(InputChoiceOneList.class, "choice/one","selectOneListbox");
		setInputDefaultValues(InputChoiceOneButton.class, "choice/one","selectOneButton");
		setInputDefaultValues(InputChoiceOneRadio.class, "choice/one","selectOneRadio");
		setInputDefaultValues(InputChoiceOneCascadeList.class, "choice/one","multiSelectListbox");
		
		setInputDefaultValues(InputChoiceManyAutoComplete.class, "choice","autoComplete");
		Properties.setDefaultValues(InputChoiceManyAutoComplete.class, new Object[]{Properties.MULTIPLE, Boolean.TRUE});
		setInputDefaultValues(InputChoiceManyButton.class, "choice/many","selectManyButton");
		setInputDefaultValues(InputChoiceManyCheck.class, "choice/many","selectManyCheckbox");
		setInputDefaultValues(InputChoiceManyList.class, "choice/many","selectManyMenu");
		setInputDefaultValues(InputChoiceManyCombo.class, "choice/many","selectCheckboxMenu");
		Properties.setDefaultValues(InputChoiceManyCombo.class, new Object[]{Properties.LABEL, StringHelper.getInstance().getWord("choice", Boolean.TRUE, Boolean.TRUE)});
		setInputDefaultValues(InputChoiceManyPickList.class, "choice/many","pickList");
		Properties.setDefaultValues(InputChoiceManyPickList.class, new Object[]{Properties.SELECT_ITEM_WRAPPABLE, Boolean.FALSE});
		
		setInputDefaultValues(InputFile.class, "file","fileUpload");
		Properties.setDefaultValues(InputFile.class, new Object[]{Properties.MODE,"simple",Properties.PREVIEWABLE,Boolean.TRUE,Properties.CLEARABLE,Boolean.TRUE
				,Properties.SKIN_SIMPLE,Boolean.TRUE});
		
		Form.Detail.Builder.Target.Adapter.Default.DEFAULT_CLASS = (Class<? extends Target<?, ?, ?, ?>>) ClassHelper.getInstance().getByName(FormBuilderBasedOnDynamicForm.class);
	
		BeanListener.COLLECTION.add(new BeanListener.Adapter(){
			private static final long serialVersionUID = 1L;

			@Override
			public void propertiesMapInstanciated(AbstractBean instance,Properties properties) {
				super.propertiesMapInstanciated(instance,properties);
				if(instance instanceof Component){
					properties.setWidgetVar(RandomHelper.getInstance().getAlphabetic(10));
					properties.setIdentifierAsStyleClass(RandomHelper.getInstance().getAlphabetic(10));
					properties.addString(Properties.STYLE_CLASS, (String)properties.getIdentifierAsStyleClass());
					properties.setDir("LTR");
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
						public Object execute(Properties properties, Object key,Object value, Object nullValue) {
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
					
					Image image = new Image();
					image.getPropertiesMap().setStream(Boolean.FALSE);
					if(LOGO_FILE!=null)
						image.getPropertiesMap().setValue(new ByteArrayContent(LOGO_FILE.getBytes(), LOGO_FILE.getMime()));
					properties.setImageComponent(image);
					
					Menu menu = (Menu) properties.getMainMenu();
					if(menu==null){
						properties.setMainMenu(menu = Menu.build(null, Menu.Type.MAIN));
					}
					if(menu.getBuilt()==null)
						menu.build();
					
				}else if(instance instanceof Form.Master){
					Form.Master form = (Form.Master) instance;
					form.getSubmitCommand().getPropertiesMap().addString(Properties.PROCESS, "@this"); 
					//setProcess("@this "+form.getDetail().getPropertiesMap().getIdentifier());
					//form.getSubmitCommand().getPropertiesMap().setProcess("@this "+form.getDetail().getPropertiesMap().getIdentifier());
					//form.getSubmitCommand().getPropertiesMap().setUpdate(form.getDetail().getPropertiesMap().getIdentifier());
					form.getSubmitCommand().getPropertiesMap().setType("submit");
					form.getSubmitCommand().getPropertiesMap().setPartialSubmit(Boolean.TRUE);
					//System.out.println("PrimefacesResourcesManager.initialize()");
					//setInteractivityBlocker(form, Boolean.TRUE);
				}else if(instance instanceof InputChoice<?>){
					properties.setConverter(new ObjectIdentifierConverter());
					
					if(instance instanceof InputChoiceManyPickList){
						if(((InputChoiceManyPickList)instance).getChoices().getElements() == null)
							((InputChoiceManyPickList)instance).getChoices().setElements(new ArrayList<Object>());
						if(((InputChoiceManyPickList)instance).getValue() == null)
							((InputChoiceManyPickList)instance).setValue(new ArrayList<Object>());
						((InputChoiceManyPickList)instance).setValueObject(new DualListModel<Object>((List<Object>) ((InputChoiceManyPickList)instance).getChoices().getElements()
								, (List<Object>) ((InputChoiceManyPickList)instance).getValue()));
					}else if(instance instanceof InputChoiceOneAutoComplete){
						properties.setConverter(new ObjectLabelConverter());
					}else if(instance instanceof InputChoiceManyAutoComplete){
						properties.setConverter(new ObjectLabelConverter());
					}
					
				}else if(instance instanceof InputFile){
					Image previewImage = (Image) properties.getPreviewImageComponent();
					previewImage.getPropertiesMap().setValue("/"+UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_CONTEXT+"/javax.faces.resource/images/icons/128/file.png.jsf?ln=org.cyk.ui.web.primefaces.resources").setWidth("100px").setHeight("100px");
					
					properties.setOnChange(JavaScriptHelper.getInstance().getFunctionCallPreview("this",JavaScriptHelper.getInstance()
							.formatParameterString(previewImage.getPropertiesMap().getIdentifierAsStyleClass())
							,JavaScriptHelper.getInstance().formatParameterString(properties.getIdentifierAsStyleClass()))
							+JavaScriptHelper.getInstance().getFunctionCallSetAttribute(
									JavaScriptHelper.getInstance().formatParameterString(((Command)properties.getClearCommand()).getPropertiesMap().getIdentifierAsStyleClass())
									,"'style'","'visibility : visible'"
							)
							);
					
					((Command)properties.getClearCommand()).getLabel().getPropertiesMap().setRendered(Boolean.FALSE);
					((Command)properties.getClearCommand()).getPropertiesMap().setIcon("fa fa-eraser");
					((Command)properties.getClearCommand()).getPropertiesMap().setType("button");
					((Command)properties.getClearCommand()).getPropertiesMap().setStyle("visibility: hidden");
					((Command)properties.getClearCommand()).getPropertiesMap().setOnClick(JavaScriptHelper.getInstance().getFunctionCallResetInputFile(
							JavaScriptHelper.getInstance().formatParameterString(properties.getIdentifierAsStyleClass())
							,JavaScriptHelper.getInstance().formatParameterString(previewImage.getPropertiesMap().getIdentifierAsStyleClass())
							,JavaScriptHelper.getInstance().formatParameterString(previewImage.getPropertiesMap().getValue()))
							
							+JQueryHelper.getInstance().getHide(JavaScriptHelper.OBJECT_THIS)
							
							);
					
					Image image = (Image) properties.getImageComponent();
					image.getPropertiesMap().setWidth(previewImage.getPropertiesMap().getWidth());
					image.getPropertiesMap().setHeight(previewImage.getPropertiesMap().getHeight());
					
					((Command)properties.getRemoveCommand()).getLabel().getPropertiesMap().setRendered(Boolean.FALSE);
					((Command)properties.getRemoveCommand()).getPropertiesMap().setIcon("fa fa-trash");
					((Command)properties.getRemoveCommand()).getPropertiesMap().setUpdate("currentimagepanel currentimageremovecommandpanel");
					//((Command)properties.getRemoveCommand()).getPropertiesMap().setUpdate("@(."+image.getPropertiesMap().getIdentifierAsStyleClass()+")");
					
				}else if(instance instanceof InputNumber){
					
				}else if(instance instanceof Menu){
					
				}else if(instance instanceof OutputFile){
					((Image)properties.getThumbnail()).getPropertiesMap().setStream(Boolean.FALSE);
					((Image)properties.getThumbnail()).getPropertiesMap().setWidth("150px");
					((Image)properties.getThumbnail()).getPropertiesMap().setHeight("150px");
				}
				
				if(instance instanceof Input){
					properties.setGetter(Properties.REQUIRED, new Properties.Getter() {
					@Override
					public Object execute(Properties properties, Object key,Object value, Object nullValue) {
						return  !Boolean.TRUE.equals(RequestHelper.getInstance().getParameterInputValueIsNotRequired()) && Boolean.TRUE.equals(value);
					}
				});
				}
			}
		});
		
		/*Component.Listener.COLLECTION.add(new Component.Listener.Adapter(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object build(Component component) {
				if(component instanceof Form.Detail)
					return Form.Detail.buildTarget((Form.Detail) component);
				if(component instanceof Menu)
					return new MenuBasedOnMenuModel().setInput((Menu) component).execute();
				if(component instanceof Hierarchy)
					return new HierarchyBasedOnTreeNode().setInput((Hierarchy) component).execute();
				return super.build(component);
			}
			
		});*/
		
		Window.Listener.COLLECTION.add(new Window.Listener.Adapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public Object createLayout(Window window) {
				NorthEastSouthWestCenter layout = new NorthEastSouthWestCenter(window);
				return layout;
			}
		});
		
		logInfo("CYK Primefaces Resources Manager has been initialized");
	}
	
	public void initializeContext(ServletContextEvent servletContextEvent){
		initialize();
	}
	
	public void destroyContext(ServletContextEvent servletContextEvent){
		
	}
		
	public static final String COMPONENT_TEMPLATE_FORMAT = "/org.cyk.ui.web.primefaces.resources/template/decorate/%s/%s/%s.xhtml";
	public static final String COMPONENT_INCLUDE_FORMAT = "/org.cyk.ui.web.primefaces.resources/include/%s/%s/%s/default.xhtml";
	
	protected static <COMPONENT extends Component> void setComponentTemplateAndIncludeDefaultValues(Class<COMPONENT> aClass,String family,String relativePath,String fileName){
		Properties.setDefaultValues(aClass, new Object[]{
				Properties.TEMPLATE, String.format(COMPONENT_TEMPLATE_FORMAT, family,relativePath,fileName)
				, Properties.INCLUDE, String.format(COMPONENT_INCLUDE_FORMAT, family,relativePath,fileName)
				, Properties.TEMPLATE_WITHOUT_IDENTIFIER, String.format(COMPONENT_TEMPLATE_FORMAT, family,relativePath,fileName+"WithoutIdentifier")
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
	
	public String getDummyEmptyContentDefaultTemplate(){
		return "/org.cyk.ui.web.primefaces.resources/include/dummyemptycontent.xhtml";
	}
	
	public Object getTemplate(Component component){
		return component == null ? getDummyEmptyContentDefaultTemplate() 
				: StringUtils.defaultIfBlank((String)component.getPropertiesMap().getTemplate(), getDummyEmptyContentDefaultTemplate());
	}
	
	public Object getTemplateWithoutIdentifier(Component component){
		return component == null ? getDummyEmptyContentDefaultTemplate() 
				: StringUtils.defaultIfBlank((String)component.getPropertiesMap().getTemplateWithoutIdentifier(), getDummyEmptyContentDefaultTemplate());
	}
	
	public Object getImageDefaultTemplate(){
		return Properties.getDefaultValue(Image.class, Properties.TEMPLATE);
	}
	
	public Object getWatermarkDefaultTemplate(){
		return Properties.getDefaultValue(Watermark.class, Properties.TEMPLATE);
	}
	
	public Object getCommandDefaultTemplate(){
		return Properties.getDefaultValue(Command.class, Properties.TEMPLATE);
	}
	
	public Object getInputTextDefaultTemplate(){
		return Properties.getDefaultValue(InputText.class, Properties.TEMPLATE);
	}
	
	public Object getInputTextDefaultTemplateWithoutIdentifier(){
		return Properties.getDefaultValue(InputText.class, Properties.TEMPLATE_WITHOUT_IDENTIFIER);
	}
	
	public Object getInputTextareaDefaultTemplate(){
		return Properties.getDefaultValue(InputTextarea.class, Properties.TEMPLATE);
	}
	
	public Object getInputTextareaDefaultTemplateWithoutIdentifier(){
		return Properties.getDefaultValue(InputTextarea.class, Properties.TEMPLATE_WITHOUT_IDENTIFIER);
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
	
	public Object getInputNumberDefaultTemplateWithoutIdentifier(){
		return Properties.getDefaultValue(InputNumber.class, Properties.TEMPLATE_WITHOUT_IDENTIFIER);
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
	
	public Object getInputChoiceOneComboDefaultTemplateWithoutIdentifier(){
		return Properties.getDefaultValue(InputChoiceOneCombo.class, Properties.TEMPLATE_WITHOUT_IDENTIFIER);
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
	
	public Object getOutputFileDefaultTemplate(){
		return Properties.getDefaultValue(OutputFile.class, Properties.TEMPLATE);
	}
	
	public Object getOutputTextDefaultTemplate(){
		return Properties.getDefaultValue(OutputText.class, Properties.TEMPLATE);
	}
	
	public Object getOutputLinkDefaultTemplate(){
		return Properties.getDefaultValue(OutputLink.class, Properties.TEMPLATE);
	}
	
	public Object getMenuTemplate(Menu menu){
		Object template = menu.getPropertiesMap().getTemplate();
		if(template==null){
			Menu.RenderType renderType = InstanceHelper.getInstance().getIfNotNullElseDefault(menu.getRenderType(),Menu.RenderType.DEFAULT);
			switch(renderType){
			case BAR:template = getMenuBarDefaultTemplate();break;
			case BREAD_CRUMB:template = "/org.cyk.ui.web.primefaces.resources/template/decorate/menu/breadCrumb.xhtml";break;
			case PANEL:template = "/org.cyk.ui.web.primefaces.resources/template/decorate/menu/panelMenu.xhtml";break;
			case PLAIN:template = "/org.cyk.ui.web.primefaces.resources/template/decorate/menu/menu.xhtml";break;
			case SLIDE:template = "/org.cyk.ui.web.primefaces.resources/template/decorate/menu/slideMenu.xhtml";break;
			case TAB:template = "/org.cyk.ui.web.primefaces.resources/template/decorate/menu/tabMenu.xhtml";break;
			}
		}
		return template;
	}
	
	public Object getSlideMenuDefaultTemplate(){
		return "/org.cyk.ui.web.primefaces.resources/template/decorate/menu/slideMenu.xhtml";
	}
	
	public Object getTabMenuDefaultTemplate(){
		return "/org.cyk.ui.web.primefaces.resources/template/decorate/menu/tabMenu.xhtml";
	}
	
	public Object getMenuBarDefaultTemplate(){
		return "/org.cyk.ui.web.primefaces.resources/template/decorate/menu/menubar.xhtml";
	}
	
	public Object getHierarchyTemplate(Hierarchy hierarchy){
		Object template = hierarchy.getPropertiesMap().getTemplate();
		if(template==null){
			Hierarchy.RenderType renderType = InstanceHelper.getInstance().getIfNotNullElseDefault(hierarchy.getRenderType(),Hierarchy.RenderType.DEFAULT);
			switch(renderType){
			case TREE:template = getTreeDefaultTemplate();break;
			case TABLE:template = getTreeTableDefaultTemplate();break;
			}
		}
		return template;
	}
	
	public Object getTreeDefaultTemplate(){
		return "/org.cyk.ui.web.primefaces.resources/template/decorate/hierarchy/tree/tree.xhtml";
	}
	
	public Object getTreeTableDefaultTemplate(){
		return "/org.cyk.ui.web.primefaces.resources/template/decorate/hierarchy/treeTable.xhtml";
	}
	
	public Object getHierarchyInclude(Hierarchy hierarchy){
		Object include = hierarchy.getPropertiesMap().getTemplate();
		if(include==null){
			Hierarchy.RenderType renderType = InstanceHelper.getInstance().getIfNotNullElseDefault(hierarchy.getRenderType(),Hierarchy.RenderType.DEFAULT);
			switch(renderType){
			case TREE:include = getTreeDefaultInclude();break;
			case TABLE:include = getTreeTableDefaultInclude();break;
			}
		}
		return include;
	}
	
	public Object getTreeDefaultInclude(){
		return "/org.cyk.ui.web.primefaces.resources/include/hierarchy/tree/default.xhtml";
	}
	
	public Object getTreeNodeDefaultInclude(){
		return "/org.cyk.ui.web.primefaces.resources/include/hierarchy/tree/node/default.xhtml";
	}
	
	public Object getTreeTableDefaultInclude(){
		return "/org.cyk.ui.web.primefaces.resources/include/hierarchy/treeTable/default.xhtml";
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
	
	public static void setInteractivityBlocker(Form.Master form,Boolean global){
		setInteractivityBlocker(form, form.getSubmitCommand(), (String)form.getDetail().getPropertiesMap().getIdentifier(), global);
		/*form.getSubmitCommand().getPropertiesMap().setGlobal(global);
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
		*/
	}
	
	public static void setInteractivityBlocker(Component.Visible visible,Command command,String identifier,Boolean global){
		command.getPropertiesMap().setGlobal(global);
		if(Boolean.TRUE.equals(global)){
			visible.getPropertiesMap().setInteractivityBlocker(null);
		}else {
			setInteractivityBlockerNotGlobal(visible, command, identifier);
			/*
			visible.getPropertiesMap().setInteractivityBlocker(new InteractivityBlocker());
			command.getPropertiesMap().setOnStart(PrimefacesJavascriptHelper.getInstance()
					.getMethodCallBlock(visible.getPropertiesMap().getInteractivityBlocker()));
			command.getPropertiesMap().setOnComplete(PrimefacesJavascriptHelper.getInstance()
					.getMethodCallUnBlock(visible.getPropertiesMap().getInteractivityBlocker()));
			
			((InteractivityBlocker)visible.getPropertiesMap().getInteractivityBlocker()).getPropertiesMap().setSource(command.getPropertiesMap().getIdentifier());
			((InteractivityBlocker)visible.getPropertiesMap().getInteractivityBlocker()).getPropertiesMap().setTarget(identifier);
			*/
		}
	}
	
	public static void setInteractivityBlockerNotGlobal(Component component,Component command,String identifier){
		InteractivityBlocker interactivityBlocker = new InteractivityBlocker();
		interactivityBlocker.getPropertiesMap().setSource(command.getPropertiesMap().getIdentifier());
		interactivityBlocker.getPropertiesMap().setTarget(identifier);
		
		component.getPropertiesMap().setInteractivityBlocker(interactivityBlocker);
		
		command.getPropertiesMap().setOnStart(PrimefacesJavascriptHelper.getInstance().getMethodCallBlock(interactivityBlocker));
		command.getPropertiesMap().setOnComplete(PrimefacesJavascriptHelper.getInstance().getMethodCallUnBlock(interactivityBlocker));
	}
	
	public static void setInteractivityBlockerNotGlobal(Component component,Component command){
		setInteractivityBlockerNotGlobal(component, command, (String)component.getPropertiesMap().getIdentifier());
	}

	/**/
	
	public Boolean isInputText(Input<?> input){
		return input!=null && input.getClass().equals(InputText.class);
	}
	
	public Boolean isInputTextarea(Input<?> input){
		return input!=null && input.getClass().equals(InputTextarea.class);
	}
	
	public Boolean isInputNumber(Input<?> input){
		return input instanceof InputNumber;
	}
	
	public Boolean isInputChoiceOneCombo(Input<?> input){
		return input!=null && input.getClass().equals(InputChoiceOneCombo.class);
	}
}
