package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.BusinessServiceProvider;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.computation.Function;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCrudManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormManyPage<ENTITY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		table.setEditable(Boolean.TRUE);
		table.getRowListeners().add(new RowAdapter<Object>(){
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public Collection<Object> load(DataReadConfiguration configuration) {
				table.clear();
				Collection<Object> results = new ArrayList<>();
				if(Boolean.TRUE.equals(table.isDataTreeType())){
					table.setShowHierarchy(Boolean.TRUE);
					@SuppressWarnings({ "rawtypes" })
					AbstractDataTreeNodeBusiness business = (AbstractDataTreeNodeBusiness) BusinessInterfaceLocator.getInstance()
						.injectTyped((Class<AbstractIdentifiable>) businessEntityInfos.getClazz());
					for(Object node : business.findHierarchies())
						table.getHierarchyData().add(node);
					
					if(table.getMaster()==null)
						for(Object node : table.getHierarchyData())
							results.add(node);
					else{
						//business.findHierarchy( master);
						//System.out.println("AbstractCrudManyPage.initialisation().new RowAdapter() {...}.load()");
						//debug(table.getMaster());
						table.setMaster((AbstractIdentifiable) table.getReferenceFromHierarchy(table.getMaster(),table.getHierarchyData()));
						if( ((AbstractDataTreeNode)table.getMaster()).getChildren()!=null)
							for(AbstractDataTreeNode node : ((AbstractDataTreeNode)table.getMaster()).getChildren())
								results.add(node);	
					}
					
				}else{
					Collection<ENTITY> records = null;
					Class<AbstractIdentifiable> identifiableClass = (Class<AbstractIdentifiable>) businessEntityInfos.getClazz();
					if(Boolean.TRUE.equals(table.getLazyLoad())){
						if(Boolean.TRUE.equals(table.getGlobalFilter()))
							records = (Collection<ENTITY>) BusinessServiceProvider.getInstance().find(identifiableClass, configuration);
						else
							records = (Collection<ENTITY>) BusinessServiceProvider.getInstance().find(identifiableClass, configuration);
					}else
						records = (Collection<ENTITY>) RootBusinessLayer.getInstance().getGenericBusiness().use(identifiableClass).find().all();
					
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
			public Long count(DataReadConfiguration configuration) {
				if(Boolean.TRUE.equals(table.isDataTreeType())){
					
				}else{
					Class<AbstractIdentifiable> identifiableClass = (Class<AbstractIdentifiable>) businessEntityInfos.getClazz();
					if(Boolean.TRUE.equals(table.getLazyLoad())){
						if(Boolean.TRUE.equals(table.getGlobalFilter()))
							return BusinessServiceProvider.getInstance().count(identifiableClass, configuration);
						else
							return BusinessServiceProvider.getInstance().count(identifiableClass, configuration);
					}else
						return UIManager.getInstance().getGenericBusiness().use(identifiableClass).find(Function.COUNT).oneLong();
				}
				return super.count(configuration);
			}
		});	
				
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
	
	/*@Override
	protected Class<?> __formModelClass__() {
		if(AbstractEnumeration.class.isAssignableFrom(businessEntityInfos.getClazz()))
			return EnumerationForm.class;
		return super.__formModelClass__();
	}*/
	
	/**/
	
	

}
