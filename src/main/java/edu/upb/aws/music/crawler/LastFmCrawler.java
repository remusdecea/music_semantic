package edu.upb.aws.music.crawler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;


public class LastFmCrawler extends WebCrawler {
	
	public static String baseURL = "http://www.last.fm/music";
		
	/**
	* You should implement this function to specify whether
	* the given url should be crawled or not (based on your
	* crawling logic).
	*/
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return href.startsWith(baseURL + "?page=");
	}
	
	/**
	* This function is called when a page is fetched and ready 
	* to be processed by your program.
	*/
	@Override
	public void visit(Page page) {          
		Set<Artist> artists = (Set<Artist>) this.getMyController().getCustomData();
		String url = page.getWebURL().getURL();
		System.out.println("URL: " + url);
	
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
						
			HtmlCleaner cleaner = new HtmlCleaner();
			CleanerProperties props = cleaner.getProperties();
	        props.setOmitComments(true);
			TagNode tagNode = cleaner.clean(htmlParseData.getHtml());
			TagNode listContainer = tagNode.getElementsByAttValue("class", "artistList", true, true)[0];
			List<TagNode> list = listContainer.getChildTagList();
			for(TagNode li : list) {
				TagNode [] name = li.getElementsByAttValue("class", "name", false, false);
				String link = "http://www.last.fm" + name[0].getAttributeByName("href");
				String artistName = name[0].getElementsByAttValue("class", "name", false, false)[0].getText().toString();
				artists.add(new Artist(artistName, link));
			}
		}
	}
}
