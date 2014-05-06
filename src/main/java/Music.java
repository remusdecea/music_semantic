import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import info.aduna.iteration.Iterations;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Model;
import org.openrdf.model.Namespace;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.vocabulary.FOAF;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.openrdf.sail.memory.MemoryStore;

public class Music {
	
	
	
	public static void main(String[] args) throws RepositoryException,
			RDFHandlerException, RDFParseException, IOException {

		File file = new File("src/main/resources/mo.rdf");
		Repository repository = new SailRepository(new MemoryStore());
		repository.initialize();
		RepositoryConnection connection = repository.getConnection();
		String baseURI = "http://example.org/example/local";
		
		FileOutputStream outputXML = new FileOutputStream(new File("src/main/resources/output_rdfxml.rdf"));
		FileOutputStream outputTurtle = new FileOutputStream(new File("src/main/resources/output_turtle.rdf"));
		
		
		try {
			connection.add(file, baseURI, RDFFormat.RDFXML);
			
			RepositoryResult<Namespace> namespaces = connection.getNamespaces();
			RepositoryResult<Statement> statements = connection.getStatements(
					null, null, null, true);
			Model model = Iterations.addAll(statements, new LinkedHashModel());
			
			//Rio.write(model, System.out, RDFFormat.TURTLE);
			//Rio.write(model, outputXML, RDFFormat.RDFXML);
			//Rio.write(model, outputTurtle, RDFFormat.TURTLE);
			
			
		} finally {
			connection.close();
		}
	}

}
