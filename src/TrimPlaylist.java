import java.util.ArrayList;

public class TrimPlaylist {

	

	public static ArrayList<Song> filterSongsRequests(
			ArrayList<SongRequest> songRequests, ArrayList<Song> songs) {
		ArrayList<Song> playlistSongs = new ArrayList<Song>();
		
		for (SongRequest songRequest : songRequests) {
			String requestName=trimSpaceAndAllNonNumeric(songRequest.text);
			for (Song stickSong : songs) {
				String songName=trimSpaceAndAllNonNumeric(stickSong.name);
				if(songName.contains(requestName))
				{	
					playlistSongs.add(stickSong);
				}
			}
		}
		for(int i=0;i<playlistSongs.size();i++){
			for(int j=0;j<playlistSongs.size();j++){
				if(i==j){
					continue;
				}
				if(playlistSongs.get(i)==playlistSongs.get(j)){
					playlistSongs.get(i).priority++;
					playlistSongs.remove(j);
					j--;
				}
			}
		}
		return playlistSongs;
	}
	private static String trimSpaceAndAllNonNumeric(String string) {
		string=string.replaceAll("[^a-zA-Z0-9\\s]", "");
		string=string.replaceAll(" ","");
		string=string.toLowerCase();
		return string;
	}
}
