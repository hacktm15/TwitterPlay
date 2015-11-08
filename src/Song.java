public class Song {
	String id;
	String name;
	String type;
	int priority;
	
	public Song(String id, String nume,String type) {
		super();
		this.name = nume;
		this.id = id;
		this.type= type;
			
	}

	@Override
	public String toString() {
		return "\nSong =" + name ;
	}
	
	
}
