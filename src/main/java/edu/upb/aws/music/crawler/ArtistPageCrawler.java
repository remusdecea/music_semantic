package edu.upb.aws.music.crawler;
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


public class ArtistPageCrawler extends WebCrawler {
	
	/**
	* You should implement this function to specify whether
	* the given url should be crawled or not (based on your
	* crawling logic).
	*/
	@Override
	public boolean shouldVisit(WebURL url) {
		
		return false;
	}
	
	/**
	* This function is called when a page is fetched and ready 
	* to be processed by your program.
	*/
	@Override
	public void visit(Page page) {          
		String url = page.getWebURL().getURL();
		//System.out.println("URL: " + url);
		Set<Artist> artists = (Set<Artist>) this.getMyController().getCustomData();
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
						
			HtmlCleaner cleaner = new HtmlCleaner();
			CleanerProperties props = cleaner.getProperties();
	        props.setOmitComments(true);
			TagNode tagNode = cleaner.clean(htmlParseData.getHtml());
			
			Artist artist = null;
			for (Artist a : artists)
				if(url.equals(a.getLastFmURL()))
					artist = a;
			if(artist == null)
				return;
			
			TagNode [] songList = tagNode.getElementsByAttValue("itemtype", "http://schema.org/MusicRecording", true, true);
			//System.out.println(songList.length);
			for(TagNode tn : songList) {
				String song = tn.getElementsByAttValue("itemprop", "name", true, true)[0].getText().toString();
				artist.addSong(song);
			}
			
			TagNode[] tagList = tagNode.getElementsByAttValue("class", "tags", true, false)[0].getChildTags();
			for(TagNode tn : tagList) {
				String tag = tn.getElementsByAttValue("rel", "tag", true, false)[0].getText().toString();
				artist.addTag(tag);
			}
			
			TagNode[] artistList = tagNode.getElementsByAttValue("class", "similar-artist", true, false);
			for(TagNode tn : artistList) {
				String artistName = tn.getElementsByAttValue("class", "text-over-image-text", true, false)[0].getText().toString();
				String artistLink = tn.getAttributeByName("href");
				artist.addSimilarArtist(new Artist(artistName, LastFmCrawler.baseURL + artistLink));
			}
		}
	}
}
