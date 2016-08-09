package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.PhoneNumber;

@Named @ViewScoped @Getter @Setter
public class PhoneNumberEditPage extends AbstractContactEditPage<PhoneNumber> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	public static class Form extends AbstractForm<PhoneNumber> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
	}
	
	public static class Adapter extends AbstractAdapter<PhoneNumber> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(PhoneNumber.class);
			
		}
		
	}
	
}
