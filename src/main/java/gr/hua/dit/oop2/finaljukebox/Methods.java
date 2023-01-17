package gr.hua.dit.oop2.finaljukebox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;

public class Methods {

    private static ArrayList<Path> musicList;
    private static Scanner input;
    

   

    public static void playMusic(String name) {
        Player p = PlayerFactory.getPlayer();

        try {
            InputStream song = new FileInputStream(name);
            p.play(song);

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (PlayerException e) {
            System.err.println("Something's wrong with the player: " + e.getMessage());
        }

    }

    public static void terminateSong(Player p) {

        if (p != null) {
            p.close();
        }

    }

    public static void loop(String path) {
        while (true) {
        	File file=new File(path);
            
            String name=file.getName();
            System.out.println(name);
            FrameStart.labelSong.setText("");
            FrameStart.labelSong.setText(name + "this");
            playMusic(path);
        }

    }

    public static void order(ArrayList<Path> musicList){
     
        for (int i = 0; i < musicList.size(); i++) {
            String path = String.valueOf(musicList.get(i));
            File file=new File(path);
            
            String name=file.getName();
            System.out.println(name);
            FrameStart.labelSong.setText("");
            FrameStart.labelSong.setText(name + "this");
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //playMusic(path);

        }
    }

    public static void random(ArrayList<Path> musicList) {
        SecureRandom rand = new SecureRandom();
        while (true) {
            if (musicList.size() == 0) {
                break;
            }

            int i = rand.nextInt(musicList.size());
            String name = String.valueOf(musicList.get(i));

            playMusic(name);
            musicList.remove(i);
        }

    }

}
