package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface IdentifierProvider{

	Collection<IdentifierProvider> COLLECTION = new ArrayList<>();
	
	/* View */
	
	String getView(Class<?> aClass,CommonBusinessAction commonBusinessAction,Boolean one);
	String getViewDynamic(CommonBusinessAction commonBusinessAction,Boolean one);
	String getViewDynamicReport();
	String getViewGlobalIdentifierEdit();
	
	String getTab(Class<?> identifiableClass,Class<?> detailsClass);
	
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
		@Override
		public String getTab(Class<?> identifiableClass,Class<?> detailsClass) {
			return null;
		}
		
		public static String getTabOf(final Class<?> identifiableClass,final Class<?> detailsClass) {
			return ListenerUtils.getInstance().getString(COLLECTION, new ListenerUtils.StringMethod<IdentifierProvider>() {
				@Override
				public String execute(IdentifierProvider provider) {
					return provider.getTab(identifiableClass,detailsClass);
				}
			});
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
			
			@Override
			public String getTab(Class<?> identifiableClass,Class<?> detailsClass) {
				Field field = FieldUtils.getDeclaredField(detailsClass, "LABEL_IDENTIFIER", Boolean.TRUE);
				if(field == null){
					return UIManager.getInstance().businessEntityInfos(identifiableClass).getUserInterface().getLabelId();
				}else{
					try {
						return (String) FieldUtils.readStaticField(field);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
			
		}

	}
}