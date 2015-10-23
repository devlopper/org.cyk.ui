package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.test.model.MyIdentifiable;
import org.cyk.ui.web.api.AjaxListener;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.api.data.collector.control.WebInputNumber;
import org.cyk.ui.web.api.data.collector.control.WebInputOneCombo;
import org.cyk.ui.web.api.data.collector.control.WebInputText;
import org.cyk.ui.web.api.data.collector.control.WebInputTextarea;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class DynaFormDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private static List<SelectItem> CHOICES = new ArrayList<>();
	static{
		for(int i=1;i<=6;i++)
			CHOICES.add(new SelectItem("Choix "+i));
	}
	
	private MyIdentifiable entityWithAnnotation = new MyIdentifiable();
	private FormOneData<MyIdentifiable> form;
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		form = (FormOneData<MyIdentifiable>) createFormOneData(entityWithAnnotation,Crud.CREATE);

		form.setTitle("Mon Formulaire");
		//form.setFieldsRequiredMessage("Champs obligatoire");
		form.setDynamic(Boolean.TRUE);
		
		form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;
			@Override
			public void serve(UICommand command, Object parameter) {
				super.serve(command, parameter);
				System.out.println(entityWithAnnotation);
			}
		});
		//((Commandable)form.getSubmitCommandable()).getButton().setAjax(Boolean.FALSE);
		
		//form.build();
		/*
		form.setControlSetListener(new ControlSetAdapter(){
			@Override
			public Boolean showFieldLabel(ControlSet controlSet, Field field) {
				return Boolean.FALSE;
			}
		});
		*/
		
		/*
		form.getSelectedFormData().getControlSets().iterator().next().getControlSetListeners().add(new ControlSetAdapter(){
			@Override
			public Boolean showFieldLabel(ControlSet controlSet, Field field) {
				return Boolean.FALSE;
			}
		});
		*/
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		windowHierachyMenu = userSession.getContextualMenu();
		windowHierachyMenu.setRenderType(UIMenu.RenderType.BREAD_CRUMB);
		windowHierachyMenuModel = userSession.getContextualMenuModel();
		
		System.out.println(windowHierachyMenu);
		System.out.println(windowHierachyMenuModel);
		AjaxListener ajaxListener = null;
		/*
		setAjaxListener(form, "textOneLine", "change", new String[]{"textManyLine"},new String[]{"textManyLine","textManyLine2"}, String.class,new ListenValueMethod<String>() {
			@Override
			public void execute(String value) {
				setFieldValue(form,"textManyLine", form.findInputByFieldName("textManyLine").getValue()+" NEW - "+value);
				setFieldValue(form,"textManyLine2", value);
			}
		});
		*/
		ajaxListener = setAjaxListener(form, "number1", "change", new String[]{"number2"},new String[]{"sumResult","multiplyResult"}, BigDecimal.class,new ListenValueMethod<BigDecimal>() {
			@Override
			public void execute(BigDecimal value) {
				setFieldValue(form,"sumResult", value.add(bigDecimalValue(form, "number2")));
				setFieldValue(form,"multiplyResult", value.multiply(bigDecimalValue(form, "number2")));
			}
		});
		
		setAjaxListener(form, "number2", "change", new String[]{"number1"}, new String[]{"sumResult","multiplyResult"},BigDecimal.class,new ListenValueMethod<BigDecimal>() {
			@Override
			public void execute(BigDecimal value) {
				setFieldValue(form,"sumResult", value.add(bigDecimalValue(form, "number1")));
				setFieldValue(form,"multiplyResult", value.multiply(bigDecimalValue(form, "number1")));
			}
		});
		
		ajaxListener = setAjaxListener(form, "canSum", "change", null, null,Boolean.class,new ListenValueMethod<Boolean>() {
			@Override
			public void execute(Boolean value) {
				//form.findInputByFieldName("sumResult").setDisabled(value);
				//form.findInputByClassByFieldName(WebInput.class, "sumResult").getReadOnlyValueCss().addInline("background-color:red !important;");
				//setFieldValue(form,"sumResult", value?BigDecimal.ONE:BigDecimal.ZERO);
				//setFieldValue(form,"textManyLine2", "Can Sum : "+value);
				//Ajax.oncomplete(javaScriptHelper.hide("."+form.findInputByClassByFieldName(WebInput.class, "sumResult").getUniqueCssClass()));
				//System.out.println(javaScriptHelper.hide("."+form.findInputByClassByFieldName(WebInput.class, "sumResult").getUniqueCssClass()));
				//System.out.println("$('."+form.findInputByClassByFieldName(WebInput.class, "sumResult").getUniqueCssClass()+"').hide();");
				onComplete(inputRowVisibility(form,"sumResult",value),inputRowVisibility(form,"multiplyResult",value));
				/*
				if(Boolean.TRUE.equals(value))
					rowVisibility(form.findInputByClassByFieldName(WebInput.class, "sumResult"), "show")
					Ajax.oncomplete("$('."+form.findInputByClassByFieldName(WebInput.class, "sumResult").getUniqueCssClass()+"').closest('tr').show();");
				else
					Ajax.oncomplete("$('."+form.findInputByClassByFieldName(WebInput.class, "sumResult").getUniqueCssClass()+"').closest('tr').hide();");
					*/
			}
		});
		
		//form.findInputByClassByFieldName(WebInputText.class, "textOneLine").setOnClick("alert('They clicked me!!!');");
		//form.findInputByClassByFieldName(WebInputText.class, "textOneLine").setOnChange("alert('We on changed.');");
		/*
		form.findInputByClassByFieldName(WebInputText.class, "textOneLine").setWidgetVar("aazzeerrttyy");
		form.findInputByClassByFieldName(WebInputTextarea.class, "textManyLine").setWidgetVar("tml");
		form.findInputByClassByFieldName(WebInputNumber.class, "number1").setWidgetVar("wv1");
		form.findInputByClassByFieldName(WebInputNumber.class, "number2").setWidgetVar("wv2");
		*/
		//form.findInputByClassByFieldName(WebInputText.class, "textOneLine")
		//	.setOnChange("document.getElementById(PF('tml').id).value=document.getElementById(PF('aazzeerrttyy').id).value;");
		
		//form.findInputByClassByFieldName(WebInputNumber.class, "number1").setOnChange("alert(document.getElementById(PF('wv1').id).value);");
		
		//form.findInputByClassByFieldName(WebInputOneCombo.class, "myEnum").setFiltered(Boolean.TRUE);
		
		/*
		form.addChoices("inputOneList",CHOICES);
		form.addChoices("inputOneCombo",CHOICES);
		form.addChoices("inputOneButton",CHOICES);
		form.addChoices("inputOneCascadeList",CHOICES);
		form.addChoices("inputOneRadio",CHOICES);
		
		form.addChoices("inputManyCombo",CHOICES);
		form.addChoices("inputManyPickList",CHOICES);
		form.addChoices("inputManyButton",CHOICES);
		form.addChoices("inputManyCheck",CHOICES);
		form.addChoices("inputManyCheckCombo",CHOICES);
		*/
		
		
	}
	
	

	
}
