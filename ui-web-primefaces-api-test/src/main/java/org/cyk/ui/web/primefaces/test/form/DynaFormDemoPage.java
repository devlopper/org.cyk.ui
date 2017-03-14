package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.test.model.actor.MyEntity.MyEnum;
import org.cyk.system.test.model.actor.MyIdentifiable;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.AjaxBuilder;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

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
	private Form1 data1;
	private Form2 data2;
	private Form3 data3;
	private FormOneData<Form1> form1;
	private FormOneData<Form2> form2;
	private FormOneData<Form3> form3;
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		form = (FormOneData<MyIdentifiable>) createFormOneData(entityWithAnnotation,Crud.CREATE);

		form.setTabTitle("Mon Formulaire");
		//form.setFieldsRequiredMessage("Champs obligatoire");
		form.setDynamic(Boolean.TRUE);
		
		form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;
			@Override
			public void serve(UICommand command, Object parameter) {
				super.serve(command, parameter);
				System.out.println(entityWithAnnotation);
				debug(data1);
				debug(data2);
				debug(data3);
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
		
		form1 = (FormOneData<Form1>) createFormOneData(data1 = new Form1(),Crud.CREATE);
		form1.setDynamic(Boolean.TRUE);
		form1.setTabTitle("Form1");
		
		form2 = (FormOneData<Form2>) createFormOneData(data2 = new Form2(),Crud.CREATE);
		form2.setDynamic(Boolean.TRUE);
		form2.setTabTitle("Form2");
		
		form3 = (FormOneData<Form3>) createFormOneData(data3 = new Form3(),Crud.CREATE);
		form3.setDynamic(Boolean.TRUE);
		form3.setTabTitle("Form3");
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		/*windowHierachyMenu = userSession.getContextualMenu();
		windowHierachyMenu.setRenderType(UIMenu.RenderType.BREAD_CRUMB);
		windowHierachyMenuModel = userSession.getContextualMenuModel();
		*/
		
		createAjaxBuilder(form,"number1").crossedFieldNames("number2").updatedFieldNames("sumResult","multiplyResult","myEnum","textManyLine2")
		.method(BigDecimal.class,new ListenValueMethod<BigDecimal>() {
			@Override
			public void execute(BigDecimal value) {
				setFieldValue(form,"sumResult", value.add(bigDecimalValue(form, "number2")));
				setFieldValue(form,"multiplyResult", value.multiply(bigDecimalValue(form, "number2")));
				setFieldValue(form,"myEnum", MyEnum.V3);
				
				setFieldValue(form,"textManyLine2", "Jesus is there");
				
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
		
		createAjaxBuilder(form,"canSum").updatedFieldNames("sumResult")
		.method(Boolean.class,new ListenValueMethod<Boolean>() {
			@Override
			public void execute(Boolean value) {
				form.getInputByFieldName("sumResult").setLabel(RandomStringUtils.randomAlphanumeric(10));
				setFieldValue(form,"sumResult", new BigDecimal(RandomStringUtils.randomNumeric(5)));
				onComplete(inputRowVisibility(form,"sumResult",value),inputRowVisibility(form,"multiplyResult",value));
				//Ajax.update("form:contentp");
				//Ajax.updateAll();
				
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
	
	/**/
	
	public static class Form1 {
		
		@Input @InputText @NotNull private String form1Field1;
		
	}
	
	public static class Form2 {
		
		@Input @InputText private String form2Field1;
		
	}

	public static class Form3 {
		
		@Input @InputText private String form3Field1;
		
	}

	
}
