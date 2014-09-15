package org.cyk.ui.web.vaadin.editor;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.model.table.HierarchyNode;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableListener;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.ui.web.api.editor.input.WebUIInputSelectOne;
import org.cyk.ui.web.vaadin.CommandBuilder;
import org.cyk.utility.common.model.table.DefaultCell;

import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;

public class TableModel extends org.cyk.ui.api.model.table.Table<AbstractIdentifiable> implements TableListener,Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuBar menuBar,rowEditMenuBar;
	@Getter private Table table;
	@Getter private Tree tree;
	private FieldGroup fieldGroup;
	private PropertysetItem propertysetItem;
	
	//@Getter @Setter private RowEditEventMethod onRowEditMethod,onRowEditInitMethod,onRowEditCancelMethod;
	//@Getter private Button primefacesAddRowCommand,primefacesDeleteRowCommand,primefacesOpenRowCommand;
	//@Getter private PrimefacesTree primefacesTree;
	//@Getter private String updateStyleClass;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		listeners.add(this);
	}
	
	@Override
	public void targetDependentInitialisation() {
		menuBar = CommandBuilder.getInstance().menuBar(menu,null);
		update();
		propertysetItem = new PropertysetItem();
		table = new Table();
		table.setTableFieldFactory(new CustomTableFieldFactory());
		
		table.setEditable(Boolean.TRUE);
		table.setSelectable(getSelectable());
		table.setImmediate(Boolean.TRUE);
		table.setNullSelectionAllowed(Boolean.FALSE);
		table.setSizeFull();
		table.addContainerProperty("#", Integer.class, 0);
		for(TableColumn column : columns)
			table.addContainerProperty(column.getTitle(), String.class, "");
		table.addContainerProperty(UIManager.getInstance().text("table.row.actions"),MenuBar.class, null);
		updateRows();
		
		if(UsedFor.ENTITY_INPUT.equals(usedFor)){
			if(Boolean.TRUE.equals(getShowHierarchy())){
				BusinessEntityInfos infos = UIManager.getInstance().businessEntityInfos(rowDataClass);
				HierarchyNode base = new HierarchyNode();
				base.setData(infos);
				base.setLabel(infos.getUiLabel());
				tree = CommandBuilder.getInstance().tree(base,hierarchyData);
				if(tree==null){
					
				}else{
					tree.expandItem(base);
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
			table.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 2055260499277595656L;
				@Override
				public void valueChange(ValueChangeEvent event) {
					selectedRow = rows.get((Byte)event.getProperty().getValue());
					update();
					//table.setEditable(Boolean.TRUE);
					//System.out.println("TableModel.targetDependentInitialisation().new ValueChangeListener() {...}.valueChange()");
				}
			});
		}else if(UsedFor.FIELD_INPUT.equals(usedFor)){
			
		}
		
	}
	
	private void updateRows(){
		table.removeAllItems();
		for(TableRow<AbstractIdentifiable> row : rows)
			__createRow__(row);
	}
	
	@Override
	protected void __createRow__(TableRow<AbstractIdentifiable> row) {
		super.__createRow__(row);
		Object[] r = new Object[1+columns.size()+1];
		r[0] = row.getIndex()+1;
		
		for(TableColumn column : columns)
			r[column.getIndex()+1] = row.getCells().get(column.getIndex()).getValue();
		MenuBar rowMenuBar = CommandBuilder.getInstance().menuBar(editRowMenu,row);
		rowMenuBar.setVisible(Boolean.FALSE);
		r[columns.size()+1]=rowMenuBar;
		table.addItem(r, row.getIndex());
		
	}
	
	@Override
	protected void __deleteRow__(TableRow<AbstractIdentifiable> row) {
		super.__deleteRow__(row);
		table.removeItem(row.getIndex());
	}
	
	protected MenuItem menuItem(MenuBar menuBar,UICommandable commandable){
		for(MenuItem menuItem : menuBar.getItems())
			if( menuItem.getText().equals(commandable.getLabel()) )
				return menuItem;
		return null;
	}
	
	protected void update(){
		
		for(UICommandable commandable : rowCommandables){
			MenuItem menuItem = menuItem(menuBar, commandable);
			menuItem.setEnabled(selectedRow!=null);
		}
		
		menuItem(menuBar, addRowCommand).setEnabled(editingRow==null);
		menuItem(menuBar, openRowCommand).setEnabled(selectedRow!=null && editingRow==null);
		menuItem(menuBar, editRowCommand).setEnabled(selectedRow!=null && editingRow==null);
		menuItem(menuBar, deleteRowCommand).setEnabled(editingRow==null);
		menuItem(menuBar, addRowCommand).setEnabled(editingRow==null);
		menuItem(menuBar, exportCommand).setEnabled(editingRow==null);
		
		menuItem(menuBar, cancelCommand).setVisible(editingRow!=null);
		menuItem(menuBar, saveRowCommand).setVisible(editingRow!=null);
		
		
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
	
	@Override
	public void rowEvent(UICommandable commandable,TableRow<?> row) {
		if(commandable == addRowCommand){
			fieldGroup = new FieldGroup();
			
		}else if(commandable == saveRowCommand){
			//Field<?> f= fieldGroup.getFields().iterator().next();
			//f.commit();
			//System.out.println(f.getValue());
			try {
			
				fieldGroup.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(commandable == editRowCommand){
			fieldGroup = new FieldGroup();
			
		}else if(commandable == deleteRowCommand){
			
		}else if(commandable == cancelCommand){
			
		}
		table.setEditable(Boolean.TRUE);
		update();
	}
	
	private class CustomTableFieldFactory extends DefaultFieldFactory implements Serializable {
		private static final long serialVersionUID = -3648532849998018215L;
		@Override
		public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
			Field<?> field = null;
			if(editingRow!=null && editingRow.getIndex().equals(itemId)){
				TableRow<?> row = rows.get((Byte) itemId);
				for(UIInputComponent<?> input : row.getInputComponents()){
					if(input.getLabel().equals(propertyId)){
						field = (Field<?>) Input.createComponent(new Input(null, input));
						ObjectProperty<String> property = new ObjectProperty<String>("Hello", String.class);
						field.setPropertyDataSource(property);
						((TextField)field).setValue("df");
						((TextField)field).setDescription("GDF");
						propertysetItem.addItemProperty(propertyId+""+itemId+System.currentTimeMillis(), property);
						if(field!=null){
							field.setRequired(true);
							if(fieldGroup==null)
								return null;
							fieldGroup.setItemDataSource(propertysetItem);
						}
					}
				}
				//return super.createField(container, itemId, propertyId, uiContext);
			}
			return field;
		}
		
	}
}
