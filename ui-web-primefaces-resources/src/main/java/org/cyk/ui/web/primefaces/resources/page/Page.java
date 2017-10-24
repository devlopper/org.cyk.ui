package org.cyk.ui.web.primefaces.resources.page;

import java.io.Serializable;

import org.cyk.ui.web.primefaces.resources.page.layout.LayoutModel;
import org.cyk.ui.web.primefaces.resources.page.layout.NorthEastSouthWestCenter;
import org.cyk.utility.common.userinterface.container.Window;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Page extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String CONTRACTS = "defaultDesktop";
	public static final String LAYOUT_TEMPLATE = "/template/default.xhtml";
	public static final String TEMPLATE = "/org.cyk.ui.web.primefaces/template/page/desktop/default.xhtml";
		
	@Override
	protected void initialisation() {
		super.initialisation();
		getPropertiesMap().setTemplate(TEMPLATE);
		getPropertiesMap().setLayout(createLayoutModel());
		
		getLayout().getPropertiesMap().setTemplate(LAYOUT_TEMPLATE);
		getLayout().getPropertiesMap().setContracts(CONTRACTS);
	}
		
	protected LayoutModel createLayoutModel(){
		return new NorthEastSouthWestCenter();
	}
	
}
