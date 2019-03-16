package modifyXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class NetworkHelper {
	
	public static ArrayList<String> getAllSitemaps(ArrayList<String> sitemapArray) throws IOException {
		ArrayList<String> sitemapSourceCodeArray = new ArrayList<String>();
		for (String sitemapUrl : sitemapArray) {

			StringBuffer siteMapOverview = getHtmlCode(new URL(sitemapUrl));

			// Replace head and end of file
			siteMapOverview.replace(0, 553 + XMLModification.domainName.length(), "").replace((siteMapOverview.length() - 58),
					siteMapOverview.length(), "");

			sitemapSourceCodeArray.add(siteMapOverview.toString());
			// TODO Cut SubSitemaps into Array and then put arrays for all sitemapUrl items
			// together and return this.

		}
		return sitemapSourceCodeArray;
	}

	public static StringBuffer getHtmlCode(URL url1) throws IOException {

		InputStream in = url1.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String s = null;
		StringBuffer sb = new StringBuffer("");

		while ((s = reader.readLine()) != null) {
			sb.append(s);
		}
		return sb;
	}

}
