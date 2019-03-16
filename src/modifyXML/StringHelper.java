package modifyXML;

import java.util.ArrayList;

public class StringHelper {
	
	// Generate post name from URL
	public static String getPostName(String url) {
		String postName = url.replace(XMLModification.DOMAIN, "").replace("/", "");
		return postName;

	}

	public static String convertToTitleCaseIteratingChars(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}

		StringBuilder converted = new StringBuilder();

		boolean convertNext = true;
		for (char ch : text.toCharArray()) {
			if (Character.isSpaceChar(ch)) {
				convertNext = true;
			} else if (convertNext) {
				ch = Character.toTitleCase(ch);
				convertNext = false;
			} else {
				ch = Character.toLowerCase(ch);
			}
			converted.append(ch);
		}
		return converted.toString();
	}
	
	// Generate titles from URLs in urlArray and saves them in a new Array
	public static ArrayList<String> getTitles(ArrayList<String> urlArray) {
		ArrayList<String> titleArray = new ArrayList<String>();

		for (String url : urlArray) {
			// Insert domain name
			String s = url.replace(XMLModification.DOMAIN, "").replace("/", "").replace("-", " ");
			String titleCaseString = StringHelper.convertToTitleCaseIteratingChars(s);
			titleArray.add(titleCaseString);
		}
		return titleArray;
	}
	
	public static String prepareOverviewSitemap(String overviewSitemap) {
		String preparedOverviewSitemap = overviewSitemap.replaceAll("</loc>", "").replaceAll("</lastmod>", "<lastmod>")
				.replaceAll("</sitemap>", "").replaceAll("<sitemap>", "").replaceAll("<loc>", "").replaceAll("\\n", "")
				.replaceAll("\\s", "").replaceAll("\\r", "");
		return preparedOverviewSitemap;
	}

	public static String prepareSitemap(String sitemap) {

		String preparedSitemap = sitemap.replaceAll("</loc>", "").replaceAll("<loc>", "").replaceAll("</lastmod>", "")
				.replaceAll("</url>", "").replaceAll("<lastmod>", "<url>").replaceAll("\\n", "").replaceAll("\\s", "")
				.replaceAll("\\r", "");
		return preparedSitemap;
	}

}
