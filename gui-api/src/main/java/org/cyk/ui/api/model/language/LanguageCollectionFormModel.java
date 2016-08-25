package org.cyk.ui.api.model.language;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.language.LanguageCollection;
import org.cyk.system.root.model.language.LanguageCollectionItem;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LanguageCollectionFormModel extends AbstractFormModel<LanguageCollection> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Language language1;
	@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Language language2;
	
	@Override
	public void write() {
		super.write();
		updateLanguageAtIndex(0, language1);
		updateLanguageAtIndex(1, language2);
	}
	
	@Override
	public void read() {
		super.read();
		language1 = readLanguageAtIndex(0);
		language2 = readLanguageAtIndex(1);
	}
	
	protected Language readLanguageAtIndex(Integer index){
		if(identifiable==null)
			return null;
		if(identifiable.getCollection()==null)
			identifiable.setCollection(new ArrayList<LanguageCollectionItem>());
		if(identifiable.getCollection().isEmpty())
			return null;
		if(identifiable.getCollection() instanceof List && index < identifiable.getCollection().size()){
			return ((List<LanguageCollectionItem>)identifiable.getCollection()).get(index.intValue()).getLanguage();
		}
		Iterator<LanguageCollectionItem> iterator = identifiable.getCollection().iterator();
		Integer count = -1;
		Language languageIndex = null;
		while(iterator.hasNext()){
			languageIndex = iterator.next().getLanguage();
			if(++count == index)
				return languageIndex;
		}
		return null;
	}
	
	protected void updateLanguageAtIndex(Integer index,Language language){
		if(identifiable==null)
			return;
		if(identifiable.getCollection() instanceof List){
			if(language == null){
				if(identifiable.getCollection().size()>index)
					((List<LanguageCollectionItem>)identifiable.getCollection()).remove(index.intValue());
			}else if(identifiable.getCollection()!=null)
				if(identifiable.getCollection().size()>index)
					((List<LanguageCollectionItem>)identifiable.getCollection()).get(index.intValue()).setLanguage(language);
				else{
					LanguageCollectionItem item = new LanguageCollectionItem();
					item.setLanguage(language);
					((List<LanguageCollectionItem>)identifiable.getCollection()).add(item);
				}
		}else{
			Iterator<LanguageCollectionItem> iterator = identifiable.getCollection().iterator();
			Integer count = -1;
			if(language==null)
				;//identifiable.getCollection().r;
			else
				while(iterator.hasNext()){
					if(++count == index){
						iterator.next().setLanguage(language);
						break;
					}
				}
		}
		
	}
	
	public static final String FIELD_LANGUAGE_1 = "language1";
	public static final String FIELD_LANGUAGE_2 = "language2";
	
}
