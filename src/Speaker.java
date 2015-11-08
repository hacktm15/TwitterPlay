
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Speaker {
	private String SpeakerIP = "http://192.168.0.100:80/";
	private String createSession = "fsapi/CREATE_SESSION?pin=1234";
	private String disableNavi ="fsapi/SET/netRemote.nav.state?pin=1234&value=0";
	private String readList ="fsapi/LIST_GET_NEXT/netRemote.sys.caps.validModes/-1?pin=1234&maxItems=65535";
	private String setMusicPlayer ="fsapi/SET/netRemote.sys.mode?pin=1234&value=3";
	private String enableNavi ="fsapi/SET/netRemote.nav.state?pin=1234&value=1";
	
	private String USBStatus="fsapi/GET/netRemote.nav.status?pin=1234";
	private String navigateToUSB = "fsapi/SET/netRemote.nav.action.navigate?pin=1234&value=1";
	private String showList ="fsapi/LIST_GET_NEXT/netRemote.nav.list/-1?pin=1234&maxItems=100";
	
	private String select ="fsapi/SET/netRemote.nav.action.selectItem?pin=1234&value=";
	
	
	public String sendRequest(String request) {
		
		try {

			URL url = new URL(SpeakerIP+request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output="";
			String readLine;
		//	System.out.println("Output from Server .... \n");
			while (( readLine = br.readLine()) != null) {
				output += readLine +"\n";
			}

			conn.disconnect();
			
			return output;
			
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;
	}

		public String retrieveSongList(){
			String songList=null;
		
			
			System.out.println(sendRequest(createSession));
			System.out.println(sendRequest(setMusicPlayer));
			System.out.println(sendRequest(disableNavi));
			System.out.println(sendRequest(enableNavi));
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println(sendRequest(USBStatus));
			
			//System.out.println(sendRequest(readList));
			
			
			
			
			System.out.println(sendRequest(navigateToUSB));
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			songList = sendRequest(showList);
			System.out.println(songList);
			/*if(songList.contains("Shared media")){
				sendRequest(navigateToUSB);
				songList = sendRequest(showList);
			}*/
			//sendRequest(select+"15");
			
			return songList;
			
		}
		
		public void playSong(Song song){
			sendRequest(select+song.id);
		}

}