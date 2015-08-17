package org.cyk.ui.api.model.geography;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;

@Getter @Setter
public abstract class AbstractContactCollectionFormModel extends AbstractFormModel<ContactCollection> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;
	
	protected PhoneNumber phoneNumber(PhoneNumberType type){
		for(PhoneNumber phoneNumber : identifiable.getPhoneNumbers())
			if(phoneNumber.getType().equals(type))
				return phoneNumber;
		return null;
	}
	
	protected void updatePhoneNumber(PhoneNumberType type,String number){
		PhoneNumber phoneNumber = phoneNumber(type);
		if(phoneNumber ==null){
			if(!StringUtils.isBlank(number)){
				phoneNumber = new PhoneNumber();
				phoneNumber.setType(type);
				phoneNumber.setNumber(number);
				phoneNumber.setCountry(RootBusinessLayer.getInstance().getCountryCoteDivoire());
				identifiable.getPhoneNumbers().add(phoneNumber);
			}
		}else{
			if(StringUtils.isBlank(number))
				deletePhoneNumber(type);
			else
				phoneNumber.setNumber(number);
		}
	}
	
	protected void deletePhoneNumber(PhoneNumberType type){
		PhoneNumber phoneNumber = phoneNumber(type);
		if(phoneNumber!=null)
			identifiable.getPhoneNumbers().remove(phoneNumber);
	}
	
	protected String readPhoneNumber(PhoneNumberType type){
		if(identifiable.getPhoneNumbers()==null)
			identifiable.setPhoneNumbers(new ArrayList<PhoneNumber>());
		for(PhoneNumber p : identifiable.getPhoneNumbers())
			if(p.getType().equals(type))
				return p.getNumber();
		return null;
	}
	
	/**/
	
	protected Location location(LocationType type){
		for(Location location : identifiable.getLocations())
			if(location.getType().equals(type))
				return location;
		return null;
	}
	
	protected void updateLocation(LocationType type,String comments){
		Location location = location(type);
		if(location ==null){
			if(!StringUtils.isBlank(comments)){
				location = new Location();
				location.setType(type);
				location.setComment(comments);
				identifiable.getLocations().add(location);
			}
		}else{
			if(StringUtils.isBlank(comments))
				deleteLocation(type);
			else
				location.setComment(comments);
		}
	}
	
	protected void deleteLocation(LocationType type){
		Location location = location(type);
		if(location==null)
			identifiable.getLocations().remove(location);
	}
	
	protected String readLocation(LocationType type){
		if(identifiable.getLocations()==null)
			identifiable.setLocations(new ArrayList<Location>());
		for(Location p : identifiable.getLocations())
			if(p.getType().equals(type))
				return p.getComment();
		return null;
	}
	
	/**/
	
	protected String readElectronicMail(){
		if(identifiable.getElectronicMails()==null)
			identifiable.setElectronicMails(new ArrayList<ElectronicMail>());
		if(identifiable.getElectronicMails().isEmpty())
			return null;
		return identifiable.getElectronicMails().iterator().next().getAddress();
	}
	
	protected void updateElectronicMail(String address){
		if(StringUtils.isBlank(address))
			identifiable.getElectronicMails().clear();
		else{
			if(identifiable.getElectronicMails().isEmpty()){
				ElectronicMail em = new ElectronicMail(address);
				identifiable.getElectronicMails().add(em);
			}else
				identifiable.getElectronicMails().iterator().next().setAddress(address);
		}
	}
	
	/**/
	
	protected String readPostalBox(){
		if(identifiable.getPostalBoxs()==null)
			identifiable.setPostalBoxs(new ArrayList<PostalBox>());
		if(identifiable.getPostalBoxs().isEmpty())
			return null;
		return identifiable.getPostalBoxs().iterator().next().getValue();
	}
	
	protected void updatePostalBox(String address){
		if(StringUtils.isBlank(address))
			identifiable.getPostalBoxs().clear();
		else{
			if(identifiable.getPostalBoxs().isEmpty()){
				PostalBox pb = new PostalBox(address);
				identifiable.getPostalBoxs().add(pb);
			}else
				identifiable.getPostalBoxs().iterator().next().setValue(address);
		}
	}
	
}
