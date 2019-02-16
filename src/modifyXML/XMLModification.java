package modifyXML;

import java.util.ArrayList;

public class XMLModification {

public static void main(String[] args) {
	
	
	// Paste Sitemap html without leading <url> tag into sitemap variable (because <url> is later used for splitting the string into array)
	// Example:
	
	String sitemap =
			"		<loc>https://www.domain.de/item1/</loc>\n" + 
			"			<lastmod>2018-05-03T12:41:13+00:00</lastmod>\n" + 
			"		</url>\n" + 
			"		<url>\n" + 
			"			<loc>https://www.domain.de/item2/</loc>\n" + 
			"			<lastmod>2018-05-03T12:41:25+00:00</lastmod>\n" + 
			"		</url>\n" + 
			"		<url>\n" + 
			"			<loc>https://www.domain.de/item3/</loc>\n" + 
			"			<lastmod>2018-05-03T12:41:30+00:00</lastmod>\n" + 
			"		</url>\n";

	String item =
				"	<item>\n" +
				"		<title>#1</title>\n" + 
				"		<link>#2</link>\n" + 
				"		<pubDate>Mon, 11 Feb 2019 22:18:25 +0000</pubDate>\n" + 
				"		<dc:creator><![CDATA[root]]></dc:creator>\n" + 
				"		<guid isPermaLink=\"false\">#3</guid>\n" + 
				"		<description></description>\n" + 
				"		<content:encoded><![CDATA[Welcome to WordPress. This is your first post. Edit or delete it, then start writing!]]></content:encoded>\n" + 
				"		<excerpt:encoded><![CDATA[]]></excerpt:encoded>\n" + 
				"		<wp:post_id>#4</wp:post_id>\n" + 
				"		<wp:post_date><![CDATA[2019-02-11 22:18:25]]></wp:post_date>\n" + 
				"		<wp:post_date_gmt><![CDATA[2019-02-11 22:18:25]]></wp:post_date_gmt>\n" + 
				"		<wp:comment_status><![CDATA[open]]></wp:comment_status>\n" + 
				"		<wp:ping_status><![CDATA[open]]></wp:ping_status>\n" + 
				"		<wp:post_name><![CDATA[hello-world]]></wp:post_name>\n" + 
				"		<wp:status><![CDATA[publish]]></wp:status>\n" + 
				"		<wp:post_parent>0</wp:post_parent>\n" + 
				"		<wp:menu_order>0</wp:menu_order>\n" + 
				"		<wp:post_type><![CDATA[post]]></wp:post_type>\n" + 
				"		<wp:post_password><![CDATA[]]></wp:post_password>\n" + 
				"		<wp:is_sticky>0</wp:is_sticky>\n" + 
				"		<category domain=\"category\" nicename=\"uncategorized\"><![CDATA[Uncategorized]]></category>\n" +
				"	</item>\n";
	
		// Remove all tags from sitemap string, except <url>, which is used to split the string into an array that only contains urls and last modified date
		String siteMapStringLean = sitemap.replaceAll("</loc>", "").replaceAll("<loc>", "").replaceAll("</lastmod>", "").replaceAll("</url>", "").replaceAll("<lastmod>", "<url>").replaceAll("\\n", "").replaceAll("\\s", "").replaceAll("\\r", "");
		ArrayList<String> urlArray = getUrlArray(siteMapStringLean);
		ArrayList<String> titleArray = getTitles(urlArray);
		StringBuffer xmlposts = new StringBuffer("");
		
		// Generate XML file that contains all posts by placing data from sitemap in item template and filling items into StringBuffer xmlposts
		for(int i = 0; i<urlArray.size(); i++) {
			String numberAsString = String.valueOf(i);
			String u = item.replace("#1", titleArray.get(i)).replace("#2", urlArray.get(i)).replace("#3", urlArray.get(i)).replace("#4", numberAsString);
			xmlposts.append(u);
		}
		
		System.out.println(xmlposts);

	}

	// Generates titles from urls in urlArray and saves them in a new Array
	public static ArrayList<String> getTitles(ArrayList<String> urlArray) {
		
		ArrayList<String> titleArray = new ArrayList<String>();
		
		for(String url : urlArray) {
			String s = url.replace("https://www.domain.de/", "").replace("/", "").replace("-"," ");
			titleArray.add(s);
		}
		return titleArray;
	}
	
	// Generates array that only contains URLs and not last modified dates
	public static ArrayList<String> getUrlArray(String sitemap){
		
		ArrayList<String> urlArray = new ArrayList();
		String[] sitemapItemArray = sitemap.split("<url>");	
		
		for(int i = 0; i<sitemapItemArray.length; i++) {
			if(i%2==0) {
				urlArray.add(sitemapItemArray[i]);
			}
		}
		return urlArray;
	}
	
}