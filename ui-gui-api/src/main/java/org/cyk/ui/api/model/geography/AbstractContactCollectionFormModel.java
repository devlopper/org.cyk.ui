package org.cyk.ui.api.model.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractContactCollectionFormModel extends AbstractFormModel<ContactCollection> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;
	
	protected PhoneNumber phoneNumber(PhoneNumberType type){
		/*if(identifiable!=null && identifiable.getPhoneNumbers()!=null)
			for(PhoneNumber phoneNumber : identifiable.getPhoneNumbers())
				if(phoneNumber.getType().equals(type))
					return phoneNumber;*/
		return null;
	}
	
	protected void updatePhoneNumber(PhoneNumberType type,String number){
		/*if(identifiable==null)
			return;
		PhoneNumber phoneNumber = phoneNumber(type);
		if(phoneNumber ==null){
			if(!StringUtils.isBlank(number)){
				phoneNumber = new PhoneNumber();
				phoneNumber.setType(type);
				phoneNumber.setNumber(number);
				phoneNumber.setCountry(inject(CountryBusiness.class).find(RootConstant.Code.Country.COTE_DIVOIRE));
				identifiable.getPhoneNumbers().add(phoneNumber);
			}
		}else{
			if(StringUtils.isBlank(number))
				deletePhoneNumber(type);
			else
				phoneNumber.setNumber(number);
		}
		*/
	}
	
	protected void deletePhoneNumber(PhoneNumberType type){
		/*PhoneNumber phoneNumber = phoneNumber(type);
		if(phoneNumber!=null)
			identifiable.getPhoneNumbers().remove(phoneNumber);*/
	}
	
	protected String readPhoneNumber(PhoneNumberType type){
		/*if(identifiable==null)
			return null;
		if(identifiable.getPhoneNumbers()==null)
			identifiable.setPhoneNumbers(new ArrayList<PhoneNumber>());
		for(PhoneNumber p : identifiable.getPhoneNumbers())
			if(p.getType().equals(type))
				return p.getNumber();*/
		return null;
	}
	
	/**/
	
	protected Location location(LocationType type){
		/*for(Location location : identifiable.getLocations())
			if(location.getType().equals(type))
				return location;*/
		return null;
	}
	
	protected void updateLocation(LocationType type,String comments){
		/*if(identifiable==null)
			return;
		Location location = location(type);
		if(location ==null){
			if(!StringUtils.isBlank(comments)){
				location = new Location();
				location.setType(type);
				location.setOtherDetails(comments);
				identifiable.getLocations().add(location);
			}
		}else{
			if(StringUtils.isBlank(comments))
				deleteLocation(type);
			else
				location.setOtherDetails(comments);
		}*/
	}
	
	protected void deleteLocation(LocationType type){
		/*Location location = location(type);
		if(location==null)
			identifiable.getLocations().remove(location);*/
	}
	
	protected String readLocation(LocationType type){
		/*if(identifiable==null)
			return null;
		if(identifiable.getLocations()==null)
			identifiable.setLocations(new ArrayList<Location>());
		for(Location p : identifiable.getLocations())
			if(p.getType()!=null && p.getType().equals(type))
				return p.getOtherDetails();*/
		return null;
	}
	
	/**/
	
	protected String readElectronicMail(Integer index){
		/*if(identifiable==null)
			return null;
		if(identifiable.getElectronicMails()==null)
			identifiable.setElectronicMails(new ArrayList<ElectronicMail>());
		if(identifiable.getElectronicMails().isEmpty())
			return null;
		return index >= identifiable.getElectronicMails().size() ? null : new ArrayList<>(identifiable.getElectronicMails()).get(index.intValue()).getAddress();
		*/
		return null;
	}
	
	protected void updateElectronicMail(String address,Integer index){
		/*if(identifiable==null)
			return;
		if(StringUtils.isBlank(address))
			if(index < identifiable.getElectronicMails().size())
				((List<ElectronicMail>)identifiable.getElectronicMails()).remove(index.intValue());
			else
				;
		else
			getElectronicMail(index).setAddress(address);
		*/
	}
	
	private ElectronicMailAddress getElectronicMail(Integer index){
		/*if(identifiable==null)
			return null;
		if(identifiable.getElectronicMails()==null)
			identifiable.setElectronicMails(new ArrayList<ElectronicMail>());
		ElectronicMail electronicMail = null;
		if(index >= identifiable.getElectronicMails().size())
			identifiable.getElectronicMails().add(electronicMail = new ElectronicMail());
		else
			electronicMail = ((List<ElectronicMail>)identifiable.getElectronicMails()).get(index.intValue());
		return electronicMail;
		*/
		return null;
	}
	
	/**/
	
	protected String readPostalBox(){
		/*if(identifiable==null)
			return null;
		if(identifiable.getPostalBoxs()==null)
			identifiable.setPostalBoxs(new ArrayList<PostalBox>());
		if(identifiable.getPostalBoxs().isEmpty())
			return null;
		return identifiable.getPostalBoxs().iterator().next().getValue();
		*/
		return null;
	}
	
	protected void updatePostalBox(String address){
		/*
		if(identifiable==null)
			return;
		if(StringUtils.isBlank(address))
			identifiable.getPostalBoxs().clear();
		else{
			if(identifiable.getPostalBoxs().isEmpty()){
				PostalBox pb = new PostalBox(address);
				identifiable.getPostalBoxs().add(pb);
			}else
				identifiable.getPostalBoxs().iterator().next().setValue(address);
		}
		*/
	}
	
}
