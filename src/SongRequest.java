import java.util.Date;

public class SongRequest {
	String text;
	String author;
	Date createdAt;
	int priority=1;
	public void setText(String text) {
		this.text = text;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String toString(){
		String output = "\nName : "+text+" Author : "+author+" Time : "+createdAt+" Priority : "+priority;
		return output;
	}
}
