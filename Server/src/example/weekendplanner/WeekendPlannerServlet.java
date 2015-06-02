package example.weekendplanner;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;

import example.web.requests.WeekendPlannerRequest;

/**
 * Servlet implementation class WeekendPlannerServlet
 */
@WebServlet(asyncSupported = true,
			urlPatterns = { "/WeekendPlannerServlet" })
public class WeekendPlannerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Google's JSON parsing library
	 */
	private final Gson mGson;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeekendPlannerServlet() {
        super();
        mGson = new Gson();
    }
    
    public class Exclusion implements ExclusionStrategy {

		@Override
		public boolean shouldSkipClass(Class<?> c) {
			return false;
		}

		@Override
		public boolean shouldSkipField(FieldAttributes fieldAttr) {
			return fieldAttr.getDeclaringClass() == WeekendPlannerResponse.class && 
					fieldAttr.getName().equals("mEventIt");
		}
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
						  HttpServletResponse response)
								  throws ServletException, IOException {
		String country = request.getParameter("country");
		
		WeekendPlannerOps wOps = new WeekendPlannerOps();
		
		wOps.getFlightAuthToken()
		.thenCompose(authToken -> wOps.getCities(country, authToken))
		.thenAccept(cities -> sendResponse(response, cities))
		.join();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response)
								  throws ServletException, IOException {
		WeekendPlannerRequest req =
			mGson.fromJson(request.getReader().readLine(),
						   WeekendPlannerRequest.class);
		
		System.out.println(req.toString());
		
		WeekendPlannerOps wOps = new WeekendPlannerOps();
		
		wOps.initTrip(req)
		.thenCombine(
			wOps.getFlightAuthToken(),
			wOps::getFlight)
		.thenCompose(trip->
			trip.thenCombine(
				wOps.getTicketAuthToken(),
				wOps::getTickets))
		.thenCompose(trip ->
			trip.thenCompose(wOps::getWeather))
		.thenCombine(
			wOps.getGeocode(req.getDestinationCity().getName()),
			wOps::fillWeekend)
		.thenCompose(trip ->
			trip.thenAccept(respObj -> sendResponse(response, respObj)))
		.join();
		
	}
	
	private <T> void sendResponse(HttpServletResponse response,
								  T responseObj) {
		System.out.println(responseObj);
		String responseJson = mGson.toJson(responseObj);
		response.setStatus(200);
		response.setContentType("application/json");
		response.setContentLength(responseJson.getBytes().length);
		
		try (PrintWriter pw = response.getWriter()) {
			pw.write(responseJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
