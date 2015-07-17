package org.cyk.ui.api.model.party;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractPersonConsultFormModel<ENTITY extends AbstractIdentifiable> extends AbstractFormModel<ENTITY>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) private File photo;
	
	@Input @InputText private String firstName,lastName,contacts;
	
	protected abstract Person getPerson();
	
	@Override
	public void read() {
		photo = getPerson().getImage();
		firstName = getPerson().getName();
		lastName = getPerson().getLastName();
		if(getPerson().getContactCollection()!=null){
			UIManager.getInstance().getContactCollectionBusiness().load(getPerson().getContactCollection());//TODO should be done with many and not one to improve speed
			StringBuilder s = new StringBuilder();
			Boolean newLine = Boolean.FALSE;
			newLine = appendContacts(s,getPerson().getContactCollection().getPhoneNumbers(),newLine);
			newLine = appendContacts(s,getPerson().getContactCollection().getElectronicMails(),newLine);
			newLine = appendContacts(s,getPerson().getContactCollection().getLocations(),newLine);
			newLine = appendContacts(s,getPerson().getContactCollection().getPostalBoxs(),newLine);
			contacts = s.toString();
		}
	}
	
	private Boolean appendContacts(StringBuilder builder,Collection<?> collection,Boolean newLine){
		if(collection!=null && !collection.isEmpty()){
			if(Boolean.TRUE.equals(newLine))
				builder.append(UIManager.CONTENT_TYPE.getNewLineMarker());
			builder.append(StringUtils.join(collection,","));
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
		
}
