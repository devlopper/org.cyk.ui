package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.model.table.TableAdapter;

@Named
@ViewScoped
@Getter
@Setter
public class TableDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private Table<PhoneNumberType> table;

	@Override
	protected void initialisation() { 
		super.initialisation(); 
		table = (Table<PhoneNumberType>) createTable(PhoneNumberType.class);
		table.getTableListeners().add(new TableAdapter<Row<PhoneNumberType>, Column, PhoneNumberType, String, Cell, String>(){
			@Override
			public Boolean ignore(Field field) {
				Input input = field.getAnnotation(Input.class);
				return input == null;
			}
		});
		
		table.addColumnFromDataClass();
		for(AbstractIdentifiable i : getGenericBusiness().use(PhoneNumberType.class).find().all())
			table.addRow((PhoneNumberType)i);
		
		
	}
	
	
}
