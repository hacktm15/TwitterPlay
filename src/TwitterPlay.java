import java.util.ArrayList;

import twitter4j.HashtagEntity;

public class TwitterPlay {
	private static ArrayList<SongRequest> requests;
	private static ArrayList<Song> songs;
	private static ArrayList<Song> playlist = null;
	private static ArrayList<Song> oldPlaylist =null;
	private static String hashtagEvent;

	public TwitterPlay(String hashtagEventKeyword) {
		hashtagEvent = hashtagEventKeyword;
	}
	
	public ArrayList<Song> getPlaylist(){
		return playlist;
	}
	
	private static void populateSongs() {
		Speaker speaker = new Speaker();
		XMLParser parser = new XMLParser();
		songs = parser.parseXML(speaker.retrieveSongList());
	}

	private void populateSongRequests() {
		TwitterCommunicator twitterCom = new TwitterCommunicator(hashtagEvent);
		requests = twitterCom.retrieveSongRequests();
		createPlaylist();
	}

	public static ArrayList<Song> getSongs() {
		return songs;
	}

	public void createPlaylist() {
		playlist = TrimPlaylist.filterSongsRequests(requests, songs);
	}

	public static void start() {
		populateSongs();
		
		TwitterPlay play = new TwitterPlay(hashtagEvent);
		play.populateSongRequests();
		System.out.println(requests);
		System.out.println(songs);
		System.out.println(playlist);
		if (!playlist.isEmpty()) {
			Player player = Player.getInstance(playlist);
			player.startPlaying(playlist);
		}
		

	}
	
	public static ArrayList<Song> checkForUpdates(){
		oldPlaylist = playlist;
		TwitterPlay play = new TwitterPlay(hashtagEvent);
		play.populateSongRequests();
		if(oldPlaylist!=playlist){
			return playlist;
		}else{
			return null;
		}
		
	}

	public static void main(String args[]) {
		start();
	}
}
