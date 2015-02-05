package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.event.EventCalendar;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.PersonFormModel;
import org.cyk.ui.api.model.table.AbstractTable.UsedFor;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.data.collector.form.ControlSet;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.model.table.TableAdapter;
import org.primefaces.model.UploadedFile;

@Named @ViewScoped @Getter @Setter
public class TestPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = -8787434348857586666L;

	@Getter private ContactCollection contactCollection;
	private ControlSet<ContactCollection> c;
	
	protected Table<Object> t1,t2,t3;
	private FormOneData<PersonFormModel> form;
	
	@Setter
	private UploadedFile uploadedFile;
	private Boolean showUploadedFile=true;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		
		form = (FormOneData<PersonFormModel>) createFormOneData(new PersonFormModel(), Crud.CREATE);
		form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -8235162327440724633L;

			@Override
			public void serve(UICommand command, Object parameter) {
				System.out.println("TestPage.initialisation().new CommandAdapter() {...}.serve()");
				debug(((PersonFormModel)parameter).getIdentifiable());
				//UIManager.getInstance().getGenericBusiness().save( ((PersonFormModel)parameter).getIdentifiable() );
			}
		});
		
		contactCollection = new ContactCollection();
		t1 = (Table<Object>) createTable(PhoneNumber.class, null, UsedFor.FIELD_INPUT, Crud.CREATE);
		t1.getTableListeners().add(new TableAdapter<Row<Object>, Column, Object, String, Cell, String>(){
			
			@Override
			public Boolean ignore(Field field) {
				Input input = field.getAnnotation(Input.class);
				IncludeInputs includeInputs = field.getAnnotation(IncludeInputs.class);
				return input == null && includeInputs==null;
			}
			
		});
		
		//table.getAddRowCommandable().getCommand().getCommandListeners().add(this);
		//table.getApplyRowEditCommandable().getCommand().getCommandListeners().add(this);
		
		t1.addColumnFromDataClass();
		//table.build();
		//System.out.println(table.getColumns());
		//table.addRow(new PhoneNumberType(null, null));
		
		/*
		c = new ControlSet<ContactCollection>();
		InputText inputText = new InputText();
		c.add(inputText);
		*/
		
		t2 = (Table<Object>) createTable(EventCalendar.class, null, UsedFor.FIELD_INPUT, Crud.CREATE);
		
		
		t2.getTableListeners().add(new TableAdapter<Row<Object>, Column, Object, String, Cell, String>(){
			
			@Override
			public Boolean ignore(Field field) {
				Input input = field.getAnnotation(Input.class);
				IncludeInputs includeInputs = field.getAnnotation(IncludeInputs.class);
				return input == null && includeInputs==null;
			}
			
		});
		
		//table.getAddRowCommandable().getCommand().getCommandListeners().add(this);
		//table.getApplyRowEditCommandable().getCommand().getCommandListeners().add(this);
		
		t2.addColumnFromDataClass();
	}
		
}
