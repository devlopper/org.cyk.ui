package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ElectronicMail;

@Named @ViewScoped @Getter @Setter
public class ElectronicMailEditPage extends AbstractContactEditPage<ElectronicMail> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	public static class Form extends AbstractForm<ElectronicMail> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Override
		public void write() {
			super.write();
			identifiable.setAddress(value);
		}
		
	}
	
	public static class Adapter extends AbstractAdapter<ElectronicMail> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(ElectronicMail.class);
			
		}
		
	}
	
}
