package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.ui.web.primefaces.resources.page.Page;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.cdi.BeanListener;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.NotificationHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.command.Command;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail.Builder.Target;
import org.cyk.utility.common.userinterface.event.Confirm;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.output.OutputText;
import org.cyk.utility.common.userinterface.panel.ConfirmationDialog;
import org.cyk.utility.common.userinterface.panel.Dialog;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@SuppressWarnings("unchecked")
@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class Startup implements Serializable {

	private static final long serialVersionUID = 1L;

	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		
		Properties.setDefaultValue(Page.class, Properties.TEMPLATE, Page.TEMPLATE);
		Properties.setDefaultValue(OutputText.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces/template/decorate/outputText.xhtml");
		
		Properties.setDefaultValue(Form.Master.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces/template/decorate/form.xhtml");
		Properties.setDefaultValue(Form.Master.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces/include/form/default.xhtml");
		
		Properties.setDefaultValue(Form.Detail.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces/template/decorate/dynaForm.xhtml");
		Properties.setDefaultValue(Form.Detail.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces/include/dynaForm/default.xhtml");
		
		Properties.setDefaultValue(Command.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces/template/decorate/command/commandButton.xhtml");
		Properties.setDefaultValue(Command.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces/include/command/commandButton/default.xhtml");
		Properties.setDefaultValue(Command.class, Properties.AJAX, Boolean.TRUE);
		
		Properties.setDefaultValue(Confirm.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces/template/decorate/event/confirm.xhtml");
		Properties.setDefaultValue(Confirm.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces/include/event/confirm/default.xhtml");
		Properties.setDefaultValue(Confirm.class, Properties.DISABLED, Boolean.FALSE);
		
		Properties.setDefaultValue(Dialog.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces/template/decorate/panel/dialog/dialog.xhtml");
		Properties.setDefaultValue(Dialog.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces/include/panel/dialog/default.xhtml");
		
		
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces/template/decorate/panel/dialog/confirmation/confirmationDialog.xhtml");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces/include/panel/dialog/confirmation/confirmationDialog/default.xhtml");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.DISABLED, Boolean.FALSE);
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.GLOBAL, Boolean.TRUE);
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.HEADER, "Your Confirmation Dialog Header");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.MESSAGE, "Your Confirmation Dialog Message");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.SEVERITY, "alert");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.WIDTH, "auto");
		Properties.setDefaultValue(ConfirmationDialog.class, Properties.HEIGHT, "auto");
		
		Properties.setDefaultValue(InputText.class, Properties.TEMPLATE, "/org.cyk.ui.web.primefaces/template/decorate/input/text/oneline/inputText.xhtml");
		Properties.setDefaultValue(InputText.class, Properties.INCLUDE, "/org.cyk.ui.web.primefaces/include/input/text/oneline/default.xhtml");
		
		Form.Detail.Builder.Target.Adapter.Default.DEFAULT_CLASS = (Class<? extends Target<?, ?, ?, ?>>) ClassHelper.getInstance().getByName(FormBuilderBasedOnDynamicForm.class);
	
		BeanListener.COLLECTION.add(new BeanAdapter(){
			@Override
			public void propertiesMapInstanciated(AbstractBean instance) {
				super.propertiesMapInstanciated(instance);
				if(instance instanceof ConfirmationDialog){
					((ConfirmationDialog)instance).getYesCommand().setLabelFromIdentifier(StringHelper.getInstance().get("yes", new Object[]{}))
						.getPropertiesMap().setType("button");
					((ConfirmationDialog)instance).getYesCommand().getPropertiesMap().setStyleClass("ui-confirmdialog-yes");
					((ConfirmationDialog)instance).getYesCommand().getPropertiesMap().setIcon("ui-icon-check");
					
					((ConfirmationDialog)instance).getNoCommand().setLabelFromIdentifier(StringHelper.getInstance().get("no", new Object[]{}))
						.getPropertiesMap().setType("button");
					((ConfirmationDialog)instance).getNoCommand().getPropertiesMap().setStyleClass("ui-confirmdialog-no");
					((ConfirmationDialog)instance).getNoCommand().getPropertiesMap().setIcon("ui-icon-close");

				}
			}
		});
		
		NotificationHelper.Notification.Viewer.Adapter.Default.DEFAULT_CLASS = (Class<NotificationHelper.Notification.Viewer>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.resources.NotificationHelper.Viewer.class);
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
			return "InputText";
		}
		
	}
}
