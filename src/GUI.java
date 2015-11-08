import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;

public class GUI {

	private JFrame frame;
	private JTextField txtKeywordHashtag;
	private String hashtagEventKeyword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hashtagEventKeyword = txtKeywordHashtag.getText();
				System.out.println(hashtagEventKeyword);
				TwitterPlay play = new TwitterPlay(hashtagEventKeyword);
				play.start();
				try{
					Thread.sleep(2000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				JList list =new JList();
				
				//JList songList = new JList(play.getPlaylist());
				/*textPane.setText(play.getPlaylist().toString());
				textPane.repaint()*/;
				System.out.println("Something !!!");
				System.out.println(play.getPlaylist());
				System.out.println(list);
				frame.getContentPane().add(list);
			}
		});
		frame.getContentPane().add(btnStart, BorderLayout.SOUTH);
		
		txtKeywordHashtag = new JTextField();
		txtKeywordHashtag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hashtagEventKeyword = txtKeywordHashtag.getText();
			}
		});
		txtKeywordHashtag.setText("Keyword / Hashtag / Event ");
		frame.getContentPane().add(txtKeywordHashtag, BorderLayout.NORTH);
		txtKeywordHashtag.setColumns(10);
	}

}
