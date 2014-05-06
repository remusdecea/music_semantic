package edu.upb.aws.music.resources;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.query.resultio.sparqljson.SPARQLResultsJSONWriter;
import org.openrdf.repository.RepositoryException;

import edu.upb.aws.music.repo.MusicRepository;

@WebServlet("/api")
public class EndpointServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6934953312270878966L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		
		SPARQLResultsJSONWriter sparqlWriter = new SPARQLResultsJSONWriter(response.getOutputStream());
		
//		String queryString = "PREFIX mo: <http://purl.org/ontology/mo/> SELECT ?x ?y WHERE { ?x mo:performed ?y }  ";
		String queryString = request.getParameter("query");
		TupleQuery tupleQuery;
		try {
			tupleQuery = MusicRepository.getRepositoryConnection()
					.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			System.out.println("json writer");
			tupleQuery.evaluate(sparqlWriter);
			
//			while (result.hasNext()) {
//				BindingSet bindingSet = result.next();
//				List<String> bindingNames = result.getBindingNames();
//				System.out.println("BINDING NAMES " + bindingNames);
//				for(int i = 0; i < bindingNames.size(); i++){
//					response.getWriter().print(bindingSet.getValue(bindingNames.get(i)) + " ");
//				}
//				
//				
//			}
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

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json");

		SPARQLResultsJSONWriter sparqlWriter = new SPARQLResultsJSONWriter(response.getOutputStream());
		String queryString = request.getParameter("query");
		TupleQuery tupleQuery;
		try {
			tupleQuery = MusicRepository.getRepositoryConnection()
					.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			tupleQuery = MusicRepository.getRepositoryConnection()
					.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			System.out.println("json writer");
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
