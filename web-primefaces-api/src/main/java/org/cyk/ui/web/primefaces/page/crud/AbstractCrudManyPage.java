package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.utility.common.computation.Function;
import org.cyk.utility.common.model.table.TableAdapter;

@Getter @Setter
public abstract class AbstractCrudManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormManyPage<ENTITY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		table.setEditable(Boolean.TRUE);
		table.getTableListeners().add(new TableAdapter<Row<Object>, Column, Object, String, Cell, String>(){
			@SuppressWarnings("unchecked")
			@Override
			public Collection<Object> fetchData(Integer first, Integer pageSize, String sortField, Boolean ascendingOrder, Map<String, Object> filters, String globalFilter) {
				table.clear();
				Collection<Object> results = new ArrayList<>();
				if(Boolean.TRUE.equals(table.isDataTreeType())){
					table.setShowHierarchy(Boolean.TRUE);
					@SuppressWarnings({ "rawtypes" })
					AbstractDataTreeNodeBusiness business = (AbstractDataTreeNodeBusiness) BusinessLocator.getInstance().locate((Class<AbstractIdentifiable>)businessEntityInfos.getClazz());
					for(Object node : business.findHierarchies())
						table.getHierarchyData().add(node);
					
					if(table.getMaster()==null)
						for(Object node : table.getHierarchyData())
							results.add(node);
					else{
						//business.findHierarchy( master);
						table.setMaster((AbstractIdentifiable) table.getReferenceFromHierarchy(table.getMaster(),table.getHierarchyData()));
						if( ((AbstractDataTreeNode)table.getMaster()).getChildren()!=null)
							for(AbstractDataTreeNode node : ((AbstractDataTreeNode)table.getMaster()).getChildren())
								results.add(node);	
					}
					
				}else{
					Collection<AbstractIdentifiable> records = null;
					Class<AbstractIdentifiable> identifiableClass = (Class<AbstractIdentifiable>) businessEntityInfos.getClazz();
					if(Boolean.TRUE.equals(table.getLazyLoad())){
						if(Boolean.TRUE.equals(table.getGlobalFilter()))
							records = UIManager.getInstance().find(identifiableClass, first, pageSize, sortField, ascendingOrder, globalFilter);
						else
							records = UIManager.getInstance().find(identifiableClass, first, pageSize, sortField, ascendingOrder, filters);
					}else
						records = UIManager.getInstance().getGenericBusiness().use(identifiableClass).find().all();
					
					/*if(records!=null){
						if(AbstractIdentifiable.class.isAssignableFrom(table.getRowDataClass()))
							for(AbstractIdentifiable identifiable : records)	
								results.add(identifiable);
						else
							for(AbstractIdentifiable identifiable : records)	
								results.add(AbstractFormModel.instance(table.getRowDataClass(), identifiable));
					}*/
					results = datas(records);
				}
				return results;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Long count(String filter) {
				if(Boolean.TRUE.equals(table.isDataTreeType())){
					
				}else{
					Class<AbstractIdentifiable> identifiableClass = (Class<AbstractIdentifiable>) businessEntityInfos.getClazz();
					if(Boolean.TRUE.equals(table.getLazyLoad())){
						if(Boolean.TRUE.equals(table.getGlobalFilter()))
							return UIManager.getInstance().count(identifiableClass, filter);
						else
							return UIManager.getInstance().count(identifiableClass, filter);
					}else
						return UIManager.getInstance().getGenericBusiness().use(identifiableClass).find(Function.COUNT).oneLong();
				}
				return super.count(filter);
			}
		});	
		
		//onDocumentLoadJavaScript = "$('.dataTableStyleClass > .ui-datatable-tablewrapper > table > tfoot').hide();"
		//		+ "$('.dataTableStyleClass > .ui-datatable-header').hide();";
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		if(table.getApplyRowEditCommandable().getCommand()==command){
			//getGenericBusiness().save(identifiable);
		}else if(table.getRemoveRowCommandable().getCommand()==command){
			//getGenericBusiness().delete(identifiable);
		}/*else if(table.getAddRowCommandable().getCommand()==command){
			
		}*/
	}

}
