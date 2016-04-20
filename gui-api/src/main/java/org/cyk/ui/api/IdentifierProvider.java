package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.utility.common.cdi.BeanAdapter;

public interface IdentifierProvider{

	Collection<IdentifierProvider> COLLECTION = new ArrayList<>();
	
	/* View */
	
	String getView(Class<?> aClass,Crud crud,Boolean one);
	String getDynamicView(Crud crud,Boolean one);
	String getDynamicReportView();
	
	/* Parameter */
	
	String getParameterClass();
	String getParameterIdentifiable();
	String getParameterFileExtension();
	String getParameterWindowMode();
	String getParameterReportIdentifier();
	String getParameterViewIdentifier();
	String getParameterPrint();
	
	/**/
	
	public static class Adapter extends BeanAdapter implements IdentifierProvider,Serializable{
		private static final long serialVersionUID = 748871774704504356L;
	
		/**/
		
		@Override
		public String getView(Class<?> aClass, Crud crud, Boolean one) {
			BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(aClass);
			String identifier=null;
			if(Boolean.TRUE.equals(one)){
				switch(crud){
				case CREATE:identifier=businessEntityInfos.getUserInterface().getEditViewId();break;
				case READ:identifier=businessEntityInfos.getUserInterface().getConsultViewId();break;
				case UPDATE:identifier=businessEntityInfos.getUserInterface().getEditViewId();break;
				case DELETE:identifier=businessEntityInfos.getUserInterface().getEditViewId();break;
				}
				if(StringUtils.isEmpty(identifier))
					identifier = getDynamicView(crud, one);
			}else{
				
			}
			if(StringUtils.isEmpty(identifier))
				logWarning("No view identifier found for {} {} {}", aClass.getSimpleName(),crud,Boolean.TRUE.equals(one) ? "one":"many");
			return identifier;
		}
		
		@Override
		public String getDynamicView(Crud crud, Boolean one) {
			return null;
		}
		@Override
		public String getDynamicReportView() {
			return null;
		}
		@Override
		public String getParameterClass() {
			return null;
		}

		@Override
		public String getParameterIdentifiable() {
			return null;
		}

		@Override
		public String getParameterFileExtension() {
			return null;
		}

		@Override
		public String getParameterWindowMode() {
			return null;
		}

		@Override
		public String getParameterReportIdentifier() {
			return null;
		}

		@Override
		public String getParameterViewIdentifier() {
			return null;
		}

		@Override
		public String getParameterPrint() {
			return null;
		}
		
		public static class Default extends Adapter implements Serializable{
			private static final long serialVersionUID = 748871774704504356L;
			
		}

	}
}