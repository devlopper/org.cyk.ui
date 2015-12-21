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
import org.cyk.ui.test.model.MyEntity.MyEnum;
import org.cyk.ui.test.model.MyIdentifiable;
import org.cyk.ui.web.api.AjaxBuilder;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.computation.ExecutionProgress;

@Named
@ViewScoped
@Getter
@Setter
public class ProgressBarPage extends AbstractPrimefacesPage implements Serializable {

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
		form.getSubmitCommandable().getCommand().setShowExecutionProgress(Boolean.TRUE);

		form.setTitle("Mon Formulaire");
		//form.setFieldsRequiredMessage("Champs obligatoire");
		form.setDynamic(Boolean.TRUE);
		
		form.getSubmitCommandable().getCommand().setExecutionProgress(executionProgress = new ExecutionProgress());
		//form.getSubmitCommandable().getCommand().getExecutionProgress().setCurrentAmountOfWorkDone(0l);
		
		primefacesManager.configureProgressBar(form.getSubmitCommandable());
		
		form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;
			@Override
			public void serve(UICommand command, Object parameter) {
				super.serve(command, parameter);
				pause(1000);executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone()+5);
				pause(1000);executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone()+10);
				pause(1000);executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone()+10);
				pause(1000);executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone()+30);
				pause(1000);executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone()+5);
				pause(1000);executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone()+5);
				pause(1000);executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone()+25);
				pause(1000);executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone()+10);
				
				//Ajax.oncomplete("PF('progressBar').cancel();");
			}
			
			@Override
			public Object succeed(UICommand command, Object parameter) {
				//Ajax.oncomplete("PF('progressBar').cancel();");
				return super.succeed(command, parameter);
			}
		});
		
		//form.getSubmitCommandable().setOnClick("PF('progressBar').start();");
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
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
		ajaxBuilder.build();
		
	}
	
	

	
}
