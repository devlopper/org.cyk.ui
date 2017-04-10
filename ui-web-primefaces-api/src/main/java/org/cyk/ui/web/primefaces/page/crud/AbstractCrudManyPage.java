package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.computation.DataReadConfiguration;

import lombok.Getter;
import lombok.Setter;

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
				if(Boolean.TRUE.equals(table.isDataTreeType())){
					records = new ArrayList<>();
					table.setShowHierarchy(Boolean.TRUE);
					@SuppressWarnings({ "rawtypes" })
					AbstractDataTreeNodeBusiness business = (AbstractDataTreeNodeBusiness) BusinessInterfaceLocator.getInstance()
						.injectTyped((Class<AbstractIdentifiable>) businessEntityInfos.getClazz());
					for(Object node : business.findHierarchies())
						table.getHierarchyData().add(node);
					
					if(table.getMaster()==null){
						for(Object node : table.getHierarchyData())
							records.add((ENTITY) node);
					}else{
						table.setMaster((AbstractIdentifiable) table.getReferenceFromHierarchy(table.getMaster(),table.getHierarchyData()));
						if( ((AbstractDataTreeNode)table.getMaster()).getChildren()!=null)
							for(AbstractIdentifiable node : ((AbstractDataTreeNode)table.getMaster()).getChildren())
								records.add((ENTITY) node);	
					}
				}else{
					Class<ENTITY> identifiableClass = (Class<ENTITY>) businessEntityInfos.getClazz();
					if(Boolean.TRUE.equals(table.getLazyLoad())){
						if(Boolean.TRUE.equals(table.getGlobalFilter())){
							records = inject(BusinessInterfaceLocator.class).injectTyped(identifiableClass).findByString(configuration.getGlobalFilter(),null,configuration);
							//(Collection<ENTITY>) BusinessServiceProvider.getInstance().find(identifiableClass, configuration);
						}else
							records = inject(BusinessInterfaceLocator.class).injectTyped(identifiableClass).findAll(configuration);
							//(Collection<ENTITY>) BusinessServiceProvider.getInstance().find(identifiableClass, configuration);
					}else
						records = inject(BusinessInterfaceLocator.class).injectTyped(identifiableClass).findAll();
							//(Collection<ENTITY>) RootBusinessLayer.getInstance().getGenericBusiness().use(identifiableClass).find().all();
					
					/*if(records!=null){
						if(AbstractIdentifiable.class.isAssignableFrom(table.getRowDataClass()))
							for(AbstractIdentifiable identifiable : records)	
								results.add(identifiable);
						else
							for(AbstractIdentifiable identifiable : records)	
								results.add(AbstractFormModel.instance(table.getRowDataClass(), identifiable));
					}*/
					
				}
			
				results = datas(records);
				logMessageBuilder.addParameters("results count",results.size());
				logTrace(logMessageBuilder);
				return results;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Long count(DataReadConfiguration configuration) {
				if(Boolean.TRUE.equals(table.isDataTreeType())){
					
				}else{
					Class<AbstractIdentifiable> identifiableClass = (Class<AbstractIdentifiable>) businessEntityInfos.getClazz();
					if(Boolean.TRUE.equals(table.getLazyLoad())){
						if(Boolean.TRUE.equals(table.getGlobalFilter()))
							return inject(BusinessInterfaceLocator.class).injectTyped(identifiableClass).countByString(configuration.getGlobalFilter());
							//BusinessServiceProvider.getInstance().count(identifiableClass, configuration);
						else
							return inject(BusinessInterfaceLocator.class).injectTyped(identifiableClass).countByString(configuration.getGlobalFilter());
							//BusinessServiceProvider.getInstance().count(identifiableClass, configuration);
					}else
						return inject(BusinessInterfaceLocator.class).injectTyped(identifiableClass).countAll();
							//UIManager.getInstance().getGenericBusiness().use(identifiableClass).find(Function.COUNT).oneLong();
				}
				return super.count(configuration);
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
