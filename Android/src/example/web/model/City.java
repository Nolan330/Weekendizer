package example.web.model;

import java.util.List;


public class City {

	public String code;
	public String name;
	public String countryCode;
	public String countryName;
	public String regionName;
	public List<Link> Links;
	
	public City(String c, String n, String cCode,
				String cName, String rName, List<Link> l) {
		code = c;
		name = n;
		countryCode = cCode;
		countryName = cName;
		regionName = rName;
		Links = l;
	}
	
}
