package example.web.model;

public class City {

	public String code;
	public String name;
	public String countryCode;
	public String countryName;
	public String regionName;
	
	public City(String c, String n, String cCode,
				String cName, String rName) {
		code = c;
		name = n;
		countryCode = cCode;
		countryName = cName;
		regionName = rName;
	}
	
}
