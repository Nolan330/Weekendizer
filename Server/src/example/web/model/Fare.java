package example.web.model;

public class Fare {
	
	public String CurrencyCode;
	public String Amount;
	
	public String toString() {
		return "$" + Amount + " (" + CurrencyCode + ")";
	}

}
