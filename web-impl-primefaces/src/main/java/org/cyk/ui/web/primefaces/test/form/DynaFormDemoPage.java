package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.test.model.MyEntity.MyEnum;
import org.cyk.ui.test.model.MyIdentifiable;
import org.cyk.ui.web.api.AjaxBuilder;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.Constant;

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
		/*windowHierachyMenu = userSession.getContextualMenu();
		windowHierachyMenu.setRenderType(UIMenu.RenderType.BREAD_CRUMB);
		windowHierachyMenuModel = userSession.getContextualMenuModel();
		*/
		
		createAjaxBuilder(form,"number1").crossedFieldNames("number2").updatedFieldNames("sumResult","multiplyResult")
		.method(BigDecimal.class,new ListenValueMethod<BigDecimal>() {
			@Override
			public void execute(BigDecimal value) {
				setFieldValue(form,"sumResult", value.add(bigDecimalValue(form, "number2")));
				setFieldValue(form,"multiplyResult", value.multiply(bigDecimalValue(form, "number2")));
				System.out
						.println("DynaFormDemoPage.afterInitialisation().new ListenValueMethod() {...}.execute()");
			}
		}).build();
		
		createAjaxBuilder(form,"number2").crossedFieldNames("number1").updatedFieldNames("sumResult","multiplyResult")
		.method(BigDecimal.class,new ListenValueMethod<BigDecimal>() {
			@Override
			public void execute(BigDecimal value) {
				setFieldValue(form,"sumResult", value.add(bigDecimalValue(form, "number1")));
				setFieldValue(form,"multiplyResult", value.multiply(bigDecimalValue(form, "number1")));
			}
		}).build();
		
		createAjaxBuilder(form,"canSum")
		.method(Boolean.class,new ListenValueMethod<Boolean>() {
			@Override
			public void execute(Boolean value) {
				onComplete(inputRowVisibility(form,"sumResult",value),inputRowVisibility(form,"multiplyResult",value));
			}
		}).build();
		
		AjaxBuilder ajaxBuilder = createAjaxBuilder(form,"myEnum").updatedFieldNames("textOneLine")
		.method(MyEnum.class,new ListenValueMethod<MyEnum>() {
			@Override
			public void execute(MyEnum value) {
				if(MyEnum.V2.equals(value)){
					setFieldValue(form,"textOneLine", "Your two val");
					//setFieldValue(form,"sumResult", new BigDecimal("102030"));
					//setFieldValue(form,"multiplyResult", new BigDecimal("1278"));
					//((Input<Object, ?, ?, ?, ?, ?>) form.findInputByClassByFieldName(Input.class, "textOneLine")).setValue("Update here");
					//((Input<Object, ?, ?, ?, ?, ?>) form.findInputByClassByFieldName(Input.class, "number1")).setValue(new BigDecimal("159"));
					//((Input<Object, ?, ?, ?, ?, ?>) form.findInputByClassByFieldName(Input.class, "number2")).setValue(new BigDecimal("753"));
				}else if(MyEnum.v55.equals(value)){
					setFieldValue(form,"textOneLine", stringValue(form, "textOneLine",Constant.EMPTY_STRING)+" : "+value);
					setFieldValue(form,"multiplyResult", new BigDecimal("9900880077"));
				}
				value = MyEnum.v55;
			}
		});
		//debug(((WebInput) form.findInputByClassByFieldName(WebInput.class, "textOneLine")).getCss());
		//System.out.println("Class : "+((WebInput) form.findInputByClassByFieldName(WebInput.class, "textOneLine")).getUniqueCssClass());
		//ajaxBuilder.getUpdated().add("@(.cyk-ui-form-inputfield)");
		ajaxBuilder.build();
		
		//System.out.println("ddd");
		//debug(((WebInput) form.findInputByClassByFieldName(WebInput.class, "number1")).getCss());
		//debug(((WebInput) form.findInputByClassByFieldName(WebInput.class, "number2")).getCss());
		
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
