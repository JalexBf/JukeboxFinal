/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.hua.dit.oop2.finaljukebox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FrameStart {

	final static private int WIDTH = 300;
	final static private int HEIGHT = 250;
	public static JFrame frame;
	private JLabel label;
	private static JTextField textField;
	private JButton butOk;
	private static Path path;
	private static ArrayList<Path> musicList;
	private static Player p;
	private static DirectoryStream<Path> directoryStream;
	private static JRadioButton radioOrder;
	private static JRadioButton radioRandom;
	private static JRadioButton radioLoop;
	public static JLabel labelSong; 

	public void func() {
		frame = new JFrame("Jukebox");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);

		frame.getContentPane().setLayout(null); // set dimensions for each component
		frame.setLocationRelativeTo(null); // frame=window

		label = new JLabel("Give directory name: ");
		frame.getContentPane().add(label);
		label.setLocation(80, 50);
		label.setSize(label.getPreferredSize()); // label's size

		textField = new JTextField();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(textField);
		textField.setBounds(50, 90, 200, 20);
		// textField.setSize(150, 20);

		butOk = new JButton("OK");
		frame.getContentPane().add(butOk);
		// butOk.setLocation(85,100);
		butOk.setBounds(90, 140, 100, 20);
		// butOk.setSize(150, 30);
		butOk.addActionListener(new ActionListener() { // 2nd Listener activated with Button click.
			public void actionPerformed(ActionEvent e) {
				functionListener();
			}
		});
		textField.addKeyListener(new CustomKeyListener());

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		frame.setVisible(true);

	}

	public static void jList() {
		DefaultListModel<String> model = new DefaultListModel<>();
		for (Path p : musicList) {
			File file = new File(String.valueOf(p));
			String element = file.getName();
			model.addElement(element);
		}
		final JList<String> view = new JList<>(model);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(view);// has view with scrollPane (i with constrator)
		view.setLayoutOrientation(JList.VERTICAL);// default for VERTICAL could have been skiped

		/*
		 * Second way for ScrollPane in view(lines 130-133)
		 * 
		 * 
		 * JScrollPane scrollPane = new JScrollPane(view);
		 * 
		 */

		JLabel labelList = new JLabel();
		labelList.setText("Choose which song you want to listen: ");

		labelList.setForeground(Color.YELLOW);
		labelList.setOpaque(true);// background view
		labelList.setBackground(Color.BLACK);

		JButton buttonOk = new JButton("OK");

		JPanel ListPanel = new JPanel();
		ListPanel.setLayout(new GridLayout(3, 1,10,10));

		ListPanel.add(labelList);

		ListPanel.add(scrollPane);

		ListPanel.add(buttonOk);

		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = "";

				if (view.getSelectedIndex() != -1) {
					data = view.getSelectedValue();
					for (Path p : musicList) {
						String path = String.valueOf(p);
						if (path.contains(data)) {
							System.out.println();
							System.out.println(path);
							
							if (radioOrder.isSelected()) {
								Methods.order(musicList);
								break;
							}else if (radioRandom.isSelected()) {
								Methods.random(musicList);
								break;
							}else {
								Methods.loop(path);
								break;
							}
								
						}
					}

				} else
					JOptionPane.showMessageDialog(frame, "Choose something from list"); // <-------
			}
		});

		// oloklirothike to ListPanel

		JButton stopButton = new JButton("Stop");
		JButton playButton = new JButton("Play");
		JButton closeButton = new JButton("Close");
		JButton nextButton = new JButton("Next");
        //Dimension d=new Dimension(50,50);
		//nextButton.setPreferredSize(d);
		

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 2,10,10));

		buttonPanel.add(stopButton);
		buttonPanel.add(playButton);
		buttonPanel.add(closeButton);
		buttonPanel.add(nextButton);
		

		// oloklirothike to buttonPanel

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1,10,10));

		ButtonGroup options = new ButtonGroup();

		radioOrder = new JRadioButton("Order");
		radioOrder.setSelected(true); // to Default einai i proti epilogi
	    radioRandom = new JRadioButton("Random");
		radioLoop = new JRadioButton("Loop");

		options.add(radioOrder);
		options.add(radioRandom);
		options.add(radioLoop);

		labelSong = new JLabel("Name Song's", JLabel.CENTER);

		labelSong.setForeground(Color.orange); // <-----
		labelSong.setOpaque(true);// diafanes ara fenete to background
		labelSong.setBackground(Color.lightGray); // <---

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(radioOrder);
		optionsPanel.add(radioRandom);
		optionsPanel.add(radioLoop);
		optionsPanel.setBackground(Color.gray);
		centerPanel.add(optionsPanel);
		centerPanel.add(labelSong);
		

		JFrame frame = new JFrame("App"); // TODO
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(ListPanel, BorderLayout.NORTH);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		// ena frame me auta ta tria Panel

		frame.pack(); // orizei diatksi kai size
		frame.setVisible(true);

		frame.setSize(1000, 1000);
		frame.setLocationRelativeTo(null);

	}

	public static void functionListener() {
		frame.dispose();
		path = Paths.get(textField.getText());

		if (!path.isAbsolute()) { // path conversion
			path = path.toAbsolutePath();
			System.out.printf("Absolute path: %s%n", path);
		} else {
			System.out.printf("path: %s%n", path);
		}

		musicList = new ArrayList<>();

		if (!Files.exists(path)) {
			System.err.println("error path");
			System.exit(0);
		}

		// add to the log file a new spleiade, which has the SEVERE and the exception
		try {
			directoryStream = Files.newDirectoryStream(path);
		} catch (IOException ex) {
			Logger.getLogger(FrameStart.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Content of musicList: ");

		for (Path p : directoryStream) {
			String str = String.valueOf(p);

			if (str.endsWith(".mp3")) {
				musicList.add(p);
				System.out.println("Song's path: " + str);

			}

		}

		// check if the list is empty
		if (musicList.size() == 0) {
			System.out.println("Empty list.");
			System.exit(0);
		}
		Collections.sort(musicList);
		jList();

	}

}
