package org.cyk.ui.api.command;

/**
 * Represent a button on which user can click to request something
 * @author Komenan Y .Christian
 *
 */
public interface UICommand {
	
	String getLabel();
	
	void setLabel(String label);
	
	String getIcon();
	
	void setIcon(String icon);

	String getTooltip();
	
	void setTooltip(String label);
	
}
