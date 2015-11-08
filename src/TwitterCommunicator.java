import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterCommunicator {
	private  String keyword;
	private  Twitter twitter = null;
	private  List<Status> tweets;
	private  ArrayList<SongRequest> requests;
	private  boolean isSongListNeeded = false;
	private static int searchTries=0;

	public TwitterCommunicator(String key){
		keyword=key;
	}
	private  void authenticate() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("A2yTG4zUVBOQDWZPxxKu0Hkd6")
				.setOAuthConsumerSecret("ewDEuExMbu1KwhBcHGRO2xfwDEVitE3O6CMXUlPACAIbcr3QCG")
				.setOAuthAccessToken("4129628986-rDa2oGfZoOlmi4NGoNuO6xbpJCUIBbxtzwqwuT4")
				.setOAuthAccessTokenSecret("eSoc0NvNqN3YWFSyIcf3WPv1yZtfBk14Ycy456dupoPx7");

		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	public  void getHashtagTweets() throws TwitterException {
		QueryResult result = null;
		try {
			result = twitter.search(new Query("%23" + keyword));

		} catch (TwitterException te) {
			System.out.println("Error searching !");
			if(searchTries<3){
				searchTries++;
				try{
					Thread.sleep(20 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getHashtagTweets();
			}
		}
		tweets = result.getTweets();
		/*if (tweets.size() < 2) {
			isSongListNeeded = true;
		}*/
		// System.out.println(tweets);
		// System.out.println(result);
	}

	public  void setSongRequests() {
		requests = new ArrayList<SongRequest>();
		for (Status status : tweets) {
			SongRequest request = new SongRequest();
			request.setText(status.getText().replace("#" + keyword, ""));
			request.setAuthor(status.getUser().getName());
			request.setCreatedAt(status.getCreatedAt());
			requests.add(request);
		}

	}

	public  void setPriority() {

		for (int j = 0; j < requests.size(); j++) {
			for (int i = 0; i < requests.size(); i++) {
				if (j == i) {
					continue;
				}
				if (requests.get(j).text.equalsIgnoreCase(requests.get(i).text)) {
					requests.get(j).priority++;
					requests.remove(i);
					i--;
					continue;

				}
			}
		}

	}

	private  void sendSongListTweet() {
		try {
			ArrayList<Song> songs = TwitterPlay.getSongs();
			String message = "";
			for (Song song : songs) {
				message += song.toString();
			}
			message += " " + keyword;
			twitter.updateStatus(message);

		} catch (

		Exception e)

		{
			e.printStackTrace();
		}

	}

	public  void showTweets() {
		for (SongRequest song : requests) {
			System.out.println(song.toString());
		}
	}

	public  ArrayList<SongRequest> retrieveSongRequests() {
		try {
			
			authenticate();
			getHashtagTweets();
			setSongRequests();
			setPriority();
			if (isSongListNeeded) {
				sendSongListTweet();
			}
			
			return requests;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*public static void main(String args[]) {
		try {
			keyword = "hacktm2015Test999";
			authenticate();
			getHashtagTweets(keyword);
			setSongRequests();
			setPriority();
			showTweets();
			for (SongRequest request : requests) {
				System.out.println(request.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Speaker speaker = new Speaker();
		// speaker.sendRequest("fsapi/CREATE_SESSION?pin=1234");
		// System.out.println(speaker.retrieveSongList());
		// XMLParser parser = new XMLParser();
		// parser.parseXMLString(speaker.retrieveSongList());
	}*/

}