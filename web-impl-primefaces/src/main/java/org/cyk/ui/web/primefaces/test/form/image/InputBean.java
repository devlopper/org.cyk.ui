package org.cyk.ui.web.primefaces.test.form.image;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.model.party.PersonFormModel;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named
@ViewScoped @Getter @Setter
public class InputBean extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 9040359120893077422L;

	private FormOneData<PersonFormModel> form;
	//private Part part;
	//private String statusMessage;

	@Override
	protected void initialisation() {
		super.initialisation();
		form = (FormOneData<PersonFormModel>) createFormOneData(new PersonFormModel(), Crud.CREATE);
		
	
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.TRUE;
	}
	/*
	public Object uploadFile() throws IOException {

		// Extract file name from content-disposition header of file part
		String fileName = getFileName(part);
		System.out.println("***** fileName: " + fileName);

		String basePath = "C:" + File.separator + "temp" + File.separator;
		File outputFilePath = new File(basePath + fileName);

		// Copy uploaded file to destination path
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = part.getInputStream();
			outputStream = new FileOutputStream(outputFilePath);

			int read = 0;
			final byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			statusMessage = "File upload successfull !! : "+outputFilePath.getTotalSpace();
		} catch (IOException e) {
			e.printStackTrace();
			statusMessage = "File upload failed !!";
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return null; // return to same page
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	// Extract file name from content-disposition header of file part
	private String getFileName(Part part) {
		final String partHeader = part.getHeader("content-disposition");
		System.out.println("***** partHeader: " + partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}*/
}