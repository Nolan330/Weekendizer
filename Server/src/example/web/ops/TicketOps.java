package example.web.ops;

import example.web.services.TicketInfoService;
import example.web.utils.TicketAuthUtils;

public class TicketOps extends BaseOps<TicketInfoService> {
	
	public TicketOps(String endpoint) {
		super(endpoint, TicketInfoService.class, new TicketAuthUtils());
	}

	@Override
	public String authorize() {
		return mService.authorize(
			mAuthUtils.makeCredential(),
			mAuthUtils.getGrantType(),
			mAuthUtils.getUsername(),
			mAuthUtils.getPassword(),
			mAuthUtils.getScope()).access_token;
	}

}
