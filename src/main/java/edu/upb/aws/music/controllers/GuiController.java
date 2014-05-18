package edu.upb.aws.music.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


@Controller
@RequestMapping("/search")
public class GuiController {

	private static String url = "http://localhost:8080/Music/";
	
	private static Logger logger = LogManager.getLogger(GuiController.class
			.getName());
	
	@RequestMapping(method = RequestMethod.GET)
	public String printSearchPage(ModelMap model, HttpServletRequest request) throws JSONException{
		System.out.println("home page requested");
		logger.info("home page requested");
		model.addAttribute("message", "Explore Music");
		model.addAttribute("artists", getArtists());
		model.addAttribute("tags", getTags());
		return "/search"; 
	}
	
	private ArrayList<String> getArtists() throws JSONException{
		ArrayList<String> artists = new ArrayList<>();
		final ClientConfig cc = new DefaultClientConfig();
		final Client client = Client.create(cc);
		//TODO: relative to context
		WebResource webResource = client.resource(url+"/artists");
		ClientResponse clientResponse = webResource
				.accept(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
		JSONArray bindingsArray = responseEntity.getJSONObject("results").getJSONArray("bindings");
		System.out.println(responseEntity);
		for (int i = 0; i < bindingsArray.length(); i++) {
			JSONObject artistJson = ((JSONObject) bindingsArray.get(i)).getJSONObject("artist");
			artists.add(artistJson.getString("value").substring(
					artistJson.getString("value").lastIndexOf("/") + 1));
		}
		return artists;
	}
	
	private ArrayList<String> getTags() throws JSONException{
//		ArrayList<String> artists = new ArrayList<>();
//		final ClientConfig cc = new DefaultClientConfig();
//		final Client client = Client.create(cc);
//		//TODO: relative to context
//		WebResource webResource = client.resource(url+"/artists");
//		ClientResponse clientResponse = webResource
//				.accept(MediaType.APPLICATION_JSON)
//				.get(ClientResponse.class);
//		JSONObject responseEntity = clientResponse.getEntity(JSONObject.class);
//		JSONArray bindingsArray = responseEntity.getJSONObject("results").getJSONArray("bindings");
//		System.out.println(responseEntity);
//		for (int i = 0; i < bindingsArray.length(); i++) {
//			JSONObject artistJson = ((JSONObject) bindingsArray.get(i)).getJSONObject("artist");
//			artists.add(artistJson.getString("value").substring(
//					artistJson.getString("value").lastIndexOf("/") + 1));
//		}
//		return artists;
		ArrayList<String> tags = new ArrayList<>();
		tags.add("rock");
		tags.add("alternative rock");
		tags.add("indie rock");
		tags.add("hip hop");
		return tags;
	}
}
