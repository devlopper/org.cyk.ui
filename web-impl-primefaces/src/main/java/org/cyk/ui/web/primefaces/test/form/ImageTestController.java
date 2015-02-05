package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.model.UploadedFile;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ImageTestController extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3874558222002384857L;

	private UploadedFile uploadedFile;
	private String text;
	
	public void submit(){
		System.out.println(text);
		debug(uploadedFile);
	}
	
}
