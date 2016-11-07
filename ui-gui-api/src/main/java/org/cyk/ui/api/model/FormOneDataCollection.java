package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cyk.ui.api.data.collector.form.FormOneData;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FormOneDataCollection implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<FormOneData<?, ?, ?, ?, ?, ?>> collection = new ArrayList<>();
	
	public void add(FormOneData<?, ?, ?, ?, ?, ?>...formOneDatas){
		if(formOneDatas!=null)
			for(FormOneData<?, ?, ?, ?, ?, ?> formOneData : formOneDatas){
				collection.add(formOneData);
				formOneData.getSubmitCommandable().setRendered(collection.size()==1);
			}
	}
	
	public Boolean isFormOneDataAt(FormOneData<?, ?, ?, ?, ?, ?> formOneData,Integer index){
		return Boolean.TRUE.equals(hasFormOneDataAt(index)) && collection.get(index.intValue()) == formOneData ;
	}
	
	public Boolean hasFormOneDataAt(Integer index){
		return index!=null && index < collection.size();
	}
	
	public FormOneData<?, ?, ?, ?, ?, ?> getFormOneDataAt(Integer index){
		if(hasFormOneDataAt(index))
			return collection.get(index.intValue());
		//return null;//FIXME returning null will trigger decorator exception
		return getFormOneDataAt(0);
	}
	
	public String getFormOneDataTitleAt(Integer index){
		FormOneData<?, ?, ?, ?, ?, ?> formOneData = getFormOneDataAt(index);
		if(formOneData==null)
			return null;
		return formOneData.getTitle();
	}
	
	public String getFormOneDataTabTitleAt(Integer index){
		FormOneData<?, ?, ?, ?, ?, ?> formOneData = getFormOneDataAt(index);
		if(formOneData==null)
			return null;
		return formOneData.getTabTitle();
	}
	
}
