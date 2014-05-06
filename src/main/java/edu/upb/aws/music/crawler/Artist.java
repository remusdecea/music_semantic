package edu.upb.aws.music.crawler;
import java.util.ArrayList;
import java.util.List;


public class Artist {

	private String name, lastFmURL;
	private List<String> songs;

	public Artist(String name, String lastFmURL) {
		this.name = name;
		this.lastFmURL = lastFmURL;
		this.songs = new ArrayList<String>();
	}

	@Override
	public boolean equals(Object o) {
		return this.lastFmURL.equals(((Artist)o).getLastFmURL());
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
}
