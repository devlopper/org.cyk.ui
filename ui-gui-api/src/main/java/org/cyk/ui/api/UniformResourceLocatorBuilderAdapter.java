package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;

public class UniformResourceLocatorBuilderAdapter extends org.cyk.system.root.business.impl.network.UniformResourceLocatorBuilderAdapter implements Serializable {

	private static final long serialVersionUID = -1574930954646341195L;

	@Override
	public String getAddress(CommonBusinessAction commonBusinessAction, Object object) {
		BusinessEntityInfos businessEntityInfos = inject(ApplicationBusiness.class).findBusinessEntityInfos(((AbstractIdentifiable)object).getClass());
		switch(commonBusinessAction){
		case CONSULT:
			if(CrudStrategy.BUSINESS.equals(businessEntityInfos.getCrudStrategy()))
				return UIManager.getInstance().getViewPath(IdentifierProvider.Adapter.getViewOf(object.getClass(), commonBusinessAction, Boolean.TRUE));
		default:
		}
		return super.getAddress(commonBusinessAction, object);
	}
	
	@Override
	public Collection<UniformResourceLocatorParameter> getParameters(CommonBusinessAction commonBusinessAction,Object object) {
		BusinessEntityInfos businessEntityInfos = inject(ApplicationBusiness.class).findBusinessEntityInfos(((AbstractIdentifiable)object).getClass());
		switch(commonBusinessAction){
		case CONSULT:
			if(CrudStrategy.BUSINESS.equals(businessEntityInfos.getCrudStrategy())){
				return Arrays.asList(UniformResourceLocatorParameter.Builder.createIdentifiable((AbstractIdentifiable) object));
			}
		default:
		}
		return super.getParameters(commonBusinessAction, object);
	}
	
}
