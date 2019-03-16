package modifyXML;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;;

public class XMLModification {
		
	// Enter full Domain URL
	public static final String DOMAIN = "";
	public static final String domainName = "";
	
	public static void main(String[] args) throws IOException {

		// Enter Domain Name and URL
		URL SitemapUrl = new URL("");
		
		int postIdThousandsplace = 35;
		StringBuffer siteMapOverview = NetworkHelper.getHtmlCode(SitemapUrl);
		// Replace static head and end of File
		siteMapOverview.replace(0, (219 + domainName.length()), "").replace((siteMapOverview.length() - 58),
				siteMapOverview.length(), "");
				
		String preparedSitemapOverview = StringHelper.prepareOverviewSitemap(siteMapOverview.toString());
		
		/* TODO If no overview sitemap exist just set siteMapOverviewArray manually to an Array
		 * that only consists of the one sitemap instead of calling  getUrlArray()*/
		ArrayList<String> sitemapOverviewArray = getUrlArray(preparedSitemapOverview, "<lastmod>");
		ArrayList<String> sourceCodeSubPagesArray = NetworkHelper.getAllSitemaps(sitemapOverviewArray);	
		
		for (String sitemap : sourceCodeSubPagesArray) {
			/* Remove all tags from sitemap string, except <url>, which is used to split the
			 * string into an array that only contains urls and last modified date */
			String siteMapStringLean = StringHelper.prepareSitemap(sitemap);
			ArrayList<String> urlArray = getUrlArray(siteMapStringLean, "<url>");
			ArrayList<String> titleArray = StringHelper.getTitles(urlArray);
			StringBuffer xmlPosts = new StringBuffer("");

			/* Generate XML file that contains all posts by placing data from sitemap in
			 * item template and filling items into StringBuffer xmlposts */
			for (int i = 0; i < urlArray.size(); i++) {
				String numberAsString = String.valueOf(postIdThousandsplace * 1000 + i);
				String u = Template.templatePostItem.replace("#1", titleArray.get(i)).replace("#2", urlArray.get(i))
						.replace("#3", urlArray.get(i)).replace("#4", numberAsString)
						.replace("#5", StringHelper.getPostName(urlArray.get(i)));
				xmlPosts.append(u);
			}
			postIdThousandsplace++;
			System.out.println(xmlPosts);
		}
		
	}

	// Generate array that only contains URLs and not last modified dates
	public static ArrayList<String> getUrlArray(String sitemap, String split) {
		ArrayList<String> urlArray = new ArrayList<String>();
		String[] sitemapItemArray = sitemap.split(split);

		for (int i = 0; i < sitemapItemArray.length; i++) {
			if (i % 2 == 0) {
				urlArray.add(sitemapItemArray[i]);
			}
		}
		return urlArray;
	}

}
