package edu.upb.aws.music.repo;

import java.io.File;

import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.memory.MemoryStore;

public class MusicRepository {

	private static RepositoryConnection REPOSITORY_CONN;
	private static final String ONTOLOGY_FILE_PATH = "src/main/resources/mo.rdf";
	private static final String BASE_URI = "http://purl.org/ontology/mo/";
	
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
		System.out.println("paths");
		System.out.println(MusicRepository.class.getResource("/mo.rdf").getPath());
		
		REPOSITORY_CONN.add(new File(MusicRepository.class.getResource("/mo.rdf").getPath()), BASE_URI, RDFFormat.RDFXML);
		
		ValueFactory f = repository.getValueFactory();
		
		URI musicGroup = f.createURI(BASE_URI+ "MusicGroup");
		URI performance = f.createURI(BASE_URI+ "Performance");
		URI performed = f.createURI(BASE_URI+ "performed");

		URI a = f.createURI(BASE_URI+ "awolnation");
		URI sail = f.createURI(BASE_URI+ "sail");
		
		REPOSITORY_CONN.add(a, RDF.TYPE, musicGroup);
		REPOSITORY_CONN.add(sail, RDF.TYPE, performance);
		REPOSITORY_CONN.add(a, performed, sail);
	}
	
	
}
