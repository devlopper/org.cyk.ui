package org.cyk.ui.web.vaadin.editor;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.table.HierarchyNode;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.ui.web.api.editor.input.WebUIInputSelectOne;
import org.cyk.ui.web.vaadin.CommandBuilder;
import org.cyk.utility.common.model.table.DefaultCell;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;

public class TableModel extends org.cyk.ui.api.model.table.Table<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuBar menuBar;
	@Getter private Table table;
	@Getter private Tree tree;
	//@Getter @Setter private RowEditEventMethod onRowEditMethod,onRowEditInitMethod,onRowEditCancelMethod;
	//@Getter private Button primefacesAddRowCommand,primefacesDeleteRowCommand,primefacesOpenRowCommand;
	//@Getter private PrimefacesTree primefacesTree;
	//@Getter private String updateStyleClass;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
	}
	
	@Override
	public void targetDependentInitialisation() {
		menuBar = CommandBuilder.getInstance().menuBar(menu);
	
		table = new Table();
		table.setSizeFull();
		for(TableColumn column : columns)
			table.addContainerProperty(column.getTitle(), /*commonUtils.getFieldFromClass(rowDataClass, column.getFieldName()).getType()*/ String.class, null);
		updateRows();
		
		if(UsedFor.ENTITY_INPUT.equals(usedFor)){
			if(Boolean.TRUE.equals(getShowHierarchy())){
				BusinessEntityInfos infos = UIManager.getInstance().businessEntityInfos(rowDataClass);
				HierarchyNode base = new HierarchyNode();
				base.setData(infos);
				base.setLabel(infos.getUiLabel());
				tree = CommandBuilder.getInstance().tree(base,hierarchyData);
				tree.expandItem(base);
				if(tree==null){
					
				}else{
					tree.addValueChangeListener(new ValueChangeListener() {
						private static final long serialVersionUID = -2024063606092210625L;
						@Override
						public void valueChange(ValueChangeEvent event) {
							HierarchyNode hierarchyNode = (HierarchyNode) event.getProperty().getValue();
							if(hierarchyNode.getData()==null){
								
							}else if(hierarchyNode.getData() instanceof BusinessEntityInfos) {
								addRowOfRoot(null);
								updateRows();
							}else if(hierarchyNode.getData() instanceof AbstractIdentifiable) {
								AbstractIdentifiable identifiable = (AbstractIdentifiable) hierarchyNode.getData();
								addRowOfRoot(identifiable);
								updateRows();
							}
						}
					});
				}
				select(master);
			}
		}else if(UsedFor.FIELD_INPUT.equals(usedFor)){
			
		}
		
	}
	
	private void updateRows(){
		table.removeAllItems();
		Object[] r = new Object[columns.size()];
		for(TableRow<?> row : rows){
			for(TableColumn column : columns)
				r[column.getIndex()] = row.getCells().get(column.getIndex()).getValue();
			table.addItem(r, row.getIndex());
		}
	}
	
	/**/
	
	@Override
	public boolean addCell(TableRow<AbstractIdentifiable> row, TableColumn column, DefaultCell cell) {
		Boolean r = super.addCell(row, column, cell);
		if(((TableCell)cell).getInputComponent() instanceof WebUIInputSelectOne<?, ?>)
			((WebUIInputSelectOne<?, ?>)((TableCell)cell).getInputComponent()).getCascadeStyleSheet().setClazz("cyk-ui-table-dynamic-selectonemenu");
		//else if(((TableCell)cell).getInputComponent() instanceof WebUIInputText)
		//	((WebUIInputText)((TableCell)cell).getInputComponent()).getCascadeStyleSheet().setClazz("cyk-ui-table-dynamic-selectonemenu");
		return r;
	}
	
	/**/  
	
	
	
		
}
