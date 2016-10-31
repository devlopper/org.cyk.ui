package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.file.FileIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.globalidentification.JoinGlobalIdentifierBusiness;
import org.cyk.system.root.business.impl.file.FileIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier.SearchCriteria;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;


@Getter @Setter
public class FileIdentifiableGlobalIdentifiers extends AbstractJoinGlobalIdentifiers<FileIdentifiableGlobalIdentifier,FileIdentifiableGlobalIdentifiers.FileIdentifiableGlobalIdentifiersListener,FileIdentifiableGlobalIdentifierDetails,FileIdentifiableGlobalIdentifier.SearchCriteria> implements CommandListener, Serializable {

	private static final long serialVersionUID = 2876480260626169563L;

	public FileIdentifiableGlobalIdentifiers(AbstractPrimefacesPage page,Class<FileIdentifiableGlobalIdentifierDetails> detailsClass, AbstractIdentifiable identifiable) {
		super(page, detailsClass, identifiable);
	}
	
	@Override
	protected AbstractTableAdapter<FileIdentifiableGlobalIdentifier, FileIdentifiableGlobalIdentifierDetails, SearchCriteria> createTableAdapter(AbstractIdentifiable identifiable) {
		return new TableAdapter(identifiable);
	}

	@Override
	protected Boolean isUserDefinedObject(AbstractIdentifiable comment) {
		return FileIdentifiableGlobalIdentifier.isUserDefinedObject(comment);
	}
	
	/**/
	
	public static interface FileIdentifiableGlobalIdentifiersListener extends org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifiers.AbstractJoinGlobalIdentifiersListener {
		
	}
	
	/**/
	
	public static class TableAdapter extends AbstractTableAdapter<FileIdentifiableGlobalIdentifier, FileIdentifiableGlobalIdentifierDetails, FileIdentifiableGlobalIdentifier.SearchCriteria> implements Serializable{

		private static final long serialVersionUID = -4500309183317753415L;

		public TableAdapter(AbstractIdentifiable identifiable) {
			super(FileIdentifiableGlobalIdentifier.class,FileIdentifiableGlobalIdentifierDetails.class, identifiable);
		}

		@Override
		protected SearchCriteria createSearchCriteria() {
			SearchCriteria searchCriteria = new SearchCriteria();
			return searchCriteria;
		}

		@Override
		protected JoinGlobalIdentifierBusiness<FileIdentifiableGlobalIdentifier, SearchCriteria> getBusiness() {
			return inject(FileIdentifiableGlobalIdentifierBusiness.class);
		}
		
	}

	
}

