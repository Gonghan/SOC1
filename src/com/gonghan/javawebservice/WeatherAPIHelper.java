package com.gonghan.javawebservice;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WeatherAPIHelper {

	// get this key from worldweatheronline.com
	private final String KEY = "uq25agrdtxjxkrtwvqmkbtmt";

	// find details at http://developer.worldweatheronline.com/io-docs
	public Weather getWeather(String q) {
		Weather weather = null;
		String response = null;
		
		
		HttpURLConnection conn = null;
		try {
			q=java.net.URLEncoder.encode(q, "UTF-8").replace("+", "%20");
			String urlStr = generateURL(q);
			URL url = new URL(urlStr);

			conn = (HttpURLConnection) url.openConnection();
			if (conn == null) {
				throw new RuntimeException("Connection failed");
			}
			if (conn.getResponseCode() != 200) {
				throw new IOException(conn.getResponseMessage());
			}
			StringBuilder sb = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = rd.readLine();
			while (line != null) {
				sb.append(line);
				line = rd.readLine();
			}
			response = sb.toString();

			Element e = this.parse(response);
			String date = getStrFromEle(e, "date");
			int tempMaxC = getIntFromEle(e, "tempMaxC");
			int tempMinC = getIntFromEle(e, "tempMinC");
			int tempMaxF = getIntFromEle(e, "tempMaxF");
			int tempMinF = getIntFromEle(e, "tempMinF");
			int windspeedMiles = getIntFromEle(e, "windspeedMiles");
			String winddirection = getStrFromEle(e, "winddirection");
			int windspeedKmph = getIntFromEle(e, "windspeedKmph");
			weather = new Weather(q,date, tempMaxC, tempMinC, tempMaxF, tempMinF,
					windspeedMiles, windspeedKmph, winddirection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return weather;
	}

	// example:
	// http://api.worldweatheronline.com/free/v1/weather.ashx?q=London&format=json&num_of_days=0&date=no&cc=no&includelocation=no&show_comments=no&callback=no&key=uq25agrdtxjxkrtwvqmkbtmt
	private String generateURL(String q) {
		String url = String
				.format("http://api.worldweatheronline.com/free/v1/weather.ashx?q=%s&format=xml&num_of_days=0&date=no&cc=no&includelocation=no&show_comments=no&callback=no&key=%s",
						q, KEY);
		return url;
	}

	private Element parse(String s) throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new ByteArrayInputStream(s.getBytes()));
		NodeList nList = doc.getElementsByTagName("weather");
		Node node = nList.item(0);
		Element eElement = (Element) node;
		return eElement;
	}

	private int getIntFromEle(Element e, String attr) {
		String i = e.getElementsByTagName(attr).item(0).getTextContent();
		return Integer.parseInt(i);
	}

	private String getStrFromEle(Element e, String attr) {
		String s = e.getElementsByTagName(attr).item(0).getTextContent();
		return s;
	}

}
