package example.weekendplanner;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		.thenComposeAsync(
			authToken -> wOps.getCities(authToken, country),
			wOps.getExecutor())
		.thenAcceptAsync(
			cities -> sendResponse(response, cities),
			wOps.getExecutor())
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
		
		// new ops for each request (concurrent requests)
		WeekendPlannerOps wOps = new WeekendPlannerOps(req);
		
		// initiate the monad by retrieving an auth token
		wOps.getFlightAuthToken()
		.thenComposeAsync(
			wOps::getFlight,
			wOps.getExecutor())
		.thenCombineAsync(
			wOps.getEventAuthToken(),
			wOps::getEvents,
			wOps.getExecutor())
		.thenCombineAsync(
			wOps.getTicketAuthToken(),
			wOps::filterEventsByTicketPrice,
			wOps.getExecutor())
		.thenCombineAsync(
			wOps.getWeather(),
			wOps::fillWeekend,
			wOps.getExecutor())
		.thenAcceptAsync(
			tripVariants -> sendResponse(response, tripVariants),
			wOps.getExecutor())
		.join();
	}
	
	// if toJson needs a ".class" second parameter, could maybe use an object wrapper that calls this.class?
	private <T> void sendResponse(HttpServletResponse response,
								  T responseObj) {
		String responseJson = mGson.toJson(responseObj);
		response.setContentType("application/json");
		response.setContentLength(responseJson.getBytes().length);
		
		try (PrintWriter pw = response.getWriter()) {
			pw.write(responseJson);
		} catch (IOException e) {
			// try again?
			e.printStackTrace();
		}
	}
	
}