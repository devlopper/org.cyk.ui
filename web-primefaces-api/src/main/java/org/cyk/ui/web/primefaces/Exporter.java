package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.ui.api.UIManager;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Exporter implements Serializable {

	private static final long serialVersionUID = 3803745350859325894L;

	private String type,fileUrl,height="700px",width="100%",linkMessage,clickToDownload;
	
	public Exporter() {
		linkMessage = UIManager.getInstance().text("exporter.link");
		clickToDownload = UIManager.getInstance().text("exporter.clickToDownload");
	}
	
}
