package example.web.ops;

import example.web.services.PlacesInfoService;
import example.web.utils.PlacesAuthUtils;

public class PlacesOps extends BaseOps<PlacesInfoService> {
	
	public PlacesOps(String endpoint) {
		super(endpoint, PlacesInfoService.class, new PlacesAuthUtils());
	}

	@Override
	public String authorize() {
		// TODO Auto-generated method stub
		return null;
	}

}
