package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named @Getter @Setter
public class DefaultDesktopLayoutManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 776758304463284101L;

	private String logoPath = "/org.cyk.ui.web.primefaces/images/logo/cyksystems.png";
	private String loginBackgroundPath = "/org.cyk.ui.web.primefaces/images/background/default.jpg";
	private String homeBackgroundPath = "/org.cyk.ui.web.primefaces/images/background/home/default.jpg";
	private String incomingMessageSoundPath = "/org.cyk.ui.web.primefaces/sound/messageincoming.mp3";
	private String barCodeReaderReadSoundPath = "/org.cyk.ui.web.primefaces/sound/barCodeReaderRead.mp3";
	private String barCodeReaderReadNotNullSoundPath = "/org.cyk.ui.web.primefaces/sound/barCodeReaderReadNotNull.mp3";
	
	private String northInclude = "/org.cyk.ui.web.primefaces/include/layout/default/north.xhtml";
	private String westInclude = "/org.cyk.ui.web.primefaces/include/layout/default/west.xhtml";
	
}
