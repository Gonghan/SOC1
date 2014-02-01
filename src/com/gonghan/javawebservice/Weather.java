package com.gonghan.javawebservice;

public class Weather {
	String date;
	int tempMaxC;
	int tempMinC;
	int tempMaxF;
	int tempMinF;
	int windspeedMiles;
	int windspeedKmph;
	String winddirection;
	String city;

	public Weather(String city, String date, int tempMaxC, int tempMinC,
			int tempMaxF, int tempMinF, int windspeedMiles, int windspeedKmph,
			String winddirection) {
		super();
		this.city = city;
		this.date = date;
		this.tempMaxC = tempMaxC;
		this.tempMinC = tempMinC;
		this.tempMaxF = tempMaxF;
		this.tempMinF = tempMinF;
		this.windspeedMiles = windspeedMiles;
		this.windspeedKmph = windspeedKmph;
		this.winddirection = winddirection;
	}

	public String getCity() {
		return city;
	}

	public String getDate() {
		return date;
	}

	public int getTempMaxC() {
		return tempMaxC;
	}

	public int getTempMinC() {
		return tempMinC;
	}

	public int getTempMaxF() {
		return tempMaxF;
	}

	public int getTempMinF() {
		return tempMinF;
	}

	public int getWindspeedMiles() {
		return windspeedMiles;
	}

	public int getWindspeedKmph() {
		return windspeedKmph;
	}

	public String getWinddirection() {
		return winddirection;
	}

	@Override
	public String toString() {
		return "Weather [city=" + city + ", date=" + date + ", tempMaxC="
				+ tempMaxC + ", tempMaxF=" + tempMaxF + ", tempMinC="
				+ tempMinC + ", tempMinF=" + tempMinF + ", winddirection="
				+ winddirection + ", windspeedKmph=" + windspeedKmph
				+ ", windspeedMiles=" + windspeedMiles + "]";
	}

	

}
