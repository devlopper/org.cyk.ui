package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.ui.api.data.collector.form.FormOneData;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetailsBlockCollection<MENU_MODEL> implements Serializable {

	private static final long serialVersionUID = -1962971794962204899L;

	private Collection<DetailsBlock<MENU_MODEL>> collection = new ArrayList<>();

	public boolean add(DetailsBlock<MENU_MODEL> block) {
		return collection.add(block);
	}
	
	public boolean add(FormOneData<?, ?, ?, ?, ?, ?> formOneData) {
		DetailsBlock<MENU_MODEL> block = new DetailsBlock<MENU_MODEL>();
		block.setFormOneData(formOneData);
		block.setTitle(formOneData.getTitle());
		return add(block);
	}
	
}
