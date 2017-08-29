package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.search.StringSearchCriteria;
import org.cyk.system.root.persistence.api.party.person.PersonExtendedInformationsDao;
import org.cyk.system.test.model.actor.MyEntity.MyEnum;
import org.cyk.system.test.model.actor.MyIdentifiableDetails1;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.api.AjaxBuilder;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.api.SelectItemHelper;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.OutputText;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;
import org.cyk.utility.common.helper.InstanceHelper;

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

	/**/
	
	@Getter @Setter
	public static class MyIdentifiable extends AbstractIdentifiable implements Serializable {

		private static final long serialVersionUID = 2551782857718212950L;
		
		@Input @InputText //@NotNull
		private String textOneLine;

		@Input @InputTextarea //@NotNull
		private String textManyLine;
		
		@Input @InputNumber /*@NotNull*/ private BigDecimal number1=BigDecimal.ZERO;
		@Input @InputNumber /*@NotNull*/ private BigDecimal number2=BigDecimal.ZERO;
		
		@Input @InputBooleanButton private Boolean canSum;
		
		@OutputSeperator(label=@Text(value="results")) 
		@OutputText(label=@Text(valueType=ValueType.VALUE,value="This is the results section")) 
		@Input(disabled=true) @InputText 
		private String sumResult;
		@Input(disabled=true) @InputText private String multiplyResult = "159753.852";
		
		@Input(disabled=true)
		@InputChoice
		@InputOneChoice
		@InputOneCombo
		private MyEnum myEnum = MyEnum.V3;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private Person persons;
		@Input @NotNull @InputChoice(itemBuilderClass=CustomSelectPersonBuilder1.class,nullable=false,getChoicesClass=CustomePersonList2.class) @InputOneChoice @InputOneCombo private Person persons1;
		@Input @InputChoice(itemBuilderClass=CustomSelectPersonBuilder2.class,getChoicesClass=CustomePersonList1.class) @InputOneChoice @InputOneCombo private Person persons2;
		
		@Input(disabled=true) @InputTextarea
		private String textManyLine2 = "Text Many Line Two";
		
		//@IncludeInputs
		//@OutputSeperator
		private MyIdentifiableDetails1 details1;
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
		
		public static class CustomSelectPersonBuilder1 extends SelectItemHelper.OneBuilder {
			private static final long serialVersionUID = 1L;
			@Override
			public String getFieldValue(Object instance, String fieldName) {
				if(instance instanceof Person && ((Person)instance).getSex()!=null)
					return ((Person)instance).getNames()+"("+((Person)instance).getSex()+")";
				return super.getFieldValue(instance, fieldName);
			}
		}
		
		public static class CustomSelectPersonBuilder2 extends SelectItemHelper.OneBuilder {
			private static final long serialVersionUID = 1L;
			@Override
			public String getFieldValue(Object instance, String fieldName) {
				if(instance instanceof Person && ((Person)instance).getSex()!=null){
					PersonExtendedInformations personExtendedInformations = inject(PersonExtendedInformationsDao.class).readByParty((Person)instance);
					if(personExtendedInformations!=null)
						return "("+((Person)instance).getSex()+")"+((Person)instance).getNames()+"("+personExtendedInformations.getTitle()+")";
				}
					
				return super.getFieldValue(instance, fieldName);
			}
		}
		
		public static class CustomePersonList1 extends InstanceHelper.Many.Adapter.Default implements Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			protected Collection<?> __execute__() {
				return inject(PersonBusiness.class).findAll();
			}
			
		}
		
		public static class CustomePersonList2 extends InstanceHelper.Many.Adapter.Default implements Serializable {
			private static final long serialVersionUID = 1L;

			@Override
			protected Collection<?> __execute__() {
				return inject(PersonBusiness.class).findByString(new StringSearchCriteria("komenan"));
			}
			
		}
	}
}
