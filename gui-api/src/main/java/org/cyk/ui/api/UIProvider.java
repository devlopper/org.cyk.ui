package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindTextResult;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.command.AbstractCommandable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.InputBooleanButton;
import org.cyk.ui.api.data.collector.control.InputBooleanCheck;
import org.cyk.ui.api.data.collector.control.InputCalendar;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.control.InputEditor;
import org.cyk.ui.api.data.collector.control.InputFile;
import org.cyk.ui.api.data.collector.control.InputManyAutoComplete;
import org.cyk.ui.api.data.collector.control.InputManyButton;
import org.cyk.ui.api.data.collector.control.InputManyCheck;
import org.cyk.ui.api.data.collector.control.InputManyCheckCombo;
import org.cyk.ui.api.data.collector.control.InputManyCombo;
import org.cyk.ui.api.data.collector.control.InputManyPickList;
import org.cyk.ui.api.data.collector.control.InputNumber;
import org.cyk.ui.api.data.collector.control.InputOneAutoComplete;
import org.cyk.ui.api.data.collector.control.InputOneButton;
import org.cyk.ui.api.data.collector.control.InputOneCascadeList;
import org.cyk.ui.api.data.collector.control.InputOneCombo;
import org.cyk.ui.api.data.collector.control.InputOneList;
import org.cyk.ui.api.data.collector.control.InputOneRadio;
import org.cyk.ui.api.data.collector.control.InputPassword;
import org.cyk.ui.api.data.collector.control.InputText;
import org.cyk.ui.api.data.collector.control.InputTextarea;
import org.cyk.ui.api.data.collector.control.OutputImage;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.ui.api.data.collector.control.OutputSeparator;
import org.cyk.ui.api.data.collector.control.OutputText;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter @Singleton @Named("uiProvider") @Deployment(initialisationType=InitialisationType.EAGER) 
public class UIProvider extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4968509852930057567L;

	private static UIProvider INSTANCE;
	
	private Package controlBasePackage = getClass().getPackage();
	private Class<? extends UICommandable> commandableClass;
	private Collection<Listener<?,?,?,?,?>> uiProviderListeners = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this; 
		super.initialisation();
	}
	
	public Boolean isFile(Field field){
		return field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class)!=null;
	}
	
	public Boolean isImage(Object data,Field field){
		org.cyk.utility.common.annotation.user.interfaces.InputFile annotation = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class);
		Object value = commonUtils.readField(data, field, Boolean.FALSE);
		if(value instanceof File){
			return inject(FileBusiness.class).isImage((File) value);
		}
		return annotation.extensions().groups().length==1 && FileExtensionGroup.IMAGE.equals(annotation.extensions().groups()[0]);
	}
	
	public Boolean isShowFileLink(Object data,Field field){
		org.cyk.utility.common.annotation.user.interfaces.InputFile annotation = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class);
		return annotation.showLink();
	}
		
	public Control<?,?,?,?,?> createFieldControl(Object data,Field field){
		final Control<?,?,?,?,?> control = createControlInstance(controlClass(field));
		/*

		*/
		if(control instanceof Input<?,?,?,?,?,?>){
			Input<?,?,?,?,?,?> input = (Input<?,?,?,?,?,?>)control;
			input.setObject(data);
			if(StringUtils.isBlank(input.getLabel())){
				FindTextResult findTextResult = UIManager.getInstance().getLanguageBusiness().findFieldLabelText(field);
				input.setLabel(findTextResult.getValue());
				input.setUniqueCssClass(CascadeStyleSheet.generateUniqueClassFrom(input.getType(), findTextResult));
			}
			input.setField(field);
			Size size = field.getAnnotation(Size.class);
			try {
				FieldUtils.writeField(control, "value", FieldUtils.readField(field, data, Boolean.TRUE), Boolean.TRUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			input.setReadOnlyValue(readOnlyValue(field, data));
			input.setRequired(field.getAnnotation(NotNull.class)!=null);
			
			if(Boolean.TRUE.equals(input.getRequired()))
				input.setRequiredMessage(UIManager.getInstance().getLanguageBusiness().findText("input.value.required", new Object[]{input.getLabel()}));
			
			if(input instanceof InputCalendar<?,?,?,?,?>){
				InputCalendar<?, ?, ?, ?, ?> calendar = (InputCalendar<?, ?, ?, ?, ?>) input;
				calendar.setPattern(UIManager.getInstance().getTimeBusiness().findFormatPattern(field));
				if(size==null){
					
				}else{
					
				}
					
			}else if(control instanceof InputChoice<?,?,?,?,?,?>){
				@SuppressWarnings("unchecked")
				InputChoice<?,?,?,?,?,Object> inputChoice = (InputChoice<?,?,?,?,?,Object>)control;
				org.cyk.utility.common.annotation.user.interfaces.InputChoice choiceAnnotation = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputChoice.class);
				org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete autoCompleteAnnotation =  field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete.class);
				Boolean loadChoices = choiceAnnotation!= null && choiceAnnotation.load()/* && autoCompleteAnnotation==null*/;
				
				if(Boolean.TRUE.equals(loadChoices)){
					for(Listener<?,?,?,?,?> listener : uiProviderListeners)
						listener.choices(inputChoice,data,field, (List<Object>) inputChoice.getList());
				}else if(autoCompleteAnnotation!=null) {
					org.cyk.ui.api.data.collector.control.InputAutoCompleteCommon<?> inputAutoCompleteCommon = null;
					if(inputChoice instanceof InputOneAutoComplete)
						inputAutoCompleteCommon = ((InputOneAutoComplete<?, ?, ?, ?, ?, ?, ?>)inputChoice).getCommon();
					else if(inputChoice instanceof InputManyAutoComplete)
						inputAutoCompleteCommon = ((InputManyAutoComplete<?, ?, ?, ?, ?, ?,?>)inputChoice).getCommon();
					if(inputAutoCompleteCommon!=null){
						
					}
					
				}
				inputChoice.setFiltered(Boolean.TRUE);
				
			}else if(control instanceof InputFile){
				InputFile<?,?,?,?,?> inputFile = (InputFile<?,?,?,?,?>)control;
				org.cyk.utility.common.annotation.user.interfaces.InputFile annotation = inputFile.getField().getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class);				
				inputFile.setPreviewable(isImage(data,inputFile.getField()));
				
				if(annotation.extensions().values().length==0)
					for(FileExtensionGroup group : annotation.extensions().groups())
						inputFile.getExtensions().addAll(group.getExtensions());
				inputFile.setMinimumSize(annotation.size().from().integer());
				inputFile.setMaximumSize(annotation.size().to().integer());
				inputFile.setInitializedValue(inputFile.getValue());
			}else if(control instanceof InputNumber){
				InputNumber<?, ?, ?, ?, ?> number = (InputNumber<?, ?, ?, ?, ?>) input;
				if(size==null){
					
				}else{
					number.setMinimum(size.min());
					number.setMaximum(size.max());
				}
			}
		}
		listenerUtils.execute(uiProviderListeners, new ListenerUtils.VoidMethod<Listener<?,?,?,?,?>>() {
			@Override
			public void execute(Listener<?, ?, ?, ?, ?> listener) {
				listener.controlCreated(control);
			}
		});
		return control;
	}
	 
	public OutputLabel<?,?,?,?,?> createLabel(String value){
		OutputLabel<?,?,?,?,?> outputLabel = (OutputLabel<?, ?, ?, ?, ?>) createControlInstance(controlClass(OutputLabel.class));
		outputLabel.setValue(value);
		return outputLabel;
	}
	
	public OutputSeparator<?,?,?,?,?> createSeparator(String value){
		OutputSeparator<?,?,?,?,?> outputSeperator = (OutputSeparator<?, ?, ?, ?, ?>) createControlInstance(controlClass(OutputSeparator.class));
		outputSeperator.setValue(value);
		return outputSeperator;
	}
	
	public OutputText<?,?,?,?,?> createOutputText(String value){
		OutputText<?,?,?,?,?> outputText = (OutputText<?, ?, ?, ?, ?>) createControlInstance(controlClass(OutputText.class));
		outputText.setValue(value);
		return outputText;
	}
	
	public OutputImage<?,?,?,?,?> createOutputImage(File value){
		OutputImage<?,?,?,?,?> outputImage = (OutputImage<?, ?, ?, ?, ?>) createControlInstance(controlClass(OutputImage.class));
		outputImage.setValue(value);
		return outputImage;
	}
	
	public UICommandable createCommandable(AbstractCommandable.Builder<? extends AbstractCommandable> builder){
		UICommandable commandable = builder.build();
		for(Listener<?,?,?,?,?> listener : uiProviderListeners)
			listener.commandableInstanceCreated(commandable);
		return commandable;
	}
	
	@Deprecated
	public UICommandable createCommandable(CommandListener commandListener,String labelId,Icon icon,EventListener anExecutionPhase,ProcessGroup aProcessGroup){
		Class<? extends UICommandable> commandableClass = this.commandableClass;
		for(Listener<?,?,?,?,?> listener : uiProviderListeners){
			Class<? extends UICommandable> c = listener.commandableClassSelected(commandableClass);
			if(c!=null)
				commandableClass = c; 
		}
		UICommandable commandable = newInstance(commandableClass);
		commandable.setCommand(new DefaultCommand());
		commandable.getCommand().setMessageManager(MessageManager.INSTANCE);
		commandable.setLabel(UIManager.getInstance().text(labelId));
		commandable.setIcon(icon);
		commandable.setEventListener(anExecutionPhase);
		commandable.setProcessGroup(aProcessGroup);
		if(commandListener!=null)
			commandable.getCommand().getCommandListeners().add(commandListener);
		for(Listener<?,?,?,?,?> listener : uiProviderListeners)
			listener.commandableInstanceCreated(commandable);

		return commandable;
	}
	/*
	public UICommandable createCommandable(String labelId,IconType iconType){
		return createCommandable(null, labelId, iconType, null, null);
	}*/
	/*
	public UICommandable createCommandable(String labelId,IconType iconType,ViewType viewType){
		UICommandable p = createCommandable(null, labelId, iconType, null, null);
		p.setViewType(viewType);
		return p;
	}
	
	public UICommandable createCommandable(String labelId,IconType iconType,Object viewId){
		UICommandable p = createCommandable(null, labelId, iconType, null, null);
		p.setViewId(viewId);
		if(viewId != null)
			p.setCommandRequestType(CommandRequestType.UI_VIEW);
		return p;
	}
	*/
	/* */
	
	public Control<?,?,?,?,?> createControlInstance(Class<? extends Control<?,?,?,?,?>> controlClass){
		for(Listener<?,?,?,?,?> listener : uiProviderListeners){
			Class<? extends Control<?,?,?,?,?>> c = listener.controlClassSelected(controlClass);
			if(c!=null)
				controlClass = c; 
		}
		
		Control<?,?,?,?,?> control;
		try {
			control = (Control<?,?,?,?,?>) controlClass.newInstance();
			
			for(Listener<?,?,?,?,?> listener : uiProviderListeners)
				listener.controlInstanceCreated(control);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return control;
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends Control<?,?,?,?,?>> controlClass(Field field){
		Class<?> controlInterface = null;
		if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputText.class)!=null){
			controlInterface = InputText.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputTextarea.class)!=null){
			controlInterface = InputTextarea.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputCalendar.class)!=null){
			controlInterface = InputCalendar.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputEditor.class)!=null){
			controlInterface = InputEditor.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton.class)!=null){
			controlInterface = InputBooleanButton.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputPassword.class)!=null){
			controlInterface = InputPassword.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class)!=null){
			controlInterface = InputFile.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck.class)!=null){
			controlInterface = InputBooleanCheck.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputNumber.class)!=null){
			controlInterface = InputNumber.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneList.class)!=null){
			controlInterface = InputOneList.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneCombo.class)!=null){
			controlInterface = InputOneCombo.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete.class)!=null){
			controlInterface = InputOneAutoComplete.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneButton.class)!=null){
			controlInterface = InputOneButton.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneCascadeList.class)!=null){
			controlInterface = InputOneCascadeList.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneRadio.class)!=null){
			controlInterface = InputOneRadio.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCombo.class)!=null){
			controlInterface = InputManyCombo.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyAutoComplete.class)!=null){
			controlInterface = InputManyAutoComplete.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyPickList.class)!=null){
			controlInterface = InputManyPickList.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCheckCombo.class)!=null){
			controlInterface = InputManyCheckCombo.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCheck.class)!=null){
			controlInterface = InputManyCheck.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyButton.class)!=null){
			controlInterface = InputManyButton.class;
		}else
			return null;
		return controlClass((Class<? extends Control<?,?,?,?,?>>)controlInterface);
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends Control<?,?,?,?,?>> controlClass(@SuppressWarnings("rawtypes") Class<? extends Control> anInterface){
		//return (Class<? extends Control<?, ?, ?, ?, ?>>) Class.forName(controlBasePackage.getName()+"."+anInterface.getSimpleName());
		return (Class<? extends Control<?, ?, ?, ?, ?>>) commonUtils.classFormName(controlBasePackage.getName()+"."+anInterface.getSimpleName());
	}
	
	public String readOnlyValue(Field field,Object object){
		if(object==null)
			return null;
		String value = null;
		for(Listener<?,?,?,?,?> listener : uiProviderListeners){
			String v = listener.readOnlyValue(field, object);
			if(v!=null)
				value = v; 
		}
		return value;
	}
	
	public String formatValue(Field field,Object object){
		if(object==null)
			return null;
		String value = null;
		for(Listener<?,?,?,?,?> listener : uiProviderListeners){
			String v = listener.formatValue(field, object);
			if(v!=null)
				value = v; 
		}
		return value;
	}
	
	public Collection<Class<? extends Annotation>> annotationClasses(){
		Collection<Class<? extends Annotation>> annotations = new ArrayList<>();
		annotations.add(org.cyk.utility.common.annotation.user.interfaces.Input.class);
		annotations.add(org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.class);
		return annotations;
	}
	
	public Boolean isControl(Control<?, ?, ?, ?, ?> control,String interfaceName){
		return control!=null && control.getClass().getSimpleName().equals(interfaceName);
	}
	
	public static UIProvider getInstance() {
		return INSTANCE;
	}
	
	/**/
	
	public static interface Listener<MODEL,ROW,LABEL,CONTROL,SELECTITEM> {

		Class<? extends Control<?,?,?,?,?>> controlClassSelected(Class<? extends Control<?,?,?,?,?>> aClass);
		
		void controlInstanceCreated(Control<?,?,?,?,?> control);
		
		void controlCreated(Control<?,?,?,?,?> control);
		
		void choices(InputChoice<?,?,?,?,?,?> inputChoice,Object data,Field field,List<Object> list);
		
		Class<? extends UICommandable> commandableClassSelected(Class<? extends UICommandable> aClass);
		
		void commandableInstanceCreated(UICommandable aCommandable);
		
		String readOnlyValue(Field field,Object object);
		
		String formatValue(Field field,Object value);
		
		ContentType contentType();
		
		/**/
		
		public static class Adapter<MODEL, ROW, LABEL, CONTROL, SELECTITEM> implements Listener<MODEL, ROW, LABEL, CONTROL, SELECTITEM>,Serializable {

			private static final long serialVersionUID = -1238691206716866866L;

			@Override
			public Class<? extends Control<?, ?, ?, ?, ?>> controlClassSelected(Class<? extends Control<?, ?, ?, ?, ?>> aClass) {
				return null;
			}

			@Override
			public void controlInstanceCreated(Control<?, ?, ?, ?, ?> control) {}

			@Override
			public void controlCreated(Control<?, ?, ?, ?, ?> control) {}
			
			@Override
			public void choices(InputChoice<?, ?, ?, ?, ?, ?> inputChoice,Object data, Field field, List<Object> list) {}

			@Override
			public Class<? extends UICommandable> commandableClassSelected(Class<? extends UICommandable> aClass) {
				return null;
			}

			@Override
			public void commandableInstanceCreated(UICommandable aCommandable) {}

			@Override
			public String readOnlyValue(Field field, Object object) {
				return null;
			}

			@Override
			public String formatValue(Field field, Object value) {
				return null;
			}

			@Override
			public ContentType contentType() {
				return null;
			}
		}

	}

	
}
