package project;

import java.util.Arrays;
import java.util.List;

public class Album extends Media{
	private String songs;
	private String artist;
	public Album(String title,int numberOfCopies,String artist,String songs) {
		super(title, numberOfCopies);
		this.artist = artist;
		this.songs=songs;
	}

	public String getSongs() {
		return songs;
	}
	public void setSongs(String songs) {
		this.songs = songs;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public boolean search(String searchedTitle, String searchedArtist,String searchedSongs) {

		if (searchedSongs != null && this.title.equals(searchedTitle)
				&& artist.equals(searchedArtist) && checkSongs(searchedSongs)) {
			return true;
		}

		if (this.title.equals(searchedTitle)
				&& artist.equals(searchedArtist)) {
			return true;
		}

		if (searchedSongs != null && this.title.equals(searchedTitle)
				&& checkSongs(searchedSongs)) {
			return true;
		}

		if (searchedSongs != null && artist.equals(searchedArtist) && checkSongs(searchedSongs)) {
			return true;
		}

		if ( searchedSongs != null && checkSongs(searchedSongs)) {
			return true;
		}

		if (this.title.equals(searchedTitle)) {
			return true;
		}

		return artist.equals(searchedArtist);
	}

	@Override
	public String toString() {
		return "Album{" +
				"songs='" + songs + '\'' +
				", artist='" + artist + '\'' +
				", title='" + title + '\'' +
				", numberOfCopies=" + numberOfCopies +
				'}';
	}

	private boolean checkSongs(String searchedSongs) {
		String [] songArr = searchedSongs.split(",");
		List<String> songsList = Arrays.asList(songArr);
		boolean itContains = true;
		for (int i = 0; i < songsList.size(); i++) {
			if (!this.songs.contains(songsList.get(i))) {
				itContains = false;
				break;				
			}
		}
		return itContains ;
	}


}
