package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface IdentifierProvider{

	Collection<IdentifierProvider> COLLECTION = new ArrayList<>();
	
	/* View */
	
	String getView(Class<?> aClass,CommonBusinessAction commonBusinessAction,Boolean one);
	String getViewDynamic(CommonBusinessAction commonBusinessAction,Boolean one);
	String getViewDynamicReport();
	String getViewGlobalIdentifierEdit();
		
	/**/
	
	public static class Adapter extends BeanAdapter implements IdentifierProvider,Serializable{
		private static final long serialVersionUID = 748871774704504356L;
	
		/**/
		
		@Override
		public String getView(Class<?> aClass, CommonBusinessAction commonBusinessAction, Boolean one) {
			return null;
		}
		
		@Override
		public String getViewDynamic(CommonBusinessAction commonBusinessAction, Boolean one) {
			return null;
		}
		@Override
		public String getViewDynamicReport() {
			return null;
		}
		@Override
		public String getViewGlobalIdentifierEdit() {
			return null;
		}
		public static class Default extends Adapter implements Serializable{
			private static final long serialVersionUID = 748871774704504356L;
			
			@Override
			public String getView(Class<?> aClass, CommonBusinessAction commonBusinessAction, Boolean one) {
				if(commonBusinessAction==null)
					return null;
				BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(aClass);
				String identifier=null;
				if(Boolean.TRUE.equals(one)){
					switch(commonBusinessAction){
					case CREATE:identifier=businessEntityInfos.getUserInterface().getEditViewId();break;
					case READ:identifier=businessEntityInfos.getUserInterface().getConsultViewId();break;
					case UPDATE:identifier=businessEntityInfos.getUserInterface().getEditViewId();break;
					case DELETE:identifier=businessEntityInfos.getUserInterface().getEditViewId();break;
					case CONSULT:identifier=businessEntityInfos.getUserInterface().getConsultViewId();break;
					case LIST:identifier=businessEntityInfos.getUserInterface().getListViewId();break;
					case SELECT:identifier=businessEntityInfos.getUserInterface().getSelectOneViewId();break;
					case SEARCH:identifier=businessEntityInfos.getUserInterface().getListViewId();break;
					case PRINT:identifier=businessEntityInfos.getUserInterface().getPrintViewId();break;
					}	
				}else{
					switch(commonBusinessAction){ 
					case CREATE:identifier=businessEntityInfos.getUserInterface().getCreateManyViewId();break;
					case READ:identifier=null;break;
					case UPDATE:identifier=null;break;
					case DELETE:identifier=null;break;
					case CONSULT:identifier=null;break;
					case LIST:identifier=businessEntityInfos.getUserInterface().getListViewId();break;
					case SELECT:identifier=businessEntityInfos.getUserInterface().getSelectManyViewId();break;
					case SEARCH:identifier=businessEntityInfos.getUserInterface().getListViewId();break;
					case PRINT:identifier=businessEntityInfos.getUserInterface().getPrintViewId();break;
					}	
				}
				
				if(StringUtils.isEmpty(identifier))
					identifier = getViewDynamic(commonBusinessAction, one);
				
				if(StringUtils.isEmpty(identifier) && businessEntityInfos.getCrudStrategy().equals(CrudStrategy.BUSINESS))
					logWarning("No view identifier found for {} {} {}", aClass.getSimpleName(),commonBusinessAction,Boolean.TRUE.equals(one) ? "one":"many");
				return identifier;
			}
			
		}

	}
}