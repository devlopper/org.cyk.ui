package org.cyk.ui.api.data.collector.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;

import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.IconType;
import org.cyk.ui.api.model.table.AbstractTable;

public class AbstractFormManyData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> extends AbstractForm<List<DATA>, MODEL, ROW, LABEL, CONTROL, SELECTITEM> implements FormManyData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> {

	private static final long serialVersionUID = 699223627315694028L;
	
	@Getter protected Class<DATA> dataType;
	@Getter protected UICommandable addCommandable,deleteCommandable,editCommandable,openCommandable,cancelEditCommandable,saveCommandable,exportCommandable;
	@Getter protected Collection<FormManyItemListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> formManyItemListeners = new ArrayList<>();
	
	public AbstractFormManyData(Class<DATA> dataType) {
		this.dataType = dataType;
		addCommandable = createCommandable(this,"command.add",IconType.ACTION_OK);
		deleteCommandable = createCommandable(this,"command.delete",IconType.ACTION_REMOVE);
		saveCommandable = createCommandable(this,"command.save",IconType.ACTION_SAVE);
		editCommandable = createCommandable(this,"command.edit",IconType.ACTION_EDIT);
		openCommandable = createCommandable(this,"command.open",IconType.ACTION_OPEN);
		cancelEditCommandable = createCommandable(this,"command.cancel",IconType.ACTION_CANCEL);
		exportCommandable = createCommandable(this,"command.export",IconType.ACTION_EXPORT);
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		super.serve(command, parameter);
		if(commandEquals(addCommandable, command)){
			DATA data;
			try {
				data = dataType.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			getData().add(data);
		}else if(commandEquals(deleteCommandable, command)){
			
		}
	}

	@Override
	public AbstractTable<DATA,?,?> getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T findInputByFieldName(DATA data, Class<T> aClass, String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChoices(DATA data, String fieldName, List<SELECTITEM> choices) {
		// TODO Auto-generated method stub
		
	}
	
}
