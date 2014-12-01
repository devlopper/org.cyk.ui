package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.security.License;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.validation.Client;

public abstract class AbstractLicensePage extends AbstractBusinessEntityFormOnePage<License> implements Serializable {

	private static final long serialVersionUID = -3563847253553434464L;
	
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected LicenseBusiness licenseBusiness;
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(License.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass) {
		return (T) applicationBusiness.findCurrentInstance().getLicense();
	}
	
	@Override
	protected Object data(Class<?> aClass) {
		return new DefaultLicenseFormModel(identifiable);
	}
	
	/**/
	@Getter @Setter
	public static abstract class AbstractLicenseFormModel extends AbstractFormModel<License> {

		private static final long serialVersionUID = 2646571878912106597L;
		
		@Input
		@InputCalendar
		@NotNull(groups={Client.class}) @Temporal(TemporalType.TIMESTAMP)
		protected Date expirationDate;
		
		public AbstractLicenseFormModel(License license) {
			identifiable = license;
			expirationDate = license.getPeriod().getToDate();
		}
		
		@Override
		public void write() {
			super.write();
			identifiable.getPeriod().setToDate(expirationDate);
		}
		
	}
	
	@Getter @Setter
	public static class DefaultLicenseFormModel extends AbstractLicenseFormModel {

		private static final long serialVersionUID = 2646571878912106597L;
		
		public DefaultLicenseFormModel(License license) {
			super(license);
		}
		
	}
	
}
