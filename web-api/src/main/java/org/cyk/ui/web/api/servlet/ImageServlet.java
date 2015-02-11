package org.cyk.ui.web.api.servlet;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.model.file.File;

/*@Log*/ @WebServlet(name="fileImageServlet",urlPatterns={"/_cyk_file_image/*"})
public class ImageServlet extends AbstractFileServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	@Override
	protected void initialisation(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	private File file(HttpServletRequest request, HttpServletResponse response){
		try {
			return fileBusiness.find(Long.parseLong(request.getParameter("identifier")));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	protected byte[] bytes(HttpServletRequest request, HttpServletResponse response) {
		File file = file(request, response);
		if(file==null)
			return null;
		try {
			return IOUtils.toByteArray(fileBusiness.findInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String fileName(HttpServletRequest request, HttpServletResponse response) {
		return RandomStringUtils.randomAlphabetic(4);
	}

	@Override
	protected String fileExtension(HttpServletRequest request, HttpServletResponse response) {
		File file = file(request, response);
		return file==null?"":file.getExtension();
	}

	@Override
	protected Boolean isAttachment(HttpServletRequest request, HttpServletResponse response) {
		return Boolean.TRUE;
	}
}
