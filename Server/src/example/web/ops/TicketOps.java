package example.web.ops;

import example.web.responses.OAuth2TokenResponse;
import example.web.responses.TicketInfoResponse;
import example.web.services.TicketInfoService;
import example.web.utils.BaseOAuth2Utils;
import example.web.utils.TicketAuthUtils;

public class TicketOps extends BaseOps<TicketInfoService> {
	
	/**
	 * Default ticket parameters
	 */
	private final String MIN_AVAILABLE = "1";
	private final String FIELD_LIST =
		"title, dateLocal, ticketInfo, venue, categories, groupings";
	private final String SORT = "dateLocal asc, minPrice desc";
	private final String LIMIT = "500";
	
	public TicketOps(String endpoint) {
		super(endpoint, TicketInfoService.class, new TicketAuthUtils());
	}

	@Override
	protected OAuth2TokenResponse authorize() {
		return new OAuth2TokenResponse(
			mAuthUtils.makeCredential(BaseOAuth2Utils.APP_TOKEN));
	}
	
	public TicketInfoResponse getTickets(String authToken,
			String dateTimeRange, String city, String maxPrice) {
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
