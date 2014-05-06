package edu.upb.aws.music.resources;

import java.io.IOException;

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

		String queryString = "SELECT ?x ?y WHERE { ?x <http://purl.org/ontology/mo/performed> ?y }  ";
		TupleQuery tupleQuery;
		try {
			tupleQuery = MusicRepository.getRepositoryConnection()
					.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult result = tupleQuery.evaluate();
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value valueOfX = bindingSet.getValue("x");
				Value valueOfY = bindingSet.getValue("y");
				System.out.println(valueOfX + " " + valueOfY);
				response.getWriter().println(valueOfX + " " + valueOfY);
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (MalformedQueryException e) {
			e.printStackTrace();
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}

		
	}

}
