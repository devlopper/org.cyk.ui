package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.omnifaces.util.Faces;

@Getter @Setter
public abstract class AbstractCrudManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormManyPage<ENTITY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		table.setEditable(Boolean.TRUE);
		table.getSearchCommandable().getCommand().getCommandListeners().add(this);
		if(AbstractDataTreeNode.class.isAssignableFrom(identifiableClass)){
			for(Object object : ((AbstractDataTreeNodeBusiness<?>) getBusiness()).findRoots()){
				table.getHierarchyData().add(object);
			}
		}
		
		table.getRowListeners().add(new RowAdapter<Object>(getUserSession()){
			private static final long serialVersionUID = 1L;

			@Override
			public AbstractUserSession<?, ?> getUserSession() {
				return AbstractCrudManyPage.this.userSession;
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
				
				if(StringUtils.isBlank(configuration.getGlobalFilter())){
					if(AbstractDataTreeNode.class.isAssignableFrom(identifiableClass))
						records = (Collection<ENTITY>) ((AbstractDataTreeNodeBusiness<?>) getBusiness()).findRoots();
					else
						records = getBusiness().findAll(configuration);
				}else{
					records = getBusiness().findByString(configuration.getGlobalFilter(),null,configuration);
				}
				
				results = datas(records);
				logMessageBuilder.addParameters("results count",results.size());
				logTrace(logMessageBuilder);
				return results;
			}
			
			@Override
			public Long count(DataReadConfiguration configuration) {
				if(StringUtils.isBlank(configuration.getGlobalFilter())){
					if(AbstractDataTreeNode.class.isAssignableFrom(identifiableClass))
						return ((AbstractDataTreeNodeBusiness<?>) getBusiness()).countRoots();
					else
						return getBusiness().countAll();
				}else{
					return getBusiness().countByString(configuration.getGlobalFilter());
				}
			}
		});	
		
		table.setShowOpenCommand(Boolean.TRUE);
		
		/*
		table.setShowHeader(Boolean.TRUE);
		table.setShowToolBar(Boolean.TRUE);
		
		table.setShowFooter(Boolean.FALSE);
		*/
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		if(table.getApplyRowEditCommandable().getCommand()==command){
			//getGenericBusiness().save(identifiable);
		}else if(table.getRemoveRowCommandable().getCommand()==command){
			//getGenericBusiness().delete(identifiable);
		}else if(table.getSearchCommandable().getCommand()==command){
			for(Entry<String, String[]> entry :  Faces.getRequest().getParameterMap().entrySet())
				if(entry.getKey().contains(":globalFilter") && entry.getValue()!=null && entry.getValue().length>0 && StringUtils.isNotBlank(entry.getValue()[0]))
					navigationManager.redirectToCrudMany(identifiableClass, identifiable, new Object[]{UniformResourceLocatorParameter.FILTER,entry.getValue()[0]});
		}
	}	

}
