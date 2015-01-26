package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
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
import org.cyk.ui.test.model.MyIdentifiable;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

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
		form.setFieldsRequiredMessage("Champs obligatoire");
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
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
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