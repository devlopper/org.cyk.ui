package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.ui.api.data.collector.form.FormOneData;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetailsBlockCollection implements Serializable {

	private static final long serialVersionUID = -1962971794962204899L;

	private Collection<DetailsBlock> collection = new ArrayList<>();

	public boolean add(DetailsBlock block) {
		return collection.add(block);
	}
	
	public boolean add(FormOneData<?, ?, ?, ?, ?, ?> formOneData) {
		DetailsBlock block = new DetailsBlock();
		block.setFormOneData(formOneData);
		block.setTitle(formOneData.getTitle());
		return add(block);
	}
	
}
