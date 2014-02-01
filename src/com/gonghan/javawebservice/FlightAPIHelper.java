package com.gonghan.javawebservice;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FlightAPIHelper {
	private Flight flight;
	private final String apiKey = "d709ce6d-eb12-4f12-868c-3cb990711cd0";
	private HashMap<String, String> map;

	public Flight generateFlight(String originPlace, String destinationPlace,
			String outboundPartialDate) {
		initMap();
		String query = generateQuery(map.get(originPlace),
				map.get(destinationPlace), outboundPartialDate);
		this.flight=new Flight(originPlace,destinationPlace,outboundPartialDate);
		// System.out.println(query);
		executeQuery(query);
		return this.flight;
	}

	private void initMap() {
		map = new HashMap<String, String>();
		map.put("Shanghai", "PVG");
		map.put("San Francisco", "SFO");
		map.put("Beijing", "PEK");
		map.put("New York", "NYC");

	}

	private String generateQuery(String originPlace, String destinationPlace,
			String outboundPartialDate) {

		String query = String
				.format("http://partners.api.skyscanner.net/apiservices/browsequotes/v1.0/US/USD/en-US/%s/%s/%s/?apiKey=%s",
						originPlace, destinationPlace, outboundPartialDate, apiKey);
		return query;
	}

	private String executeQuery(String query) {
		HttpURLConnection conn = null;
		String response = null;
		String output = "";
		try {
			// query=java.net.URLEncoder.encode(query, "UTF-8");
			URL url = new URL(query);

			conn = (HttpURLConnection) url.openConnection();
			if (conn == null) {
				throw new RuntimeException("Connection failed");
			}
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestMethod("GET");
			conn.setRequestProperty(
					"User-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.5 Safari/537.36");
			conn.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
			if (conn.getResponseCode() != 200) {
				throw new IOException(conn.getResponseMessage());
			}
			StringBuilder sb = new StringBuilder();
			InputStream stream = conn.getInputStream();
			GZIPInputStream gis = new GZIPInputStream(stream);
			BufferedReader rd = new BufferedReader(new InputStreamReader(gis,
					"UTF-8"));
			String line = rd.readLine();
			while (line != null) {
				sb.append(line);
				line = rd.readLine();
			}
			response = sb.toString();
			parse(response);
			// System.out.println(output);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return output;
	}

	private void parse(String s) throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new ByteArrayInputStream(s.getBytes()));
		NodeList quoteDtoList = doc.getElementsByTagName("QuoteDto");
		// put the minprice and direct into output
		for (int i = 0; i < quoteDtoList.getLength(); i++) {
			Node item = quoteDtoList.item(i);
			Element tmp = (Element) item;
			String price = getStrFromEle(tmp, "MinPrice");
			String direct = getStrFromEle(tmp, "Direct");
			String output = "Price: " + price + ", Direct: " + direct;
			this.flight.addPriceInfo(output);
		}
		// put airlines
		quoteDtoList = doc.getElementsByTagName("CarriersDto");
		for (int i = 0; i < quoteDtoList.getLength(); i++) {
			Node item = quoteDtoList.item(i);
			Element tmp = (Element) item;
			String name = getStrFromEle(tmp, "Name");
			this.flight.addAirline(name);
		}
	}

	private String getStrFromEle(Element e, String attr) {
		String s = e.getElementsByTagName(attr).item(0).getTextContent();
		return s;
	}
}
