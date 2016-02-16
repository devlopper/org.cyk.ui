package org.cyk.ui.web.api.servlet;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.model.Mime;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.Constant;

public abstract class AbstractFileServlet extends AbstractServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	@Inject protected FileBusiness fileBusiness;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		initialisation(request, response);
		Collection<File> files = getFiles(request, response);
		String extension = fileExtension(request, response, files);
		byte[] bytes = bytes(request,response,files,Mime.getByIdentifier(fileBusiness.findMime(extension)));
		if(bytes==null)
			return;
		send(
			getServletContext(),request, response, 
			fileName(request, response)+" "+System.currentTimeMillis() + "."+ extension, 
			bytes.length,
			new ByteArrayInputStream(bytes),!Boolean.TRUE.equals(isAttachment(request, response)),1024);
		
	}
	
	protected abstract void initialisation(HttpServletRequest request,HttpServletResponse response);
	
	protected byte[] bytes(HttpServletRequest request, HttpServletResponse response,Collection<File> files,Mime mime) {
		if(files==null || files.isEmpty())
			return null;
		if(files.size()==1)
			try {
				return IOUtils.toByteArray(fileBusiness.findInputStream(files.iterator().next()));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		else
			return fileBusiness.merge(files, mime).toByteArray();
	}
	
	protected String fileName(HttpServletRequest request, HttpServletResponse response) {
		return RandomStringUtils.randomAlphabetic(4);
	}
	
	protected String fileExtension(HttpServletRequest request, HttpServletResponse response,Collection<File> files) {
		return files==null || files.isEmpty()?Constant.EMPTY_STRING:files.iterator().next().getExtension();
	}
	
	protected abstract Boolean isAttachment(HttpServletRequest request,HttpServletResponse response);
	
	public static void send(ServletContext servletContext,HttpServletRequest request, HttpServletResponse response,String fileName, int contentLength, InputStream inputStream,boolean inline,int bufferSize) throws IOException {
		String contentType = servletContext.getMimeType(fileName);
		if (contentType == null) {
			//log.warning("Unknown content type of file : " + fileName);
			contentType = "application/octet-stream";
		}

		response.reset();
		response.setBufferSize(bufferSize);
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(contentLength));
		response.setHeader("Content-Disposition", (inline?"inline":"attachment")+"; filename=\""+ fileName + "\"");

		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new BufferedInputStream(inputStream, bufferSize);
			output = new BufferedOutputStream(response.getOutputStream(),bufferSize);
			IOUtils.copy(input, output);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
	}
	
	/**/
	
	protected Collection<File> getFiles(HttpServletRequest request, HttpServletResponse response){
		Collection<File> collection = new ArrayList<>();
		String[] identifiers = StringUtils.split(request.getParameter("identifier"),Constant.CHARACTER_COMA.charValue());
		for(String identifier : identifiers)
		try {
			collection.add(fileBusiness.find(Long.parseLong(identifier)));
		} catch (NumberFormatException e) {
			return null;
		}
		return collection;
	}
	
	/**/
	
	protected String fileExtensionRequestParameter(HttpServletRequest request){
		return requestParameter(request, uiManager.getFileExtensionParameter());
	}
}
