package org.cyk.ui.web.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import org.cyk.system.root.model.party.person.Sex;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class SelectItemHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = 1L;

	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		org.cyk.utility.common.helper.SelectItemHelper.Builder.One.Adapter.Default.DEFAULT_CLASS = SelectItemHelper.OneBuilder.class;
	}
	
	@Test
	public void selectItem(){
		Sex masculin = new Sex("M", "Masculin");
		SelectItem selectItem = new SelectItemHelper.OneBuilder().setInstance(masculin).execute();
		assertEquals("Masculin", selectItem.getLabel());
	}
	
	@Test
	public void selectItems(){
		Collection<Sex> sexs = new ArrayList<>();
		sexs.add(new Sex("M", "Masculin"));
		sexs.add(new Sex("F", "Feminin"));
		List<SelectItem> selectItems = new org.cyk.utility.common.helper.SelectItemHelper.Builder.Many.Adapter.Default<SelectItem>()
				.setInstances(sexs).execute();
		assertEquals("Masculin", selectItems.get(0).getLabel());
		assertEquals("Feminin", selectItems.get(1).getLabel());
	}
	
	@Test
	public void selectItemsWithNull(){
		Collection<Sex> sexs = new ArrayList<>();
		sexs.add(new Sex("M", "Masculin"));
		sexs.add(new Sex("F", "Feminin"));
		List<SelectItem> selectItems = new org.cyk.utility.common.helper.SelectItemHelper.Builder.Many.Adapter.Default<SelectItem>().setNullable(Boolean.TRUE)
				.setInstances(sexs).execute();
		assertEquals("--Choisir--", selectItems.get(0).getLabel());
		assertEquals("Masculin", selectItems.get(1).getLabel());
		assertEquals("Feminin", selectItems.get(2).getLabel());
	}
}
