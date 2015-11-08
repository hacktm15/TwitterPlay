import java.util.ArrayList;

public class XMLParser{
	ArrayList<Song> songs = new ArrayList<Song>();
	String id = "";
	String name = "";
	String type = "";

	public ArrayList<Song> parseXML(String xml) {
		String[] lines = xml.split("\n");
		for (int i = 0; i < lines.length; i++) {
			String sCurrentLine = lines[i];
			if (sCurrentLine.contains("item key")) {

				String[] aux = sCurrentLine.split("=");
				id = aux[1].substring(1, aux[1].length() - 2);
				continue;
			}
			if (sCurrentLine.contains("c8_array")) {
				String[] aux = sCurrentLine.split("<c8_array>");
				String[] aux2 = aux[1].split("</c8_array>");
				name = aux2[0];
				String[] aux3;
				if(name.contains("[")){
					aux3=name.split("\\[");
					name=aux3[0];
				}
				if(name.contains("(")){
					aux3=name.split("\\(");
					name=aux3[0];
				}
				
				if(name.endsWith(".mp3")){
					name=name.substring(0,name.length()-4);
				}
	
				continue;
			}
			if (sCurrentLine.contains("type")
					&& !sCurrentLine.contains("subtype")) {
				String[] aux = sCurrentLine.split("<u8>");
				String[] aux2 = aux[1].split("</u8>");
				
				type = aux2[0];
				songs.add(new Song(id, name, type));
				continue;
			}

		}
		return songs;
	}
	
}
