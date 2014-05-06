package edu.upb.aws.music.crawler;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


public class Controller {
	
	private static Set<Artist> artists;
	
	public static Set<Artist> getArtistSet() {
		if(null == artists){
			try {
				initArtists();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return artists;
	}
	
	public static void initArtists() throws Exception {
		
		artists = Collections.synchronizedSet(new HashSet<Artist>());
        String crawlStorageFolder = "data2";
        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.last.fm/music");
        controller.setCustomData(artists);

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(LastFmCrawler.class, numberOfCrawlers);  
       
        System.out.println("Found" + artists.size() + " artists");
        
        pageFetcher = new PageFetcher(config);
        robotstxtConfig = new RobotstxtConfig();
        robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        controller = new CrawlController(config, pageFetcher, robotstxtServer);
        for(Artist a : artists) {
        	controller.addSeed(a.getLastFmURL());
        }
        controller.setCustomData(artists);
        controller.start(ArtistPageCrawler.class, numberOfCrawlers);
     
}

}
