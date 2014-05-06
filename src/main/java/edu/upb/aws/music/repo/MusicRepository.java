package edu.upb.aws.music.repo;

import info.aduna.iteration.Iterations;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;
import org.openrdf.sail.memory.MemoryStore;

import edu.upb.aws.music.crawler.Artist;
import edu.upb.aws.music.crawler.Controller;

public class MusicRepository {

	private static RepositoryConnection REPOSITORY_CONN;
	private static final String ONTOLOGY_FILE_PATH = "src/main/resources/mo.rdf";
	private static final String BASE_URI = "http://purl.org/ontology/mo/";
	private static final boolean isCrawlNeeded = false;
	
	public static RepositoryConnection getRepositoryConnection(){
		if(REPOSITORY_CONN == null){
			try {
				initRepository();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return REPOSITORY_CONN;
	}
	
	private static void initRepository() throws Exception{
		Repository repository = new SailRepository(new MemoryStore());
		repository.initialize();
		
		
		REPOSITORY_CONN = repository.getConnection();
		REPOSITORY_CONN.add(new File(MusicRepository.class.getResource("/crawled_rdfxml.rdf").getPath()), BASE_URI, RDFFormat.RDFXML);
		ValueFactory f = repository.getValueFactory();
		FileOutputStream outputXML = new FileOutputStream(new File("acrawled_rdfxml.rdf"));
		
		
		//TODO: change to artist
		URI musicGroup = f.createURI(BASE_URI+ "MusicArtist");
		URI performance = f.createURI(BASE_URI+ "Performance");
		URI performed = f.createURI(BASE_URI+ "performed");

		if(isCrawlNeeded){
			Set<Artist> artists = Controller.getArtistSet();
			for(Artist a : artists) {
	        	System.out.println(a.getName());
	        	//add artist as MusicArtist type
	        	URI artist = f.createURI(BASE_URI + a.getName());
	        	REPOSITORY_CONN.add(artist, RDF.TYPE, musicGroup);
	    		for(String songName : a.getSongs()){
	        		System.out.println("\t" + songName);
	        		URI song = f.createURI(BASE_URI+songName);
	        		//add song as Performance type
	        		REPOSITORY_CONN.add(song, RDF.TYPE, performance);
	        		//add property performed
	        		REPOSITORY_CONN.add(artist, performed, song);
	        	}
	        }
			RepositoryResult<Statement> statements = REPOSITORY_CONN.getStatements(
					null, null, null, true);
			Model model = Iterations.addAll(statements, new LinkedHashModel());
			Rio.write(model, outputXML, RDFFormat.RDFXML);
		}

	}
}
