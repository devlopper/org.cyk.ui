package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.table.HierarchyNode;
import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.api.editor.input.WebUIInputSelectOne;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.model.table.DefaultCell;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.menu.MenuModel;

public class PrimefacesTable<DATA> extends Table<DATA> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuModel menuModel;
	@Getter @Setter private RowEditEventMethod onRowEditMethod,onRowEditInitMethod,onRowEditCancelMethod;
	@Getter private Command primefacesAddRowCommand,primefacesDeleteRowCommand,primefacesOpenRowCommand;
	@Getter private PrimefacesTree primefacesTree;
	@Getter private String updateStyleClass;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		updateStyleClass = RandomStringUtils.randomAlphabetic(2)+""+System.currentTimeMillis();
		primefacesAddRowCommand =  new Command(addRowCommand,  "@(."+updateStyleClass+")");
		primefacesAddRowCommand.getCommandButton().setOncomplete("clickEditButtonLastRow('"+updateStyleClass+"');");
		
		primefacesDeleteRowCommand =  new Command(deleteRowCommand);
		primefacesOpenRowCommand =  new Command(openRowCommand);
		
		rowNavigateEventMethod = new RowNavigateEventMethod() {
			private static final long serialVersionUID = -3334241830659069117L;

			@SuppressWarnings("unchecked")
			@Override
			protected void onEvent(TableRow<?> row) {
				redirectTo((DATA) row.getData());
			}
		};
		
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
		if(UsedFor.ENTITY_INPUT.equals(usedFor)){
			Field field = commonUtils.getField(getWindow(), this);
			menuModel = CommandBuilder.getInstance().menuModel(menu, window.getClass(), field.getName());
			if(Boolean.TRUE.equals(getShowHierarchy())){
				HierarchyNode hierarchyNode = new HierarchyNode(null);
				hierarchyNode.setLabel(getTitle());
				primefacesTree = new PrimefacesTree(hierarchyNode);
				for(DATA d : hierarchyData)
					primefacesTree.populate(d);	
				primefacesTree.setOnNodeSelect(new AbstractMethod<Object, Object>() {
					private static final long serialVersionUID = -9071786035119019765L;
					@SuppressWarnings("unchecked")
					@Override
					protected Object __execute__(Object parameter) {
						redirectTo((DATA) parameter);
						return null;
					}
				});
			}
		}else if(UsedFor.FIELD_INPUT.equals(usedFor)){
			
		}
		
	}
	
	private void redirectTo(DATA object){
		WebNavigationManager.getInstance().redirectTo("dynamictable",new Object[]{
				WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(rowDataClass),
				WebManager.getInstance().getRequestParameterIdentifiable(), object==null?null:((AbstractIdentifiable)object).getIdentifier()
		});
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
	
	@Override
	public boolean addCell(TableRow<DATA> row, TableColumn column, DefaultCell cell) {
		Boolean r = super.addCell(row, column, cell);
		if(((TableCell)cell).getInputComponent() instanceof WebUIInputSelectOne<?, ?>)
			((WebUIInputSelectOne<?, ?>)((TableCell)cell).getInputComponent()).getCascadeStyleSheet().setClazz("cyk-ui-table-dynamic-selectonemenu");
		//else if(((TableCell)cell).getInputComponent() instanceof WebUIInputText)
		//	((WebUIInputText)((TableCell)cell).getInputComponent()).getCascadeStyleSheet().setClazz("cyk-ui-table-dynamic-selectonemenu");
		return r;
	}
	
	/**/  
	
	public static abstract class RowEditEventMethod extends AbstractMethod<Object, RowEditEvent>{
		private static final long serialVersionUID = -145475519122234694L;
		@Override protected final Object __execute__(RowEditEvent rowEditEvent) {onEvent(rowEditEvent);return null;}
		protected abstract void onEvent(RowEditEvent rowEditEvent);
	}
	
	
		
}
