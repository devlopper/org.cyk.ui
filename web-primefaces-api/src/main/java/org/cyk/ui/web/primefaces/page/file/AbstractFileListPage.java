package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractFileListPage<FILE extends AbstractIdentifiable> extends AbstractCrudManyPage<FILE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	public static abstract class AbstractPageAdapter<FILE extends AbstractIdentifiable> extends AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener.Adapter.Default<FILE> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractPageAdapter(Class<FILE> entityTypeClass) {
			super(entityTypeClass);
			FormConfiguration configuration = getFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(AbstractOutputDetails.FIELD_CODE,AbstractOutputDetails.FIELD_NAME);
		}
		
	}
}
