package example.web.ops;

import example.web.responses.OAuth2TokenResponse;
import example.web.responses.TicketInfoResponse;
import example.web.services.TicketInfoService;
import example.web.utils.BaseOAuth2Utils;
import example.web.utils.TicketAuthUtils;

public class TicketOps extends BaseOps<TicketInfoService> {
	
	/**
	 * Default values
	 */
	private final String MIN_AVAILABLE = "1";
	private final String FIELD_LIST = "";
	private final String SORT = "";
	private final String LIMIT = "10";
	
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
		System.out.println("in TicketOps::getTickets");
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
