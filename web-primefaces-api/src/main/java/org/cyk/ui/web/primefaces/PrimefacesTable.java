package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.utility.common.AbstractMethod;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.menu.MenuModel;

public class PrimefacesTable<DATA> extends Table<DATA> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuModel menuModel;
	@Getter @Setter private RowEditEventMethod onRowEditMethod,onRowEditInitMethod,onRowEditCancelMethod;
	@Getter private Command primefacesAddRowCommand,primefacesDeleteRowCommand;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		primefacesAddRowCommand =  new Command(addRowCommand);
		primefacesAddRowCommand.getCommandButton().setOncomplete("clickEditButtonLastRow();");
		
		primefacesDeleteRowCommand =  new Command(deleteRowCommand);
		
		onRowEditMethod = new RowEditEventMethod(){
			private static final long serialVersionUID = -8499327887343205809L;
			@Override
			protected void onEvent(RowEditEvent rowEditEvent) {
				TableRow<?> row = (TableRow<?>) rowEditEvent.getObject();
				row.updateFieldValues();
				saveRowCommand.execute(row.getData());
			}};
		
		onRowEditCancelMethod = new RowEditEventMethod(){
			private static final long serialVersionUID = -8499327887343205809L;
			@Override
			protected void onEvent(RowEditEvent rowEditEvent) {
				TableRow<?> row = (TableRow<?>) rowEditEvent.getObject();
				cancelRowCommand.execute(row.getData());
			}};
	}
	
	@Override
	public void targetDependentInitialisation() {
		Field field = commonUtils.getField(getWindow(), this);
		menuModel = CommandBuilder.getInstance().menuModel(menu, window.getClass(), field.getName());
	}
		
	public void onRowEditInit(RowEditEvent rowEditEvent){
		if(onRowEditInitMethod!=null)
			onRowEditInitMethod.__execute__(rowEditEvent);
	}
	public void onRowEdit(RowEditEvent rowEditEvent){
		if(onRowEditMethod!=null)
			onRowEditMethod.__execute__(rowEditEvent);
	}
	public void onRowEditCancel(RowEditEvent rowEditEvent){
		if(onRowEditCancelMethod!=null)
			onRowEditCancelMethod.__execute__(rowEditEvent);
	}
	
	/**/
	
	public static abstract class RowEditEventMethod extends AbstractMethod<Object, RowEditEvent>{
		private static final long serialVersionUID = -145475519122234694L;
		@Override protected final Object __execute__(RowEditEvent rowEditEvent) {onEvent(rowEditEvent);return null;}
		protected abstract void onEvent(RowEditEvent rowEditEvent);
	}
		
}
