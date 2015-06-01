package example.web.ops;

import example.web.responses.OAuth2TokenResponse;
import example.web.responses.TicketResponse;
import example.web.services.TicketService;
import example.web.utils.BaseOAuth2Utils;
import example.web.utils.TicketAuthUtils;

public class TicketOps extends BaseOps<TicketService> {
	
	/**
	 * Default ticket parameters
	 */
	private final String MIN_AVAILABLE = "1";
	private final String FIELD_LIST =
		"title, dateLocal, ticketInfo, venue, categories, groupings";
	private final String SORT = "dateLocal asc, minPrice desc";
	private final String LIMIT = "500";
	
	public TicketOps(String endpoint) {
		super(endpoint, TicketService.class, new TicketAuthUtils());
	}

	@Override
	protected OAuth2TokenResponse authorize() {
		System.out.println("TicketOps::authorize - " + System.currentTimeMillis());
		return new OAuth2TokenResponse(
			mAuthUtils.makeCredential(BaseOAuth2Utils.APP_TOKEN));
	}
	
	public TicketResponse getTickets(String authToken,
			String dateTimeRange, String city, String maxPrice) {
		System.out.println("TicketOps::getTickets - " + System.currentTimeMillis());
		return mService.queryTickets(
			authToken,
			dateTimeRange,
			city,
			maxPrice,
			MIN_AVAILABLE,
			FIELD_LIST,
			SORT,
			LIMIT);
	}

}
