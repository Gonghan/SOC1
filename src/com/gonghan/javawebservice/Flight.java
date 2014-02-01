package com.gonghan.javawebservice;

import java.util.ArrayList;

//key af66c3d8d7ecb1b8128483abe4bca2ba
//https://www.developer.aero/Airport-API
//San Francisco: SFO
//New York: NYC
//Shanghai: PVG
//Beijing: PEK
//Los Angeles: LAX
public class Flight {
	private String originPlace;
	private String destinationPlace;
	private String outboundPartialDate;
	private ArrayList<String> priceInfo;
	private ArrayList<String> airlines;

	public Flight(String originPlace, String destinationPlace,
			String outboundPartialDate) {
		this.originPlace = originPlace;
		this.destinationPlace = destinationPlace;
		this.outboundPartialDate = outboundPartialDate;
		this.priceInfo = new ArrayList<String>();
		this.airlines = new ArrayList<String>();
	}

	public void addPriceInfo(String s) {
		this.priceInfo.add(s);
	}

	@Override
	public String toString() {
		return "Flight [originPlace=" + originPlace + ", destinationPlace="
				+ destinationPlace + ", outboundPartialDate="
				+ outboundPartialDate + ", priceInfo=" + priceInfo
				+ ", airlines=" + airlines + "]";
	}

	public void addAirline(String s) {
		this.airlines.add(s);
	}

}
