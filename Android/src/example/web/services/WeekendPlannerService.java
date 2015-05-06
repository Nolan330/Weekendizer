package example.web.services;

import example.web.requests.WeekendPlannerRequest;
import example.web.responses.WeekendPlannerResponse;
import retrofit.http.Body;
import retrofit.http.POST;

public interface WeekendPlannerService {

	@POST("/WeekendPlannerServlet")
	WeekendPlannerResponse execute(@Body WeekendPlannerRequest request);
	
}
