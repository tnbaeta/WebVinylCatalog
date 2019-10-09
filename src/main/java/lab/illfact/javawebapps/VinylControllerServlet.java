package lab.illfact.javawebapps;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class VinylControllerServlet
 */
@WebServlet("/VinylControllerServlet")
public class VinylControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private VinylDbUtil vinylDbUtil;
	
	@Resource(name="jdbc/web_vinyl_catalog")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our vinyl db util ... and pass in the conn pool / datasource
		try {
			vinylDbUtil = new VinylDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing vinyls
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listVinyls(request, response);
				break;
				
			case "ADD":
				addVinyl(request, response);
				break;
				
			case "LOAD":
				loadVinyl(request, response);
				break;
				
			case "UPDATE":
				updateVinyl(request, response);
				break;
			
			case "DELETE":
				deleteVinyl(request, response);
				break;
				
			default:
				listVinyls(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}

	private void deleteVinyl(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read vinyl id from form data
		String theVinylId = request.getParameter("vinylId");
		
		// delete vinyl from database
		vinylDbUtil.deleteVinyl(theVinylId);
		
		// send them back to "list vinyls" page
		listVinyls(request, response);
	}

	private void updateVinyl(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read vinyl info from form data
		int id = Integer.parseInt(request.getParameter("vinylId"));
		String album = request.getParameter("album");
		String artist = request.getParameter("artist");
		
		// create a new vinyl object
		Vinyl theVinyl = new Vinyl(id, album, artist);
		
		// perform update on database
		vinylDbUtil.updateVinyl(theVinyl);
		
		// send them back to the "list vinyls" page
		listVinyls(request, response);
		
	}

	private void loadVinyl(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// read vinyl id from form data
		String theVinylId = request.getParameter("vinylId");
		
		// get vinyl from database (db util)
		Vinyl theVinyl = vinylDbUtil.getVinyl(theVinylId);
		
		// place vinyl in the request attribute
		request.setAttribute("THE_VINYL", theVinyl);
		
		// send to jsp page: update-vinyl-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-vinyl-form.jsp");
		dispatcher.forward(request, response);		
	}

	private void addVinyl(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read vinyl info from form data
		String album = request.getParameter("album");
		String artist = request.getParameter("artist");
		
		// create a new vinyl object
		Vinyl theVinyl = new Vinyl(album, artist);
		
		// add the vinyl to the database
		vinylDbUtil.addVinyl(theVinyl);
				
		// send back to main page (the vinyl list)
		listVinyls(request, response);
	}

	private void listVinyls(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get vinyls from db util
		List<Vinyl> vinyls = vinylDbUtil.getVinyls();
		
		// add vinyls to the request
		request.setAttribute("VINYL_LIST", vinyls);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-vinyls.jsp");
		dispatcher.forward(request, response);
	}

}