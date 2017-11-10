package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.userinterface.Image;
import org.cyk.utility.common.userinterface.input.Input;
import org.cyk.utility.common.userinterface.input.InputFile;
import org.cyk.utility.common.userinterface.input.choice.InputChoiceManyPickList;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

public class InputAdapter extends Input.Listener.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Object getReadableValue(Input<?> input) {
		// TODO Auto-generated method stub
		return super.getReadableValue(input);
	}
	
	@Override
	public void read(Input input) {
		super.read(input);
		if(input instanceof InputFile){
			InputFile inputFile = (InputFile) input;
			if( inputFile.getValue()!=null && inputFile.getValue().getBytes()!=null ){
				Image image = (Image) input.getPropertiesMap().getImageComponent();
	            image.getPropertiesMap().setValue(new ByteArrayContent(inputFile.getValue().getBytes(), inputFile.getValue().getMime()))
	            	.setStream(Boolean.FALSE);	
			}
		}
	}
	
	@Override
	public Object getWritableValue(Input<?> input) {
		if(input instanceof InputChoiceManyPickList)
			return  ((DualListModel<?>)input.getValueObject()).getTarget();
		if(input instanceof InputFile){
			UploadedFile file = (UploadedFile) ((InputFile)input).getValueObject();
			FileHelper.File value = (FileHelper.File) input.getValue();
			if(file==null || file.getContents()==null || file.getContents().length == 0)
				value = null;
			else {
				if(value==null)
					value = new FileHelper.File();
				value.setName(FileHelper.getInstance().getName(file.getFileName()));
				value.setExtension(FileHelper.getInstance().getExtension(file.getFileName()));
				value.setMime(file.getContentType());
				value.setBytes(file.getContents());
			}
			return value;
		}
		return super.getWritableValue(input);
	}
	/*
	@Override
	public Object getReadableValue(Object object, Field field) {
		Object value = super.getReadableValue(object, field);
		if(value instanceof File){
			File file = (File) value;
			value = new FileHelper.File();
			((FileHelper.File)value).setBytes(file.getBytes());
			((FileHelper.File)value).setMime(file.getMime());
		}	
		return value;
	}
	
	@Override
	public Object getWritableValue(Object object) {
		if(object instanceof FileHelper.File){
			File file = new File();
			file.setBytes( ((FileHelper.File)object).getBytes() );
			file.setMime( ((FileHelper.File)object).getMime() );
			return file;
		}
		return super.getWritableValue(object);
	}
	*/
}