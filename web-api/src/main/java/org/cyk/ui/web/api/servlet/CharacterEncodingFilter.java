package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.cyk.ui.api.UIManager;

public class CharacterEncodingFilter extends AbstractFilter implements Serializable {

	private static final long serialVersionUID = -7275934377277008585L;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {
		servletRequest.setCharacterEncoding(UIManager.CHARACTER_ENCODING);
		filterChain.doFilter(servletRequest, servletResponse);

	}

}
