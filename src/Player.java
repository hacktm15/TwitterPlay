import java.util.ArrayList;

public class Player {
	private static Player instance = null;
	private static ArrayList<Song> currentPlaylist;
	private static ArrayList<Song> playedSongs = new ArrayList<Song>();

	private Player(ArrayList<Song> playlist) {
		currentPlaylist = playlist;
	}

	public static Player getInstance(ArrayList<Song> playlist) {
		if (instance == null) {
			return new Player(playlist);
		} else
			return instance;

	}

	public static void startPlaying(ArrayList<Song> playlist) {
		Song highestPrioritySong = currentPlaylist.get(0);

		if (!playlist.isEmpty()) {
			for (Song song : playlist) {
				if (!currentPlaylist.contains(song) && !playedSongs.contains(song)) {
					currentPlaylist.add(song);
				}
			}
		}
		if (!currentPlaylist.isEmpty()) {
			for (Song song : currentPlaylist) {
				if (song.priority > highestPrioritySong.priority) {
					highestPrioritySong = song;
				}
			}
			playSong(highestPrioritySong);

			if (!playlist.isEmpty()) {
				// startPlaying(playlist);
				TwitterPlay.start();
			}

		}
	}

	private static void playSong(Song song) {
		Speaker speaker = new Speaker();
		speaker.playSong(song);
		playedSongs.add(song);
		currentPlaylist.remove(song);
		try {

			Thread.sleep(60 * 1000);
			if(TwitterPlay.checkForUpdates() == null ){
				startPlaying(null);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
