package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.data.collector.form.FormOneData;

@Getter @Setter
public class DetailsBlockCollection<MENU_MODEL> implements Serializable {

	private static final long serialVersionUID = -1962971794962204899L;

	private List<DetailsBlock<MENU_MODEL>> collection = new ArrayList<>();

	public enum RenderType{}
	
	public boolean add(DetailsBlock<MENU_MODEL> block) {
		return collection.add(block);
	}
	
	public boolean add(FormOneData<?, ?, ?, ?, ?, ?> formOneData) {
		DetailsBlock<MENU_MODEL> block = new DetailsBlock<MENU_MODEL>();
		block.setFormOneData(formOneData);
		block.setTitle(formOneData.getTitle());
		return add(block);
	}
	
	public Boolean hasIndex(Integer index){
		return index < collection.size();
	}
	
	public DetailsBlock<MENU_MODEL> get(Integer index){
		if(hasIndex(index))
			return collection.get(index.intValue());
		return get(0);
	}
	
}
