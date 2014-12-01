package org.cyk.ui.web.api.servlet;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*@Log*/ @WebServlet(name="fileServlet",urlPatterns={"/_cyk_file_/"})
public class FichierServlet extends AbstractFileServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	//@Inject private FileBusiness fileBusiness;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//File fichier = null;
		String identifier = request.getParameter("id");		
		//System.out.println("Image id : "+identifier);
		//String storageType = request.getParameter("storagetype");
		//if(StringUtils.isEmpty(storageType))
		//	storageType ="storagetype";
		
		//if("storagedb".equals(storageType))
			//try {
				//dataFile = new Dao().findById(DataFile.class,Long.parseLong(identifier));
				if(identifier==null || identifier.equals(""))
					return ;
				//fichier = fileBusiness.findById(Long.parseLong(identifier));//on recupere l'objet image de la bd
				/*
			} catch (NumberFormatException e) {}
		else if("storagememory".equals(storageType))
			dataFile = (DataFile) request.getSession().getAttribute(identifier);
		else
			log.info("Unknown Storage Type "+storageType);
	
		// Check if image is actually retrieved from database.
		if (dataFile == null) {
			// Do your thing if the image does not exist in database.
			// Throw an exception, or send 404, or show default/warning image,
			// or just ignore it.
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}
		*/
				/*
		send(
			getServletContext(),
			request, 
			response, 
			"datafile" + System.currentTimeMillis() + "."+ fichier.getExtension(), 
			fichier.getBytes().length,
			new ByteArrayInputStream(fichier.getBytes()));
		*/
	}
	
	@Override
	protected void initialisation(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected byte[] bytes(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String fileName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String fileExtension(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Boolean isAttachment(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}
