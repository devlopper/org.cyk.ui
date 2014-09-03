package org.cyk.ui.web.vaadin;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.vaadin.ui.Notification.Type;

@Getter @Setter @AllArgsConstructor
public class VaadinNotificationMessage implements Serializable {

	private static final long serialVersionUID = 1554516722729542971L;

	private Type severity;
	private String caption,description;
	
}
