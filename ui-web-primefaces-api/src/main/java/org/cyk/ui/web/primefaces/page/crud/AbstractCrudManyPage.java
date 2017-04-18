package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.computation.DataReadConfiguration;

@Getter @Setter
public abstract class AbstractCrudManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormManyPage<ENTITY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		table.setEditable(Boolean.TRUE);
		table.getRowListeners().add(new RowAdapter<Object>(getUserSession()){
			private static final long serialVersionUID = 1L;

			@Override
			public AbstractUserSession<?, ?> getUserSession() {
				return AbstractCrudManyPage.this.userSession;
			}
			
			@Override
			public void added(Row<Object> row) {
				super.added(row);
				//row.setOpenable(true);
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Collection<Object> load(DataReadConfiguration configuration) {
				LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Load", "data using configuration");
				table.clear();
				Collection<Object> results = new ArrayList<>();
				Collection<ENTITY> records = null;
				logMessageBuilder.addParameters("configuration",configuration);
				logMessageBuilder.addParameters("hierarchy",Boolean.TRUE.equals(table.isDataTreeType()));
				/*if(Boolean.TRUE.equals(table.isDataTreeType())){
					records = new ArrayList<>();
					table.setShowHierarchy(Boolean.TRUE);
					Collection<?> hierarchies = ((AbstractDataTreeNodeBusiness<?>) getBusiness()).findHierarchies();
					
					for(Object node : hierarchies){
						table.getHierarchyData().add(node);//TREE data
					}
					
					if(StringUtils.isBlank(configuration.getGlobalFilter())){
						if(table.getMaster()==null){
							for(Object node :  ((AbstractDataTreeNodeBusiness<?>) getBusiness()).findRoots())
								records.add((ENTITY) node);
							//records = getBusiness().findByString(configuration.getGlobalFilter(),null,configuration);
						}else{
							table.setMaster((AbstractIdentifiable) table.getReferenceFromHierarchy(table.getMaster(),table.getHierarchyData()));
							//if( ((AbstractDataTreeNode)table.getMaster()).getChildren()!=null)
								for(Object node :  ((AbstractDataTreeNodeBusiness) getBusiness())
										.findDirectChildrenByParent((AbstractEnumeration) identifiable, configuration) )
									records.add((ENTITY) node);	
						}
					}else{
						records = getBusiness().findByString(configuration.getGlobalFilter(),null,configuration);
						//System.out.println("AbstractCrudManyPage.initialisation() : "+records.size());
					}
				}else{
					if(Boolean.TRUE.equals(table.getLazyLoad())){
						if(Boolean.TRUE.equals(table.getGlobalFilter())){
							records = getBusiness().findByString(configuration.getGlobalFilter(),null,configuration);
						}else
							records = getBusiness().findAll(configuration);
					}else
						records = getBusiness().findAll();
				}
				*/
				
				if(Boolean.TRUE.equals(table.getLazyLoad())){
					if(Boolean.TRUE.equals(table.getGlobalFilter()))
						//if(StringUtils.isBlank(configuration.getGlobalFilter()))
						records = getBusiness().findByString(configuration.getGlobalFilter(),null,configuration);
					else
						records = getBusiness().findAll(configuration);
				}else
					if(Boolean.TRUE.equals(table.isDataTreeType()))
						records = (Collection<ENTITY>) ((AbstractDataTreeNodeBusiness<?>) getBusiness()).findRoots();
					else
						records = getBusiness().findAll();
			
				if(StringUtils.isBlank(configuration.getGlobalFilter())){
					if(AbstractDataTreeNode.class.isAssignableFrom(identifiableClass)){
						
					}else{
						
					}
				}else{
					
				}
				
				results = datas(records);
				logMessageBuilder.addParameters("results count",results.size());
				logTrace(logMessageBuilder);
				return results;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Long count(DataReadConfiguration configuration) {
				/*if(Boolean.TRUE.equals(table.isDataTreeType())){
					if(StringUtils.isBlank(configuration.getGlobalFilter()))
						return ((AbstractDataTreeNodeBusiness<?>) getBusiness()).countRoots();
					else
						return ((AbstractDataTreeNodeBusiness) getBusiness()).countByString(configuration.getGlobalFilter());
						//return ((AbstractDataTreeNodeBusiness) getBusiness()).countDirectChildrenByParent((AbstractEnumeration) identifiable);
				}else{
					if(Boolean.TRUE.equals(table.getLazyLoad())){
						if(Boolean.TRUE.equals(table.getGlobalFilter()))
							return getBusiness().countByString(configuration.getGlobalFilter());
						else
							return getBusiness().countByString(configuration.getGlobalFilter());
					}else
						return getBusiness().countAll();
				}
				*/
				if(Boolean.TRUE.equals(table.getLazyLoad())){
					if(Boolean.TRUE.equals(table.getGlobalFilter()))
						return getBusiness().countByString(configuration.getGlobalFilter());
					else
						if(Boolean.TRUE.equals(table.isDataTreeType()))
							return ((AbstractDataTreeNodeBusiness<?>) getBusiness()).countRoots();
						else
							return getBusiness().countByString(configuration.getGlobalFilter());
				}else
					return getBusiness().countAll();
			}
		});	
		
		//TODO should be removed
		rowAdapter.setOpenable(Boolean.TRUE);
		rowAdapter.setUpdatable(Boolean.TRUE);
		rowAdapter.setDeletable(Boolean.TRUE);
		
		table.setShowHeader(Boolean.TRUE);
		table.setShowToolBar(Boolean.TRUE);
		table.setShowOpenCommand(Boolean.TRUE);
		table.setShowFooter(Boolean.FALSE);
		
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
