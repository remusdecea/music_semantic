package edu.upb.aws.music.resources;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.query.resultio.sparqljson.SPARQLResultsJSONWriter;
import org.openrdf.repository.RepositoryException;

import edu.upb.aws.music.repo.MusicRepository;

@WebServlet("/artists")
public class ArtistsServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json");
		
		SPARQLResultsJSONWriter sparqlWriter = new SPARQLResultsJSONWriter(response.getOutputStream());
		
		String query = "PREFIX mo: <http://purl.org/ontology/mo/> "
				+ "SELECT ?artist WHERE { ?artist rdf:type mo:MusicArtist }";		
		TupleQuery tupleQuery;
		try {
			tupleQuery = MusicRepository.getRepositoryConnection()
					.prepareTupleQuery(QueryLanguage.SPARQL, query);
			tupleQuery.evaluate(sparqlWriter);

		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		} catch (TupleQueryResultHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
