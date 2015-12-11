package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.cdi.AbstractBean;

public class FormMap extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6946914245453887414L;

	private final Map<Crud,Map<String, Class<?>>> one = new HashMap<>() , many = new HashMap<>();
	
	/**/
	
	public FormMap(Class<? extends AbstractIdentifiable> identifiableClass,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> editOneFormModelClass,
			Class<?> readOneFormModelClass) {
		BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(identifiableClass);
		
		put(Boolean.TRUE, Crud.CREATE, editOneFormModelClass);
		put(Boolean.TRUE, Crud.READ, readOneFormModelClass);
		put(Boolean.TRUE, Crud.UPDATE, editOneFormModelClass);
		put(Boolean.TRUE, Crud.DELETE, readOneFormModelClass);
		
		put(Boolean.FALSE, Crud.READ, readOneFormModelClass);
		
		if(businessEntityInfos==null){
			
		}else{
			if(get(Boolean.TRUE, Crud.CREATE) != null)
				businessEntityInfos.getUserInterface().setEditViewId(null);
			if(get(Boolean.TRUE, Crud.READ) != null)
				businessEntityInfos.getUserInterface().setConsultViewId(null);
			if(get(Boolean.FALSE, Crud.READ) != null)
				businessEntityInfos.getUserInterface().setListViewId(null);	
		}
		
	}
	
	/**/
	
	public Class<?> get(Boolean one,Crud crud,String type){
		return __get__(map(one),crud,type==null?FormConfiguration.TYPE_INPUT_SET_DEFAULT:type);
	}
	public Class<?> get(Boolean one,Crud crud){
		return get(one, crud,null);
	}
	public Class<?> getOne(Crud crud){
		return get(Boolean.TRUE, crud);
	}
	public Class<?> getEditOne(Boolean update){
		return getOne(Boolean.TRUE.equals(update)?Crud.UPDATE:Crud.CREATE);
	}
	
	public Class<?> getMany(Crud crud){
		return get(Boolean.FALSE, crud);
	}
	public Class<?> getManyRead(){
		return getMany(Crud.READ);
	}
	
	public void put(Boolean one,Crud crud,String type,Class<?> aClass){
		__put__(map(one), crud, type==null?FormConfiguration.TYPE_INPUT_SET_DEFAULT:type, aClass);
	}
	public void put(Boolean one,Crud crud,Class<?> aClass){
		put(one, crud, null, aClass);
	}
	
	public void putEdit(Boolean one,String type,Class<?> aClass){
		put(one, Crud.CREATE, type, aClass);
		put(one, Crud.UPDATE, type, aClass);
	}
	public void putEdit(Boolean one,Class<?> aClass){
		putEdit(one, null, aClass);
	}
	
	/**/
	
	private Class<?> __get__(Map<Crud,Map<String, Class<?>>> map,Crud crud,String type){
		return map.get(crud).get(type);
	}
	
	private void __put__(Map<Crud,Map<String, Class<?>>> map,Crud crud,String type,Class<?> aClass){
		Map<String, Class<?>> typeMap = map.get(crud);
		if(typeMap==null){
			typeMap = new HashMap<>();
			map.put(crud, typeMap);
		}
		typeMap.put(type, aClass);
	}
	
	private Map<Crud,Map<String, Class<?>>> map(Boolean one){
		return Boolean.TRUE.equals(one)?this.one:this.many;
	}
	
	/**/

}
