package org.cyk.ui.web.api.resources.servlet;
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

import lombok.Getter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtension;
import org.cyk.utility.common.file.FileNameNormaliser;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.RequestHelper;

public abstract class AbstractFileServletOLD extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	/*protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		initialisation(request, response);
		Collection<File> files = getFiles(request, response);
		String extension = fileExtension(request, response, files);
		byte[] bytes = bytes(request,response,files,FileExtension.getByValue(extension));
		
		if(bytes==null)
			return;
		
		send(
			getServletContext(),request, response, 
			fileName(request, response,files)+Constant.CHARACTER_SPACE+System.currentTimeMillis() +Constant.CHARACTER_DOT+ extension, 
			bytes.length,
			new ByteArrayInputStream(bytes),getAttachmentType(request, response),1024);
		
	}*/
	
	protected abstract void initialisation(HttpServletRequest request,HttpServletResponse response);
	
	protected byte[] bytes(HttpServletRequest request, HttpServletResponse response,Collection<FileHelper.File> files,FileExtension fileExtension) {
		if(files==null || files.isEmpty())
			return null;
		if(files.size()==1)
			try {
				return null;//IOUtils.toByteArray(fileBusiness.findInputStream(files.iterator().next()));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		else
			return null;//fileBusiness.merge(files, fileExtension).toByteArray();
	}
	
	protected String fileName(HttpServletRequest request, HttpServletResponse response,Collection<FileHelper.File> files) {
		String name = null;
		if(files!=null && !files.isEmpty() && files.size()==1){
			FileHelper.File file = files.iterator().next();
			if(StringUtils.isNotBlank(file.getName()))
				name = file.getName();
			//else if(StringUtils.isNotBlank(file.getCode()))
			//	name = file.getCode();
		}
		if(StringUtils.isBlank(name))
			name = RandomStringUtils.randomAlphabetic(4);
		return new FileNameNormaliser.Adapter.Default().setInput(name).execute();
	}
	
	protected String fileExtension(HttpServletRequest request, HttpServletResponse response,Collection<FileHelper.File> files) {
		if(files==null || files.isEmpty())
			return Constant.EMPTY_STRING;
		FileHelper.File file = files.iterator().next();
		return file.getExtension();
	}
	
	protected AttachmentType getAttachmentType(HttpServletRequest request,HttpServletResponse response){
		String attachmentTypeString = RequestHelper.getInstance().getParameterAsString(UniformResourceLocatorHelper.QueryParameter.Name.ATTACHMENT,request);
		AttachmentType attachmentType = null;
		if(StringUtils.isBlank(attachmentTypeString))
			attachmentType = getAttachmentTypeWhenNotSpecified();
		else
			attachmentType = AttachmentType.valueOf(attachmentTypeString);
		return attachmentType;
	}
	
	protected AttachmentType getAttachmentTypeWhenNotSpecified(){
		return AttachmentType.IN;
	}
	
	public static void send(ServletContext servletContext,HttpServletRequest request, HttpServletResponse response,String fileName, int contentLength, InputStream inputStream,AttachmentType attachmentType,int bufferSize) throws IOException {
		String contentType = servletContext.getMimeType(fileName);
		if (contentType == null) {
			//log.warning("Unknown content type of file : " + fileName);
			contentType = "application/octet-stream";
		}

		response.reset();
		response.setBufferSize(bufferSize);
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(contentLength));
		response.setHeader("Content-Disposition", attachmentType.getIdentifier()+"; filename=\""+ fileName + "\"");

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
	
	protected Collection<FileHelper.File> getFiles(HttpServletRequest request, HttpServletResponse response){
		Collection<FileHelper.File> collection = new ArrayList<>();
		Collection<Long> identifiers = new ArrayList<>();
		String identifier = RequestHelper.getInstance().getParameterAsString(UniformResourceLocatorHelper.QueryParameter.Name.IDENTIFIER,request);
		String encodedParameter = RequestHelper.getInstance().getParameterAsString(UniformResourceLocatorHelper.QueryParameter.Name.ENCODED,request);
		if(UniformResourceLocatorHelper.QueryParameter.Name.IDENTIFIER.equals(encodedParameter)){
			Collection<Long> r = null;//webManager.decodeIdentifiersRequestParameterValue(identifiable);
			if(r!=null)
				identifiers.addAll(r);
		}else{
			String[] identifiersAsString = StringUtils.split(identifier,Constant.CHARACTER_COMA.charValue());
			if(identifiersAsString==null)
				;
			else
				for(String index : identifiersAsString)
					try {
						identifiers.add(Long.parseLong(index));
					} catch (NumberFormatException e) {
						return null;
					}
		}
		
		//collection.addAll(fileBusiness.findByIdentifiers(identifiers));
		
		return collection;
	}
	
	/**/
	
	protected String fileExtensionRequestParameter(HttpServletRequest request){
		return RequestHelper.getInstance().getParameterAsString(UniformResourceLocatorHelper.QueryParameter.Name.FILE_EXTENSION,request);
	}
	
	/**/
	
	@Getter
	public static enum AttachmentType{
		IN("inline"),
		OUT("attachment"),
		
		;
		
		private String identifier;
		
		private AttachmentType(String identifier) {
			this.identifier = identifier;
		}
	}
}
