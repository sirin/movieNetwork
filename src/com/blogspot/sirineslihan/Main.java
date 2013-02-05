package com.blogspot.sirineslihan;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sampleTitle = args[0];
		URL titleURL = getTitleURL(sampleTitle);
		int numberOfPages = 1;
		try {
			numberOfPages = getNumberOfPagesOfTitle(titleURL.toString()) + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		OutputStreamWriter out = null;
		try {
			String filename = sampleTitle.replaceAll("\\W+", "_") + ".txt";
			out = new OutputStreamWriter(
					new FileOutputStream(filename), Charset.forName(
							"UTF-8").newEncoder());
			for (int i = 1; i < numberOfPages; i++) {
				String requestUrl = titleURL.toString();
				if (requestUrl.contains("?")) {
					requestUrl += "&p=";
				} else {
					requestUrl += "?p=";
				}
				requestUrl += i;
				out.write(getEntriesOfURL(requestUrl));
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Finished.");
	}

	public static URL getTitleURL(String title) {
		URL titleURL = null;
		URI uri;
		URL url = null;
		try {
			uri = new URI(
                          "http",
                          "beta.eksisozluk.com",
                          "/",
                          "q=" + title.replace(" ", "+"),
                          null);
			
			url = uri.toURL();
			System.out.println(url);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		URLConnection con;
		try {
			con = url.openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			titleURL = con.getURL();
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return titleURL;
	}

	public static String getEntriesOfURL(String url) {
		StringBuilder sb = new StringBuilder();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements entries = doc.select("div.content");
			for (Element entry : entries) {
				sb.append(entry.text() + "\n");
			    //System.out.println(entry.text());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static int getNumberOfPagesOfTitle(String url) {
		String numberOfPages = "0";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements pageInfo = doc.select("div.pager");
			if (pageInfo.size() <=  0) {
				return 1;
			}
			Element pager = pageInfo.get(0);
			numberOfPages = pager.attr("data-pagecount");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(numberOfPages);
	}
}
