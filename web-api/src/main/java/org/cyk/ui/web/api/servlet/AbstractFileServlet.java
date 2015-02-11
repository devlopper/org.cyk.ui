package org.cyk.ui.web.api.servlet;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.cyk.system.root.business.api.file.FileBusiness;

public abstract class AbstractFileServlet extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	@Inject protected FileBusiness fileBusiness;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		initialisation(request, response);
		byte[] bytes = bytes(request,response);
		if(bytes==null)
			return;
		send(
			getServletContext(),request, response, 
			fileName(request, response)+" "+System.currentTimeMillis() + "."+ fileExtension(request, response), 
			bytes.length,
			new ByteArrayInputStream(bytes),!Boolean.TRUE.equals(isAttachment(request, response)),1024);
		
	}
	
	protected abstract void initialisation(HttpServletRequest request,HttpServletResponse response);
	protected abstract byte[] bytes(HttpServletRequest request,HttpServletResponse response);
	protected abstract String fileName(HttpServletRequest request,HttpServletResponse response);
	protected abstract String fileExtension(HttpServletRequest request,HttpServletResponse response);
	protected abstract Boolean isAttachment(HttpServletRequest request,HttpServletResponse response);
	
	public static void send(ServletContext servletContext,HttpServletRequest request, HttpServletResponse response,String fileName, int contentLength, InputStream inputStream,boolean inline,int bufferSize) throws IOException {
		// Get content type by filename.
		
		String contentType = servletContext.getMimeType(fileName);

		// If content type is unknown, then set the default value.
		// For all content types, see:
		// http://www.w3schools.com/media/media_mimeref.asp
		// To add new content types, add new mime-mapping entry in web.xml.
		if (contentType == null) {
			//log.warning("Unknown content type of file : " + fileName);
			contentType = "application/octet-stream";
		}

		response.reset();
		response.setBufferSize(bufferSize);
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(contentLength));
		response.setHeader("Content-Disposition", (inline?"inline":"attachment")+"; filename=\""+ fileName + "\"");

		// Prepare streams.
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open streams.
			input = new BufferedInputStream(inputStream, bufferSize);
			output = new BufferedOutputStream(response.getOutputStream(),bufferSize);
			
			// Write file contents to response.
			/*
			byte[] buffer = new byte[bufferSize];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}*/
			
			IOUtils.copy(input, output);
		} finally {
			// Gently close streams.
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
			//close(output);
			//close(input);
		}
	}
}
