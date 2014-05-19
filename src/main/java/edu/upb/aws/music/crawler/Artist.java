package edu.upb.aws.music.crawler;
import java.util.ArrayList;
import java.util.List;


public class Artist {

	private String name, lastFmURL;
	private List<String> songs;
	private List<String> tags;
	private List<Artist> similarArtists;

	public Artist(String name, String lastFmURL) {
		this.name = name;
		this.lastFmURL = lastFmURL;
		this.songs = new ArrayList<String>();
		this.tags = new ArrayList<String>();
		this.similarArtists = new ArrayList<>();
	}

	@Override
	public boolean equals(Object o) {
		return this.lastFmURL.equals(((Artist)o).getLastFmURL());
	}
	
	@Override
	public int hashCode() {
		return this.lastFmURL.hashCode();
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastFmURL() {
		return lastFmURL;
	}

	public void setLastFmURL(String lastFmURL) {
		this.lastFmURL = lastFmURL;
	}
	
	public List<String> getSongs() {
		return songs;
	}

	public void setSongs(List<String> songs) {
		this.songs = songs;
	}
	
	public void addSong(String song) {
		this.songs.add(song);
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}

	public List<Artist> getSimilarArtists() {
		return similarArtists;
	}

	public void setSimilarArtists(List<Artist> similarArtists) {
		this.similarArtists = similarArtists;
	}
	
	public void addSimilarArtist(Artist artist) {
		this.similarArtists.add(artist);
	}
}
